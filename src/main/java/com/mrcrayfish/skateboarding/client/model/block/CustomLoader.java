package com.mrcrayfish.skateboarding.client.model.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class CustomLoader implements ICustomModelLoader
{
	private static final ImmutableMap<String, IModel> blockModels;
	
	static 
	{
		ImmutableMap.Builder<String, IModel> builder = new Builder<String, IModel>();
		builder.put("slope", new ModelSlope());
		builder.put("corner_slope", new ModelSlope());
		builder.put("stair", new ModelStair());
		blockModels = builder.build();
	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) 
	{
		if(modelLocation.getResourceDomain().equals("csm"))
		{
			for(String name : blockModels.keySet())
			{
				if(name.equals(modelLocation.getResourcePath()))
				{
					return true;
				}
				if(("models/item/" + name).equals(modelLocation.getResourcePath()))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception 
	{
		return blockModels.get(modelLocation.getResourcePath().replace("models/item/", ""));
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {}
}
