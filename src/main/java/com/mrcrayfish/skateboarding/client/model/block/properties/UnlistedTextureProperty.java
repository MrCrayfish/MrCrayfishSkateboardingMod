package com.mrcrayfish.skateboarding.client.model.block.properties;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedTextureProperty implements IUnlistedProperty<String>
{
	@Override
	public String getName() 
	{
		return "texture";
	}

	@Override
	public boolean isValid(String value) 
	{
		return true;
	}
	
	@Override
	public Class<String> getType() 
	{
		return String.class;
	}

	@Override
	public String valueToString(String value) 
	{
		return value;
	}
}
