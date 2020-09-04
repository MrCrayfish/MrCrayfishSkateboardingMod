package com.mrcrayfish.skateboarding.client.model.block;

import java.util.Collection;
import java.util.Collections;

import com.google.common.collect.ImmutableSet;
import com.mrcrayfish.skateboarding.client.model.block.baked.BakedModelCornerSlope;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class ModelCornerSlope implements IModel {

	@Override
	public Collection<ResourceLocation> getDependencies() 
	{
		return Collections.emptySet();
	}

	@Override
	public Collection<ResourceLocation> getTextures() 
	{
		return ImmutableSet.of(new ResourceLocation("minecraft", "blocks/hardened_clay"), new ResourceLocation("minecraft", "blocks/stone_slab_top"));
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, java.util.function.Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
	{
		return new BakedModelCornerSlope(format, bakedTextureGetter);
	}

	@Override
	public IModelState getDefaultState() 
	{
		return TRSRTransformation.identity();
	}

}
