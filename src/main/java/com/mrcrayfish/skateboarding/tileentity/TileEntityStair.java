package com.mrcrayfish.skateboarding.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityStair extends TileEntityTextureable 
{
	public boolean rail = false;
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		rail = compound.getBoolean("rail");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		compound.setBoolean("rail", rail);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) 
	{
		super.handleUpdateTag(tag);
		rail = tag.getBoolean("rail");
	}
	
	@Override
	public NBTTagCompound getUpdateTag() 
	{
		NBTTagCompound tag = super.getUpdateTag();
		tag.setBoolean("rail", rail);
		return tag;
	}
}
