package com.mrcrayfish.skateboarding.tileentity;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityTextureable extends TileEntity 
{
	private ResourceLocation texture = null;
	
	public boolean setTexture(ItemStack stack) 
	{
		if(stack != null && stack.getItem() instanceof ItemDye)
		{
			EnumDyeColor colour = EnumDyeColor.byDyeDamage(stack.getMetadata());
			texture = new ResourceLocation("textures/blocks/hardened_clay_stained_" + colour.getName() + ".png");
			return true;
		}
		return false;
	}
	
	public boolean hasTexture()
	{
		return texture != null;
	}
	
	public ResourceLocation getTexture() 
	{
		return texture;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		if(texture != null)
		{
			compound.setString("texture_domain", texture.getResourceDomain());
			compound.setString("texture_path", texture.getResourcePath());
		}
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		if(compound.hasKey("texture_domain") && compound.hasKey("texture_path"))
		{
			texture = new ResourceLocation(compound.getString("texture_domain"), compound.getString("texture_path"));
		}
	}

	@Override
	public NBTTagCompound getUpdateTag() 
	{
		return writeToNBT(new NBTTagCompound());
	}
}
