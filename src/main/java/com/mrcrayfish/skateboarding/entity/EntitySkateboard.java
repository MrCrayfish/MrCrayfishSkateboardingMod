package com.mrcrayfish.skateboarding.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.skateboarding.MrCrayfishSkateboardingMod;
import com.mrcrayfish.skateboarding.api.trick.Flip;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.api.trick.Trick;
import com.mrcrayfish.skateboarding.network.PacketHandler;
import com.mrcrayfish.skateboarding.network.message.MessageStack;
import com.mrcrayfish.skateboarding.util.ComboBuilder;
import com.mrcrayfish.skateboarding.util.GrindHelper;

public class EntitySkateboard extends Entity
{
	public ComboBuilder combo = new ComboBuilder();
	public int comboTimer;

	public double currentSpeed = 0.0;
	public double maxSpeed = 8.0;
	private boolean allowOnce = false;

	private boolean pushed = false;
	private boolean jumping = false;
	private int jumpingTimer = 0;

	private boolean inTrick = false;
	private int inTrickTimer = 0;
	private Trick currentTrick = null;

	private boolean grinding = false;
	private boolean goofy = true;
	private boolean switch_ = false;
	private boolean flipped = false;

	private float angleOnJump;

	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;

	public EntitySkateboard(World worldIn)
	{
		super(worldIn);
		this.preventEntitySpawning = true;
		this.setSize(1.0F, 0.5F);
	}

