package com.mrcrayfish.skateboarding.entity;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySkateboard extends Entity
{
	public EntitySkateboard(World worldIn)
	{
		super(worldIn);
		this.preventEntitySpawning = true;
		this.setSize(1.0F, 0.5F);
		this.noClip = true;
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
	protected boolean shouldSetPosAfterLoading()
	{
		return true;
	}

	@Override
	protected void entityInit()
	{

	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entityIn)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getBoundingBox()
	{
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
		life++;
		if (life >= 10000)
		{
			this.setDead();
		}

		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) riddenByEntity;
			if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
			{
				if (Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown())
				{
					this.motionX = 0;
					this.motionZ = 0;
					float rotation = this.interpolateRotation(player.prevRenderYawOffset, player.renderYawOffset, 0.1F);
					this.motionX += -Math.sin((double) (rotation * (float) Math.PI / 180.0F)) * 6 * (double) player.moveForward * 0.05000000074505806D;
					this.motionZ += Math.cos((double) (rotation * (float) Math.PI / 180.0F)) * 6 * (double) player.moveForward * 0.05000000074505806D;
					this.moveEntity(this.motionX, 0, this.motionZ);
				}
			}
		}
	}

	@Override
	public void updateRidden()
	{
		super.updateRidden();

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
