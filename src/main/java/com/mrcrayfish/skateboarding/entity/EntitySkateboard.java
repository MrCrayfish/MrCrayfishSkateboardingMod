package com.mrcrayfish.skateboarding.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.skateboarding.api.trick.Flip;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.api.trick.Trick;
import com.mrcrayfish.skateboarding.util.GrindHelper;

public class EntitySkateboard extends Entity
{
	public double currentSpeed = 0.0;
	public double maxSpeed = 8.0;

	private boolean pushed = false;
	public boolean jumping = false;
	public int jumpingTimer = 0;
	public boolean inTrick = false;
	public int inTrickTimer = 0;
	public Trick currentTrick = null;
	public boolean grinding = false;

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
		return true;
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
		if (this.riddenByEntity != null)
		{
			if (this.ticksExisted % 60 == 0)
			{
				System.out.println("");
				System.out.println("pushed:" + pushed);
				System.out.println("jumping:" + jumping);
				System.out.println("jumpingTimer:" + jumpingTimer);
				System.out.println("inTrick:" + inTrick);
				System.out.println("inTrickTimer:" + inTrickTimer);
				System.out.println("currentTrick:" + currentTrick);
				System.out.println("grinding:" + grinding);
			}
		}

		super.onUpdate();

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

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
			 * If skateboard is not jumping, allow turning. Exception if
			 * grinding
			 */
			if (!jumping)
			{
				float f = this.riddenByEntity.rotationYaw;
				// System.out.println(f);
				if (grinding)
				{
					f = EnumFacing.fromAngle(this.rotationYaw).rotateY().getHorizontalIndex() * 90F;
				}
				this.motionX = -Math.sin((double) (f * (float) Math.PI / 180.0F)) * currentSpeed / 16D;
				this.motionZ = Math.cos((double) (f * (float) Math.PI / 180.0F)) * currentSpeed / 16D;

				// System.out.println(motionX * motionX + motionZ * motionZ);
			}
		}

		/** If grinding, make position of skateboard go to edge of block. */
		if (grinding)
		{
			EnumFacing face = EnumFacing.fromAngle(this.rotationYaw).rotateY();
			// System.out.println("Setting Pos");
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
				motionY = 0.5D - (double) jumpingTimer * 0.02D;

			if (inTrick && currentTrick != null)
			{
				inTrickTimer++;

				if (currentTrick instanceof Flip)
				{
					Flip flip = (Flip) currentTrick;
					if (inTrickTimer > flip.performTime())
					{
						System.out.println("Trick Finished");
						currentTrick = null;
						inTrick = false;
						inTrickTimer = 0;
					}
					else if (this.onGround && flip.performTime() > inTrickTimer)
					{
						System.out.println("Failed Trick");
						currentTrick = null;
						inTrick = false;
						jumping = false;
						inTrickTimer = 0;
						jumpingTimer = 0;
					}
				}
			}

			if (this.onGround && !grinding)
			{
				System.out.println("Stopping Jump");
				currentTrick = null;
				inTrick = false;
				jumping = false;
				inTrickTimer = 0;
				jumpingTimer = 0;
			}

			jumpingTimer++;
		}

		if (grinding)
		{
			// System.out.println("Stage 1");
			if (inTrick && currentTrick != null)
			{
				// System.out.println("Stage 2");
				if (currentTrick instanceof Grind)
				{
					// System.out.println("Stage 3");
					inTrickTimer++;

					Grind grind = (Grind) currentTrick;
					if (!GrindHelper.canGrind(worldObj, this.posX, this.posY, this.posZ))
					{
						grinding = false;
						currentTrick = null;
						inTrick = false;
						inTrickTimer = 0;
					}
				}
			}
		}
		this.motionY -= 0.08D;

		this.moveEntity(this.motionX, this.motionY, this.motionZ);

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
				((EntityLivingBase) this.riddenByEntity).renderYawOffset = this.rotationYaw + 90F;
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

	public void jump()
	{
		if (grinding)
		{
			currentTrick = null;
			inTrick = false;
			jumping = false;
			inTrickTimer = 0;
			jumpingTimer = 0;
			grinding = false;
		}
		jumping = true;
		onGround = false;
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
}