	public EntitySkateboard(World worldIn, double x, double y, double z)
	{
		this(worldIn);
		this.setPosition(x, y, z);
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRiderSit()
	{
		return true;
	}

	@Override
	public boolean isRiding()
	{
		return this.ridingEntity != null;
	}

	@Override
	protected void entityInit()
	{

	}

	@Override
	public double getMountedYOffset()
	{
		return 0.5F;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entityIn)
	{
		return entityIn.getEntityBoundingBox();
	}

	@Override
	public AxisAlignedBB getBoundingBox()
	{
		return this.getEntityBoundingBox();
	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_)
	{
		if (this.riddenByEntity != null)
		{
			this.prevPosX = this.posX = p_180426_1_;
			this.prevPosY = this.posY = p_180426_3_;
			this.prevPosZ = this.posZ = p_180426_5_;
			this.rotationYaw = p_180426_7_;
			this.rotationPitch = p_180426_8_;
			this.setPosition(p_180426_1_, p_180426_3_, p_180426_5_);
			this.motionX = this.velocityX = 0.0D;
			this.motionY = this.velocityY = 0.0D;
			this.motionZ = this.velocityZ = 0.0D;
		}
		else
		{
			this.motionX = this.velocityX;
			this.motionY = this.velocityY;
			this.motionZ = this.velocityZ;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z)
	{
		this.velocityX = this.motionX = x;
		this.velocityY = this.motionY = y;
		this.velocityZ = this.motionZ = z;
	}

	private int life;

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (!inTrick)
		{
			if (comboTimer > 0)
			{
				comboTimer--;
			}
			else
			{
				combo.reset();
			}
		}

		/** Will only execute code if player is riding skateboard */
		if (this.riddenByEntity instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) this.riddenByEntity;

			/** Handles pushing */
			if (entity.moveForward > 0 && this.isCollidedVertically && !pushed && !grinding)
			{
				currentSpeed += 1.0D;

				if (currentSpeed > maxSpeed)
				{
					currentSpeed = maxSpeed;
				}
				pushed = true;
			}
			else if (entity.moveForward == 0.0)
			{
				pushed = false;
			}

			/**
			 * If skateboard is not jumping, allow turning. When player jumps
			 * from grinding, give exception to jump off in a direction using
			 * allowOnce.
			 */
			if (!jumping | allowOnce)
			{
				float f = this.riddenByEntity.rotationYaw;

				/** If grinding, set direction to direction of grinding. */
				if (grinding)
				{
					f = EnumFacing.fromAngle(this.rotationYaw).rotateY().getHorizontalIndex() * 90F;
				}

				this.motionX = -Math.sin((double) (f * (float) Math.PI / 180.0F)) * currentSpeed / 16D;
				this.motionZ = Math.cos((double) (f * (float) Math.PI / 180.0F)) * currentSpeed / 16D;
				allowOnce = false;
			}
		}
		else
		{
			/** If no player riding, make the board stop */
			this.motionX = 0.0D;
			this.motionZ = 0.0D;
		}

		/** If grinding, make position of skateboard go to center of block. */
		if (grinding)
		{
			EnumFacing face = EnumFacing.fromAngle(this.rotationYaw).rotateY();
			if (face == EnumFacing.NORTH | face == EnumFacing.SOUTH)
				this.setPosition(Math.floor(this.posX) + 0.5, Math.floor(this.posY), this.posZ);
			if (face == EnumFacing.EAST | face == EnumFacing.WEST)
				this.setPosition(this.posX, Math.floor(this.posY), Math.floor(this.posZ) + 0.5);
		}

		/** When collided with block, slow speed by 75% */
		if (this.isCollidedHorizontally)
		{
			this.currentSpeed *= 0.75D;
		}

		if (jumping)
		{

			if (jumpingTimer < 10)
				motionY = 0.5D - (double) jumpingTimer * 0.03D;

			if (inTrick && currentTrick != null)
			{
				inTrickTimer++;

				if (currentTrick instanceof Flip)
				{
					Flip flip = (Flip) currentTrick;
					if (inTrickTimer > flip.performTime())
					{
						combo.addTrick(getCurrentTrick());
						comboTimer = 100;
						getCurrentTrick().onEnd(this);
						resetTrick();
					}
					else if (this.onGround && flip.performTime() > inTrickTimer)
					{
						jumping = false;
						jumpingTimer = 0;
						onGround = true;
						resetTrick();
						performStack();
					}
				}
			}

			if (this.onGround && !grinding)
			{
				jumping = false;
				jumpingTimer = 0;
				resetTrick();
				handleLanding();
			}

			jumpingTimer++;
		}

		if (grinding)
		{
			if (inTrick && currentTrick != null)
			{
				if (currentTrick instanceof Grind)
				{
					inTrickTimer++;

					Grind grind = (Grind) currentTrick;
					if (!GrindHelper.canGrind(worldObj, this.posX, this.posY, this.posZ))
					{
						combo.addTrick(getCurrentTrick());
						comboTimer = 100;
						getCurrentTrick().onEnd(this);
						resetTrick();
						grinding = false;
						onGround = false;
					}
				}
			}
		}
		this.motionY -= 0.08D;

		this.moveEntity(this.motionX, this.motionY, this.motionZ);

		if (this.riddenByEntity != null)
		{
			// landedCorrectly();
		}

		/** Yaw Rotation Handling **/

		if (!grinding)
		{
			if (!jumping)
			{
				this.rotationPitch = 0.0F;
				double init = (double) this.rotationYaw;
				double newX = this.prevPosX - this.posX;
				double newZ = this.prevPosZ - this.posZ;

				if (newX * newX + newZ * newZ > 0.001D)
				{
					init = (double) ((float) (Math.atan2(newZ, newX) * 180.0D / Math.PI));
				}

				double d12 = MathHelper.wrapAngleTo180_double(init - (double) this.rotationYaw);

				if (d12 > 20.0D)
				{
					d12 = 20.0D;
				}

				if (d12 < -20.0D)
				{
					d12 = -20.0D;
				}

				this.rotationYaw = (float) ((double) this.rotationYaw + d12);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			}
			else
			{
				if (this.riddenByEntity instanceof EntityLivingBase)
				{
					EntityLivingBase entity = (EntityLivingBase) this.riddenByEntity;
					this.rotationYaw = this.interpolateRotation(entity.prevRotationYaw, entity.rotationYaw) - 90F;
				}
			}
		}

		this.motionY *= 0.9800000190734863D;
		if (!grinding)
		{
			this.currentSpeed *= 0.99D;
		}
	}

