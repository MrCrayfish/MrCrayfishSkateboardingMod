package com.mrcrayfish.skateboarding.client.model.block;

import com.google.common.collect.ImmutableSet;
import com.mrcrayfish.skateboarding.client.model.block.baked.BakedModelSlope;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public class ModelSlope implements IModel
{
	@Override
	public Collection<ResourceLocation> getDependencies() 
	{
		return Collections.emptySet();
	}

	@Override
	public Collection<ResourceLocation> getTextures() 
	{
		return ImmutableSet.of(new ResourceLocation("minecraft", "blocks/hardened_clay"), new ResourceLocation("minecraft", "blocks/stone_slab_top"), new ResourceLocation("minecraft", "blocks/anvil_base"));
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
	{
		return new BakedModelSlope(format, bakedTextureGetter);
	}

	@Override
	public IModelState getDefaultState() 
	{
		return TRSRTransformation.identity();
	}
}
