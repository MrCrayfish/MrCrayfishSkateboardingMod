package com.mrcrayfish.skateboarding.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.skateboarding.client.Keybinds;
import com.mrcrayfish.skateboarding.network.PacketHandler;
import com.mrcrayfish.skateboarding.network.message.MessageTrick;
import com.mrcrayfish.skateboarding.tricks.TrickHelper;
import com.mrcrayfish.skateboarding.tricks.TrickHelper.Tricks;

public class EntitySkateboard extends Entity
{
	public static final int SPEED = 8;
	public static final double MAX_SPEED = 0.3;

	public int jumpTimer = 0;
	public boolean jumping = false;
	public boolean inTrick = false;
	public Tricks currentTrick = null;

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
	public AxisAlignedBB getCollisionBox(Entity entityIn)
	{
		// return this.getBoundingBox();
		return null;
	}

	@Override
	public AxisAlignedBB getBoundingBox()
	{
		// return this.getEntityBoundingBox();
		return null;
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

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.riddenByEntity instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) this.riddenByEntity;

			if (entity.moveForward > 0 && this.isCollidedVertically)
			{
				float f = this.riddenByEntity.rotationYaw;
				double maxMotionX = -Math.sin((double) (f * (float) Math.PI / 180.0F)) * SPEED * (double) entity.moveForward * 0.05000000074505806D;
				double maxMotionZ = Math.cos((double) (f * (float) Math.PI / 180.0F)) * SPEED * (double) entity.moveForward * 0.05000000074505806D;

				this.motionX += maxMotionX / 16D;
				this.motionZ += maxMotionZ / 16D;

				if (Math.abs(this.motionX) >= Math.abs(maxMotionX))
				{
					this.motionX = maxMotionX;
				}
				if (Math.abs(this.motionZ) >= Math.abs(maxMotionZ))
				{
					this.motionZ = maxMotionZ;
				}
			}
		}

		if (this.isCollidedHorizontally)
		{
			this.motionX *= 0.5D;
			this.motionZ *= 0.5D;
		}

		if (this.riddenByEntity instanceof EntityPlayer)
		{
			if (worldObj.isRemote && Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown() && jumping == false)
			{

			}
		}

		if (Math.abs(this.motionX) < 0.005D)
		{
			this.motionX = 0.0D;
		}

		if (Math.abs(this.motionY) < 0.005D)
		{
			this.motionY = 0.0D;
		}

		if (Math.abs(this.motionZ) < 0.005D)
		{
			this.motionZ = 0.0D;
		}

		if (jumping)
		{
			jumpTimer++;

			if (jumpTimer <= 5)
				motionY = 0.5D - (double) jumpTimer * 0.04D;
			if (jumpTimer > 5 && jumpTimer <= 15)
				motionY = -0.5D;

			if (inTrick && currentTrick != null)
			{
				if (jumpTimer >= TrickHelper.getTrick(currentTrick).performTime() - 1)
				{
					System.out.println("Finished performing trick");
					currentTrick = null;
					inTrick = false;
				}
				else if (this.onGround && TrickHelper.getTrick(currentTrick).performTime() > jumpTimer)
				{
					System.out.println("Failed to perform trick");
					currentTrick = null;
					inTrick = false;
					jumping = false;
					jumpTimer = 0;
				}
			}

			if (jumpTimer >= 20 | this.onGround)
			{
				System.out.println("Successfully performed the trick " + jumpTimer);
				currentTrick = null;
				inTrick = false;
				jumping = false;
				jumpTimer = 0;
			}
		}
		else
		{
			this.motionY -= 0.08D;
		}

		this.moveEntity(this.motionX, this.motionY, this.motionZ);

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

		if (this.riddenByEntity instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) this.riddenByEntity;
			if (entity.moveForward == 0.0)
			{
				this.motionX *= 0.85D;
				this.motionZ *= 0.85D;
			}
		}

		this.motionY *= 0.9800000190734863D;
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
			this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + (inTrick ? 0.25D : 0D), this.posZ);
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

	public void startTrick()
	{
		jumping = true;
		inTrick = true;
		currentTrick = Tricks.TREFLIP;
		onGround = false;
	}

	public void jump()
	{
		this.jumping = true;
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		Entity entity = Minecraft.getMinecraft().thePlayer.ridingEntity;
		if (entity != null && entity instanceof EntitySkateboard)
		{
			EntitySkateboard skateboard = (EntitySkateboard) entity;
			if (Keybinds.ollie.isPressed())
			{
				skateboard.startTrick();
				PacketHandler.INSTANCE.sendToServer(new MessageTrick(skateboard.getEntityId(), 0));
			}
			
			if(Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed())
			{
				System.out.println("Hey");
			}
		}
	}
}