	public void onUpdateServer()
	{
		life++;
		if (life >= 10000)
		{
			this.setDead();
		}
	}

	@Override
	public void updateRiderPosition()
	{
		if (this.riddenByEntity != null)
		{
			this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + (inTrick && !grinding ? 0.25D : 0D), this.posZ);
			if (this.riddenByEntity instanceof EntityLivingBase)
			{
				((EntityLivingBase) this.riddenByEntity).renderYawOffset = this.rotationYaw + (this.isGoofy() ? -90F : 90F);
			}
		}
	}

	@Override
	public boolean interactFirst(EntityPlayer playerIn)
	{
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != playerIn)
		{
			return true;
		}
		else
		{
			if (!this.worldObj.isRemote)
			{
				playerIn.mountEntity(this);
			}

			return true;
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund)
	{

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound)
	{

	}

	public void handleLanding()
	{
		if (worldObj.isRemote && currentSpeed > 4)
		{
			float difference = Math.abs(Math.abs(this.angleOnJump) - Math.abs(MathHelper.wrapAngleTo180_float(this.rotationYaw)));
			if (difference > 50)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageStack(this.getEntityId()));
			}
		}
	}

	public void performStack()
	{
		if (!worldObj.isRemote && riddenByEntity != null)
		{
			riddenByEntity.attackEntityFrom(MrCrayfishSkateboardingMod.skateboardDamage, 2);
			if (riddenByEntity instanceof EntityLivingBase)
			{
				((EntityLivingBase) riddenByEntity).mountEntity((Entity) null);
			}
		}
	}

	public void startTrick(Trick trick)
	{
		inTrick = true;
		currentTrick = trick;
		if (trick instanceof Flip)
		{
			onGround = false;
		}
		if (trick instanceof Grind)
		{
			if (GrindHelper.canGrind(worldObj, posX, posY, posZ))
			{
				jumping = false;
				grinding = true;
				onGround = true;
			}
			else
			{
				inTrick = false;
				currentTrick = null;
			}
		}
	}

	public void resetTrick()
	{
		currentTrick = null;
		inTrick = false;
		inTrickTimer = 0;
	}

	public void jump()
	{
		if (grinding)
		{
			jumping = false;
			jumpingTimer = 0;
			grinding = false;
			allowOnce = true;
		}
		resetTrick();
		jumping = true;
		onGround = false;
		angleOnJump = rotationYaw;
	}

	private float interpolateRotation(float start, float end)
	{
		float f3;

		for (f3 = end - start; f3 < -180.0F; f3 += 360.0F)
		{
			;
		}

		while (f3 >= 180.0F)
		{
			f3 -= 360.0F;
		}

		return start + 0.1F * f3;
	}

	public boolean isPushed()
	{
		return pushed;
	}

	public void setPushed(boolean pushed)
	{
		this.pushed = pushed;
	}

	public boolean isJumping()
	{
		return jumping;
	}

	public void setJumping(boolean jumping)
	{
		this.jumping = jumping;
	}

	public boolean isInTrick()
	{
		return inTrick;
	}

	public void setInTrick(boolean inTrick)
	{
		this.inTrick = inTrick;
	}

	public Trick getCurrentTrick()
	{
		return currentTrick;
	}

	public void setCurrentTrick(Trick currentTrick)
	{
		this.currentTrick = currentTrick;
	}

	public boolean isGrinding()
	{
		return grinding;
	}

	public void setGrinding(boolean grinding)
	{
		this.grinding = grinding;
	}

	public boolean isGoofy()
	{
		return goofy;
	}

	public void setGoofy(boolean goofy)
	{
		this.goofy = goofy;
	}

	public boolean isSwitch_()
	{
		return switch_;
	}

	public void setSwitch_(boolean switch_)
	{
		this.switch_ = switch_;
	}

	public int getJumpingTimer()
	{
		return jumpingTimer;
	}

	public int getInTrickTimer()
	{
		return inTrickTimer;
	}

	public boolean isFlipped()
	{
		return flipped;
	}

	public void setFlipped()
	{
		this.flipped ^= true;
	}
}
