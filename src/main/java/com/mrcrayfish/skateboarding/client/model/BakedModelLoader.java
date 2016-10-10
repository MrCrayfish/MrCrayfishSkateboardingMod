package com.mrcrayfish.skateboarding.client.model;

import com.mrcrayfish.skateboarding.client.model.block.ModelSlope;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class BakedModelLoader implements ICustomModelLoader
{
	public static final ModelSlope SLOPE_MODEL = new ModelSlope();

	@Override
	public boolean accepts(ResourceLocation modelLocation) 
	{
		return modelLocation.getResourceDomain().equals("csm") && "slope".equals(modelLocation.getResourcePath());
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception 
	{
		return SLOPE_MODEL;
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {}
}
