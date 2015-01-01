package com.mrcrayfish.skateboarding.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.skateboarding.network.PacketHandler;
import com.mrcrayfish.skateboarding.network.message.MessageUpdatePos;
import com.mrcrayfish.skateboarding.tricks.TrickHelper;
import com.mrcrayfish.skateboarding.tricks.TrickHelper.Tricks;

public class EntitySkateboard extends Entity
{
	public static final int SPEED = 6;
	public static final double MAX_SPEED = 0.3;

	public int jumpTimer = 0;
	public boolean jumping = false;
	public boolean inTrick = false;
	public Tricks currentTrick = null;

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

	private int life;

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (worldObj.isRemote)
		{
			onUpdateClient();
		}
		else
		{
			onUpdateServer();
		}
	}

	public void onUpdateClient()
	{

		if (this.riddenByEntity instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) this.riddenByEntity;
			float rotation = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, 0.1F);

			this.setRotation(rotation, this.rotationPitch);

			if (entity.moveForward > 0 && motionY == 0.0)
			{
				double maxMotionX = -Math.sin((double) Math.toRadians(rotation)) * SPEED * (double) entity.moveForward * 0.05D;
				double maxMotionZ = Math.cos((double) Math.toRadians(rotation)) * SPEED * (double) entity.moveForward * 0.05D;

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
			System.out.println("Collided Horizontally");
			this.motionX *= 0.5D;
			this.motionZ *= 0.5D;
		}
		
		if (this.isCollidedVertically)
		{
			//System.out.println("Collided Vertically");
			this.onGround = true;
		}

		if (this.ticksExisted % 40 == 0)
		{
			//System.out.println("MotionY: " + motionY);
			//System.out.println(this.onGround);
		}

		if (this.riddenByEntity instanceof EntityPlayer)
		{
			if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown() && jumping == false)
			{
				jumping = true;
				inTrick = true;
				currentTrick = Tricks.KICKFLIP;
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

		// 9 Seaford Road, Seaford Meadow

		if (jumping)
		{
			if (jumpTimer < 5)
				motionY = 0.42D - (double)jumpTimer * 0.04D;
			if (jumpTimer >= 5 && jumpTimer < 15)
				motionY = -0.5D;

			if(inTrick && currentTrick != null)
			{
				if(jumpTimer >= TrickHelper.getTrick(currentTrick).performTime())
				{
					System.out.println("Finished performing trick");
					currentTrick = null;
					inTrick = false;
				}
				
				if(this.onGround && TrickHelper.getTrick(currentTrick).performTime() > jumpTimer)
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

			jumpTimer++;
		} else {
			this.motionY -= 0.08D;
		}

		this.moveEntity(this.motionX, this.motionY, this.motionZ);

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

		if (prevPosX != posX | prevPosY != posY | prevPosZ != posZ)
			PacketHandler.INSTANCE.sendToServer(new MessageUpdatePos(this.getEntityId(), this.posX, this.posY, this.posZ));
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
			this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound)
	{
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund)
	{
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

	protected float interpolateRotation(float p_77034_1_, float p_77034_2_, float p_77034_3_)
	{
		float f3;

		for (f3 = p_77034_2_ - p_77034_1_; f3 < -180.0F; f3 += 360.0F)
		{
			;
		}

		while (f3 >= 180.0F)
		{
			f3 -= 360.0F;
		}

		return p_77034_1_ + p_77034_3_ * f3;
	}

}
