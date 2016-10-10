package com.mrcrayfish.skateboarding.block.properties;

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
	public boolean isValid(String value) {
		return false;
	}
	
	@Override
	public Class<String> getType() 
	{
		return null;
	}

	@Override
	public String valueToString(String value) {
		// TODO Auto-generated method stub
		return null;
	}
}
