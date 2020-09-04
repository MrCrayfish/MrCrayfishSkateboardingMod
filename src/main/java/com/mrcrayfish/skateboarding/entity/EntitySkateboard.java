package com.mrcrayfish.skateboarding.entity;

import com.mrcrayfish.skateboarding.MrCrayfishSkateboardingMod;
import com.mrcrayfish.skateboarding.api.trick.Flip;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.api.trick.Trick;
import com.mrcrayfish.skateboarding.block.attributes.Angled;
import com.mrcrayfish.skateboarding.network.PacketHandler;
import com.mrcrayfish.skateboarding.network.message.MessageStack;
import com.mrcrayfish.skateboarding.network.message.MessageUpdatePos;
import com.mrcrayfish.skateboarding.util.ComboBuilder;
import com.mrcrayfish.skateboarding.util.GrindHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySkateboard extends Entity
{
	private static final DataParameter<Float> CURRENT_SPEED = EntityDataManager.createKey(EntitySkateboard.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> MAX_SPEED = EntityDataManager.createKey(EntitySkateboard.class, DataSerializers.FLOAT);
	private static final DataParameter<Boolean> JUMPING = EntityDataManager.createKey(EntitySkateboard.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> GRINDING = EntityDataManager.createKey(EntitySkateboard.class, DataSerializers.BOOLEAN);

	public ComboBuilder combo = new ComboBuilder();

	public float currentSpeed;

	private boolean pushed = false;
	private int jumpingTimer = 0;

	private int inTrickTimer = 0;
	private Trick currentTrick = null;

	private boolean allowJumpOnce = false;
	private boolean goofy = false;
	private boolean switch_ = false;
	private boolean flipped = false;

	private IBlockState angledBlockState;
	private Angled angledBlock;

	@SideOnly(Side.CLIENT)
	private boolean onAngledBlock = false;
	@SideOnly(Side.CLIENT)
	public float angleOnJump;

	@SideOnly(Side.CLIENT)
	public double boardRotation;
	@SideOnly(Side.CLIENT)
	public double boardRotationX;
	@SideOnly(Side.CLIENT)
	public double boardRotationY;
	@SideOnly(Side.CLIENT)
	public double boardRotationZ;
	@SideOnly(Side.CLIENT)
	public double prevBoardRotation;
	@SideOnly(Side.CLIENT)
	public double prevBoardRotationX;
	@SideOnly(Side.CLIENT)
	public double prevBoardRotationY;
	@SideOnly(Side.CLIENT)
	public double prevBoardRotationZ;
	
	public boolean needsCameraUpdate;
	public boolean canCameraIncrement;
	public float cameraIncrement;
	public float cameraYaw;

	private int lerpSteps;
	private double lerpX;
	private double lerpY;
	private double lerpZ;
	private double lerpYaw;
	private double lerpPitch;

	public EntitySkateboard(World worldIn)
	{
		super(worldIn);
		this.setSize(0.5F, 0.25F);
		this.stepHeight = 0.3F;
	}

	public EntitySkateboard(World worldIn, double x, double y, double z)
	{
		this(worldIn);
		this.setPosition(x, y, z);
	}

	@Override
	protected void entityInit()
	{
		this.dataManager.register(CURRENT_SPEED, 0F);
		this.dataManager.register(MAX_SPEED, 8F);
		this.dataManager.register(JUMPING, false);
		this.dataManager.register(GRINDING, false);
	}

	@Override
	public double getMountedYOffset()
	{
		return 0.5F;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		super.onUpdate();
		this.tickLerp();

		if(world.isRemote)
		{
			combo.update(this);
			prevBoardRotation = boardRotation;
			prevBoardRotationX = boardRotationX;
			prevBoardRotationY = boardRotationY;
			prevBoardRotationZ = boardRotationZ;
		}

		updateSpeed();
		updateMotion();
		updateDirection();
		updateTrick();

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

		this.move(MoverType.SELF, motionX, motionY, motionZ);
	}

	private void updateDirection()
	{
		Entity entity = getControllingPassenger();
		if (entity instanceof EntityLivingBase)
		{
			rotationYaw = entity.rotationYaw;
		}
	}

	private void updateSpeed()
	{
		/* Will only execute code if player is riding skateboard */
		Entity entity = getControllingPassenger();
		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase entityLiving = (EntityLivingBase) entity;

			/* Handles pushing and updates speed */
			if(entityLiving.moveForward > 0.0F && !pushed && !isGrinding())
			{
				currentSpeed += 1.0D;
				if(currentSpeed > getMaxSpeed())
				{
					currentSpeed = getMaxSpeed();
				}
				pushed = true;
			}
			else if(entityLiving.moveForward == 0.0F)
			{
				pushed = false;
			}

			if(collidedHorizontally)
			{
				this.currentSpeed *= 0.75F;
			}

			if(!isGrinding())
			{
				this.currentSpeed *= 0.99F;
			}
		}
		else
		{
			currentSpeed *= 0.5F;
		}
		setCurrentSpeed(currentSpeed);
	}

	private void updateMotion()
	{
		Entity entity = getControllingPassenger();
		if (entity instanceof EntityLivingBase)
		{


		}

		float f1 = MathHelper.sin(rotationYaw * 0.017453292F) / 20F; //Divide by 20 ticks
		float f2 = MathHelper.cos(rotationYaw * 0.017453292F) / 20F;
		this.motionX = (-currentSpeed * f1);
		this.motionY -= 0.08D;
		this.motionZ = ( currentSpeed * f2);
	}

	private void updateTrick()
	{
		if(world.isRemote)
		{
			if(isJumping())
			{
				//if (jumpingTimer < 10)
				//motionY = 0.5D - (double) jumpingTimer * 0.03D;
				if(world.isRemote)
				{
					if(currentTrick != null)
					{
						inTrickTimer++;

						currentTrick.updateBoard(this);

						if(currentTrick instanceof Flip)
						{
							Flip flip = (Flip) currentTrick;
							if(inTrickTimer > flip.performTime())
							{
								if(world.isRemote)
								{
									boolean direction = angleOnJump < rotationYaw;
									if((!isGoofy() && isSwitch_()) || (isGoofy() && !isSwitch_()))
										direction = !direction;
									combo.addTrick(getCurrentTrick(), Math.abs(angleOnJump - rotationYaw), direction);
								}
								getCurrentTrick().onEnd(this);
								resetTrick();
							}
						}
					}

					if(onGround && !isGrinding())
					{
						setJumping(false);
						jumpingTimer = 0;
						if(currentTrick != null)
						{
							if(currentTrick instanceof Flip)
							{
								resetTrick();
								PacketHandler.INSTANCE.sendToServer(new MessageStack(this.getEntityId()));
							}
						}
						handleLanding();
					}

					jumpingTimer++;
				}
			}
			else if(isGrinding())
			{
				if (currentTrick instanceof Grind)
				{
					if(!needsCameraUpdate)
					{
						//TODO:: If easy mode?
						//f = EnumFacing.fromAngle(this.angleOnJump).rotateY().getHorizontalIndex() * 90F;
					}

					inTrickTimer++;

					prevBoardRotationX = boardRotationX;
					prevBoardRotationY = boardRotationY;
					prevBoardRotationZ = boardRotationZ;

					currentTrick.updateBoard(this);

					if (!GrindHelper.canGrind(world, this.posX, this.posY, this.posZ))
					{
						getCurrentTrick().onEnd(this);
						resetTrick();
						setGrinding(false);
						onGround = false;
					}
					else
					{
						combo.addPoints(getCurrentTrick().points());
					}

					world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, posX, posY, posZ, 0, 0, 0, 0);
				}

				//TODO:: If easy mode?
				//rotationYaw = EnumFacing.fromAngle(this.angleOnJump).rotateY().getHorizontalIndex() * 90F;
			}
		}
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key)
	{
		super.notifyDataManagerChange(key);
	}

	private void updateCamera()
	{
		if(needsCameraUpdate && world.isRemote)
		{
			canCameraIncrement = true;
			cameraYaw -= cameraIncrement;
			if(Math.floor(cameraYaw) == 0F)
			{
				needsCameraUpdate = false;
			}
		}
	}

	/**
	 * Smooths the rendering on servers
	 */
	private void tickLerp()
	{
		if(this.lerpSteps > 0 && !this.canPassengerSteer())
		{
			double d0 = this.posX + (this.lerpX - this.posX) / (double) this.lerpSteps;
			double d1 = this.posY + (this.lerpY - this.posY) / (double) this.lerpSteps;
			double d2 = this.posZ + (this.lerpZ - this.posZ) / (double) this.lerpSteps;
			double d3 = MathHelper.wrapDegrees(this.lerpYaw - (double) this.rotationYaw);
			this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.lerpSteps);
			this.rotationPitch = (float) ((double) this.rotationPitch + (this.lerpPitch - (double) this.rotationPitch) / (double) this.lerpSteps);
			--this.lerpSteps;
			this.setPosition(d0, d1, d2);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
	{
		this.lerpX = x;
		this.lerpY = y;
		this.lerpZ = z;
		this.lerpYaw = (double) yaw;
		this.lerpPitch = (double) pitch;
		this.lerpSteps = 10;
	}

	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand)
	{
		if(player.isSneaking())
		{
			this.setDead();
		}
		else
		{
			player.startRiding(this, false);
			prevRotationYaw = rotationYaw = player.rotationYaw - 90F;
		}
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
			if (!this.world.isRemote)
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

	public void handleLanding()
	{
		if (world.isRemote)
		{
			int difference = (int) (Math.abs(angleOnJump - rotationYaw) % 180);
			if(currentSpeed > 4 && difference > 60 && difference < 120)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageStack(this.getEntityId()));
			}
			else
			{
				int dir = angleOnJump < rotationYaw ? 1 : -1;
				float fakie = (Math.abs(angleOnJump - rotationYaw) / 180) % 2;
				if(fakie > 0.66 && fakie < 1.33)
				{
					this.rotationYaw += 180F * dir;
					this.prevRotationYaw = this.rotationYaw;
					this.setSwitch_(!isSwitch_());
					this.setFlipped();
					this.setCameraUpdate(180F * dir);
				}
			}
		}
		//print();
	}

	public void performStack()
	{
		Entity riding = getControllingPassenger();
		if (!world.isRemote && riding != null)
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
			Flip flip = (Flip) trick;
			if(!isJumping()) 
			{
				resetTrick();
				return;
			}
			onGround = false;
		}
		
		if (trick instanceof Grind)
		{
			if (GrindHelper.canGrind(world, posX, posY, posZ))
			{
				handleLanding();
				combo.addTrick(trick, 0, true);
				setJumping(false);
				setGrinding(true);
				jumpingTimer = 0;
				onGround = false;
				float newYaw = (float) Math.floor((rotationYaw + 45F) / 90F) * 90F;
				turnToDirection(newYaw);
				moveToDirectionCenter(newYaw);
			}
			else
			{
				currentTrick = null;
			}
		}
		
		if(currentTrick != null)
		{
			currentTrick.onStart(this);
		}
	}

	public void resetTrick()
	{
		if(currentTrick instanceof Flip)
		{
			prevBoardRotation = 0F;
			prevBoardRotationX = 0F;
			prevBoardRotationY = 0F;
			prevBoardRotationZ = 0F;
		}
		currentTrick = null;
		inTrickTimer = 0;
		boardRotation = 0F;
		boardRotationX = 0F;
		boardRotationY = 0F;
		boardRotationZ = 0F;
	}

	public void jump(double height)
	{
		if (isGrinding())
		{
			setGrinding(false);
			jumpingTimer = 0;
			allowJumpOnce = true;
		}
		resetTrick();
		setJumping(true);
		onGround = false;
		angleOnJump = rotationYaw;
		prevRotationYaw = rotationYaw;
		motionY = Math.sqrt((height + 1) * 0.22);
	}
	
	public void turnToDirection(float newYaw) 
	{
		Entity entity = getControllingPassenger();
		float startYaw = entity.rotationYaw - 90F;
		rotationYaw = newYaw;
		prevRotationYaw = newYaw;
		this.setCameraUpdate(newYaw - startYaw);	
	}
	
	public void moveToDirectionCenter(float yaw)
	{
		EnumFacing facing = EnumFacing.fromAngle(yaw);
		Axis axis = facing.getAxis();
		switch(axis)
		{
		case X:
			this.motionX = 0;
			this.lastTickPosX = this.prevPosX = this.posX = Math.floor(posX) + 0.5;
			PacketHandler.INSTANCE.sendToServer(new MessageUpdatePos(getEntityId(), Math.floor(posX) + 0.5, posY, posZ));
			break;
		case Z:
			this.motionZ = 0;
			this.lastTickPosZ = this.prevPosZ = this.posZ = Math.floor(posZ) + 0.5;
			PacketHandler.INSTANCE.sendToServer(new MessageUpdatePos(getEntityId(), posX, posY, Math.floor(posZ) + 0.5));
			break;
		default:
			break;
		}
		this.updateBoundingBox(posX, posY, posZ);
		this.updatePassenger(getControllingPassenger());
	}
	
	public void updateBoundingBox(double x, double y, double z)
	{
		float f = this.width / 2.0F;
        float f1 = this.height;
        this.setEntityBoundingBox(new AxisAlignedBB(x - (double) f, y, z - (double)f, x + (double)f, y + (double)f1, z + (double)f));
	}

	public boolean isPushed()
	{
		return pushed;
	}

	public void setPushed(boolean pushed)
	{
		this.pushed = pushed;
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
	
	public boolean isOnAngledBlock() 
	{
		return onAngledBlock;
	}
	
	public IBlockState getAngledBlockState() 
	{
		return angledBlockState;
	}
	
	public Angled getAngledBlock() 
	{
		return angledBlock;
	}
	
	public void updateAngledBlock()
	{
		IBlockState inside = world.getBlockState(new BlockPos(posX, posY, posZ));
		if(inside.getBlock() instanceof Angled) 
		{
			this.onAngledBlock = true;
			this.angledBlockState = inside;
			this.angledBlock = (Angled) inside.getBlock();
			return;
		}
		
		IBlockState below = world.getBlockState(new BlockPos(posX, posY - 1, posZ));
		if(below.getBlock() instanceof Angled) 
		{
			this.onAngledBlock = true;
			this.angledBlockState = below;
			this.angledBlock = (Angled) below.getBlock();
			return;
		}
		
		if(isGrinding())
		{
			IBlockState underground = world.getBlockState(new BlockPos(posX, posY - 2, posZ));
			if(underground.getBlock() instanceof Angled) 
			{
				this.onAngledBlock = true;
				this.angledBlockState = underground;
				this.angledBlock = (Angled) underground.getBlock();
				return;
			}
		}
		
		this.onAngledBlock = false;
		this.angledBlockState = null;
		this.angledBlock = null;
	}
	
	public void setCameraUpdate(float amount) 
	{
		this.needsCameraUpdate = true;
		this.cameraIncrement = amount / 4F;
		this.cameraYaw = amount;
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

	public float getCurrentSpeed()
	{
		return this.dataManager.get(CURRENT_SPEED);
	}

	public void setCurrentSpeed(float currentSpeed)
	{
		this.dataManager.set(CURRENT_SPEED, currentSpeed);
	}

	public float getMaxSpeed()
	{
		return this.dataManager.get(MAX_SPEED);
	}

	public void setMaxSpeed(float maxSpeed)
	{
		this.dataManager.set(MAX_SPEED, maxSpeed);
	}

	public boolean isJumping()
	{
		return this.dataManager.get(JUMPING);
	}

	public void setJumping(boolean jumping)
	{
		this.dataManager.set(JUMPING, jumping);
	}

	public boolean isGrinding()
	{
		return this.dataManager.get(GRINDING);
	}

	public void setGrinding(boolean grinding)
	{
		this.dataManager.set(GRINDING, grinding);
	}
}