package com.mrcrayfish.skateboarding.entity;

import com.mrcrayfish.skateboarding.MrCrayfishSkateboardingMod;
import com.mrcrayfish.skateboarding.api.TrickProperties.Facing;
import com.mrcrayfish.skateboarding.api.trick.Flip;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.api.trick.Trick;
import com.mrcrayfish.skateboarding.network.PacketHandler;
import com.mrcrayfish.skateboarding.network.message.MessageStack;
import com.mrcrayfish.skateboarding.util.ComboBuilder;
import com.mrcrayfish.skateboarding.util.GrindHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySkateboard extends Entity
{
	public ComboBuilder combo = new ComboBuilder();

	public double currentSpeed = 0.0;
	public double maxSpeed = 8.0;
	private boolean allowOnce = false;

	private boolean pushed = false;
	private boolean jumping = false;
	private int jumpingTimer = 0;

	private int inTrickTimer = 0;
	private Trick currentTrick = null;

	private boolean grinding = false;
	private boolean goofy = false;
	private boolean switch_ = false;
	private boolean flipped = false;

	public float angleOnJump;
	private EnumFacing faceOnJump;
	private float angleOnTrick;
	private int rotation;

	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;
	
	public double boardYaw;
	public double prevBoardYaw;
	
	public double boardRotationX;
	public double boardRotationY;;
	public double boardRotationZ;
	public double prevBoardRotationX;
	public double prevBoardRotationY;
	public double prevBoardRotationZ;

	public EntitySkateboard(World worldIn)
	{
		super(worldIn);
		this.setSize(1.0F, 0.5F);
	}

	public EntitySkateboard(World worldIn, double x, double y, double z)
	{
		this(worldIn);
		this.setPosition(x, y, z);
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
	protected void entityInit()
	{

	}

	@Override
	public double getMountedYOffset()
	{
		return 0.5F + (0.04F * rand.nextDouble()) * (currentSpeed / maxSpeed);
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

	
	/*@Override
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
	}*/
	
	/*@SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
    {
		this.prevPosX = this.posX = x;
		this.prevPosY = this.posY = y;
		this.prevPosZ = this.posX = z;
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z)
	{
		this.velocityX = this.motionX = x;
		this.velocityY = this.motionY = y;
		this.velocityZ = this.motionZ = z;
	}*/

	private int life;

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if(worldObj.isRemote)
		{
			combo.update(this);
			
			prevRotationYaw = rotationYaw;
			prevBoardYaw = boardYaw;
			prevBoardRotationX = boardRotationX;
			prevBoardRotationY = boardRotationY;
			prevBoardRotationZ = boardRotationZ;
			
			/* Will only execute code if player is riding skateboard */
			if (getControllingPassenger() != null)
			{
				EntityLivingBase entity = (EntityLivingBase) this.getControllingPassenger();
				
				/* Handles pushing */
				if (entity.moveForward > 0 && !pushed && !grinding)
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
				
				/* If skateboard is not jumping, allow turning. When player jumps
				 * from grinding, give exception to jump off in a direction using
				 * allowOnce. */
				if (!jumping || allowOnce)
				{
					float f = entity.rotationYaw;
	
					/* If grinding, set direction to direction of grinding. */
					if (grinding)
					{
						//TODO:: If easy mode?
						//f = EnumFacing.fromAngle(this.angleOnJump).rotateY().getHorizontalIndex() * 90F;
					}
	
					this.motionX = -Math.sin((double) (f * (float) Math.PI / 180.0F)) * currentSpeed / 16D;
					this.motionZ = Math.cos((double) (f * (float) Math.PI / 180.0F)) * currentSpeed / 16D;
					allowOnce = false;
				}
			}
			else
			{
				/* If no player riding, make the board stop */
				this.motionX = 0.0D;
				this.motionZ = 0.0D;
			}
			
			/* If collided horizontally, slow current speed by 75% */
			if (isCollidedHorizontally)
			{
				this.currentSpeed *= 0.75D;
			}
			
			/* Gravity? */
			this.motionY -= 0.08D;
	
			/* If grinding, make position of skateboard go to center of block.
			if (grinding)
			{
				double[] offsets = ((Grind) getCurrentTrick()).offsetBoardPosition(this);
				EnumFacing face = EnumFacing.fromAngle(this.angleOnJump).rotateY();
				if (face == EnumFacing.NORTH | face == EnumFacing.SOUTH)
				{
					this.setPosition(Math.floor(this.posX) + 0.5 + offsets[0], Math.floor(this.posY) + offsets[1], this.posZ + offsets[2]);
				}
				if (face == EnumFacing.EAST | face == EnumFacing.WEST)
				{
					this.setPosition(this.posX + offsets[0], Math.floor(this.posY) + offsets[1], Math.floor(this.posZ) + 0.5 + offsets[2]);
				}
			}*/
	
			if (jumping)
			{
				if (this.getControllingPassenger() instanceof EntityLivingBase)
				{
					EntityLivingBase entity = (EntityLivingBase) this.getControllingPassenger();
					this.rotationYaw = entity.rotationYaw - 90F;
				}
				
				if (jumpingTimer < 10)
					motionY = 0.5D - (double) jumpingTimer * 0.03D;
				
				if (currentTrick != null)
				{
					inTrickTimer++;
					
					currentTrick.updateBoard(this);
	
					if (currentTrick instanceof Flip)
					{
						Flip flip = (Flip) currentTrick;
						if (inTrickTimer > flip.performTime())
						{
							if (worldObj.isRemote)
								combo.addTrick(getCurrentTrick(), Math.abs(angleOnJump - rotationYaw));
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
					System.out.println("On Landed");
					jumping = false;
					jumpingTimer = 0;
					resetTrick();
					handleLanding();
				}
	
				jumpingTimer++;
			}
			
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			
			if (grinding)
			{
				if (currentTrick instanceof Grind)
				{
					inTrickTimer++;
					
					prevBoardYaw = boardYaw;
					prevBoardRotationX = boardRotationX;
					prevBoardRotationY = boardRotationY;
					prevBoardRotationZ = boardRotationZ;
					
					currentTrick.updateBoard(this);
	
					Grind grind = (Grind) currentTrick;
					if (!GrindHelper.canGrind(worldObj, this.posX, this.posY, this.posZ))
					{
						getCurrentTrick().onEnd(this);
						resetTrick();
						grinding = false;
						onGround = false;
					}
					else
					{
						combo.addPoints(getCurrentTrick().points());
					}
					
					worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, posX, posY, posZ, 0, 0, 0, 0);
				}
				
				//TODO: If easy mode
				//this.rotationYaw = (int) (angleOnJump + (rotationYaw - angleOnJump) + 45) / 90 * 90;
			}
			
			if(!jumping)
			{
				this.rotationPitch = 0.0F;
				double init = (double) this.rotationYaw;
				double newX = this.prevPosX - this.posX;
				double newZ = this.prevPosZ - this.posZ;
	
				if (newX * newX + newZ * newZ > 0.001D)
				{
					init = (double) ((float) (Math.atan2(newZ, newX) * 180.0D / Math.PI));
					System.out.println(init);
				}
				
				
	
				double d12 = MathHelper.wrapDegrees(init - (double) this.rotationYaw);
	
				if (d12 > 20.0D)
				{
					d12 = 20.0D;
				}
	
				if (d12 < -20.0D)
				{
					d12 = -20.0D;
				}
				
				this.rotationYaw = (float) ((double) this.rotationYaw + d12);
			}
	
			this.motionY *= 0.9800000190734863D;
			if (!grinding)
			{
				this.currentSpeed *= 0.99D;
			}
		}
	}

	public void onUpdateServer()
	{
		life++;
		if (life >= 10000)
		{
			//this.setDead();
		}
	}
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, ItemStack stack, EnumHand hand) 
	{
		player.startRiding(this, false);
		return EnumActionResult.SUCCESS;
	}
	
	/*@Override
	public void updateRidden() 
	{
		if(getControllingPassenger() != null)
		{
			((EntityLivingBase) getControllingPassenger()).setRenderYawOffset(rotationYaw + (this.isGoofy() ? -90F : 90F));
		}
	}*/
	
	@Override
	public void updatePassenger(Entity passenger) 
	{
		super.updatePassenger(passenger);
	}

	/*@Override
	public void updateRiderPosition()
	{
		if (this.riddenByEntity != null)
		{
			this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + (currentTrick != null && !grinding ? 0.25D : 0D), this.posZ);
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
	}*/

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund)
	{

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound)
	{

	}
	
	public void push() 
	{
		this.currentSpeed += 1;
	}

	public void handleLanding()
	{
		if (worldObj.isRemote && currentSpeed > 4)
		{
			int difference = (int) (Math.abs(angleOnJump - rotationYaw) % 180);
			System.out.println("Angle Change in jump: " + difference);
			if(difference > 60 && difference < 120)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageStack(this.getEntityId()));
			}
		}
		print();
	}

	public int getDifferenceWithFix(int initAngle, int newAngle)
	{
		if (initAngle == 0 && newAngle == 270)
		{
			return 90;
		}
		if (initAngle == 270 && newAngle == 0)
		{
			return -90;
		}
		return initAngle - newAngle;
	}

	public void performStack()
	{
		Entity riding = getControllingPassenger();
		if (!worldObj.isRemote && riding != null)
		{
			riding.attackEntityFrom(MrCrayfishSkateboardingMod.skateboardDamage, 2);
			if (riding instanceof EntityLivingBase)
			{
				riding.dismountRidingEntity();
			}
		}
	}

	public void startTrick(Trick trick)
	{
		currentTrick = trick;
		if (trick instanceof Flip)
		{
			onGround = false;
		}
		if (trick instanceof Grind)
		{
			if (GrindHelper.canGrind(worldObj, posX, posY, posZ))
			{
				combo.addTrick(trick, 0);
				jumping = false;
				jumpingTimer = 0;
				grinding = true;
				onGround = false;
			}
			else
			{
				currentTrick = null;
			}
		}
		trick.onStart(this);
	}

	public Facing getFacing()
	{
		int initFacing = EnumFacing.fromAngle(this.angleOnJump + 90F).getHorizontalIndex() * 90;
		int newFacing = EnumFacing.fromAngle(this.rotationYaw + 90F).getHorizontalIndex() * 90;
		int difference = getDifferenceWithFix(initFacing, newFacing);
		switch (difference)
		{
		case 90:
			return Facing.FRONT;
		case -90:
			return Facing.BACK;
		case 180:
			return Facing.SWITCH;
		case -180:
			return Facing.SWITCH;
		case 270:
			return Facing.BACK;
		case -270:
			return Facing.FRONT;
		}
		return Facing.SAME;
	}

	public void resetTrick()
	{
		if(currentTrick instanceof Flip)
		{
			prevBoardYaw = 0F;
			prevBoardRotationX = 0F;
			prevBoardRotationY = 0F;
			prevBoardRotationZ = 0F;
		}
		currentTrick = null;
		inTrickTimer = 0;
		boardYaw = 0F;
		boardRotationX = 0F;
		boardRotationY = 0F;
		boardRotationZ = 0F;
	}

	public void jump()
	{
		System.out.println("Jumping");
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
		angleOnJump = this.rotationYaw;
		//print();
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
		return currentTrick != null;
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
	
	@Override
	public Entity getControllingPassenger() 
	{
		if(getPassengers().size() > 0)
		{
			return getPassengers().get(0);
		}
		return null;
	}
	
	public void print() 
	{
		EntityLivingBase entity = (EntityLivingBase) this.getControllingPassenger();
		System.out.println(entity.rotationYaw - 90F);
		System.out.println("Rotation Yaw:" + rotationYaw);
		System.out.println("Prev Rotation Yaw:" + prevRotationYaw);
		System.out.println("Current Speed: " + currentSpeed);
		System.out.println("Max Speed: " + maxSpeed);
		System.out.println("Allow Once: " + allowOnce);
		System.out.println("Pushed: " + pushed);
		System.out.println("Jumping: " + jumping);
		System.out.println("Jumping Timer: " + jumpingTimer);
		System.out.println("Trick Timer: " + inTrickTimer);
		System.out.println("Current Trick: " + currentTrick);
		System.out.println("Grinding: " + grinding);
		System.out.println("Goofy: " + goofy);
		System.out.println("Switch: " + switch_);
		System.out.println("Flipped: " + flipped);
		System.out.println("Angle On Jump: " + angleOnJump);
		System.out.println("Face On Jump: " + faceOnJump);
		System.out.println("Angle On Trick: " + angleOnTrick);
		System.out.println("Rotation: " + rotation);
		System.out.println("Board Yaw: " + boardYaw);
		System.out.println("Prev Board Yaw: " + prevBoardYaw);
		System.out.println("Board Rotation X: " + boardRotationX);
		System.out.println("Board Rotation Y: " + boardRotationY);
		System.out.println("Board Rotation Z: " + boardRotationZ);
		System.out.println("Prev Board Rotation X: " + prevBoardRotationX);
		System.out.println("Prev Board Rotation Y: " + prevBoardRotationY);
		System.out.println("Prev Board Rotation Z: " + prevBoardRotationZ);
	}
}
