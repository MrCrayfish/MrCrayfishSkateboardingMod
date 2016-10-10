package com.mrcrayfish.skateboarding.client.model.block;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mrcrayfish.skateboarding.block.BlockSlope;
import com.mrcrayfish.skateboarding.util.QuadHelper;
import com.mrcrayfish.skateboarding.util.QuadHelper.Vertex;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class BakedModelSlope implements IBakedModel 
{
	public static final ModelResourceLocation BAKED_MODEL = new ModelResourceLocation("csm:slope");
	
	private VertexFormat format;
	private TextureAtlasSprite mainTexture;
	private TextureAtlasSprite metalTexture;
	
	public BakedModelSlope(VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) 
	{
		this.format = format;
		this.mainTexture = bakedTextureGetter.apply(new ResourceLocation("minecraft", "blocks/hardened_clay"));
		this.metalTexture = bakedTextureGetter.apply(new ResourceLocation("minecraft", "blocks/half_slab"));
	}


	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) 
	{
		List<BakedQuad> quads = new ArrayList<BakedQuad>();

		if(state != null)
		{
			QuadHelper helper = new QuadHelper(format, mainTexture);
			
			if(state.getBlock() instanceof BlockHorizontal)
			{
				EnumFacing facing = state.getValue(BlockHorizontal.FACING);
				helper.setFacing(facing);
			}
			
			boolean stacked = false;
			
			if(state.getBlock() instanceof BlockSlope)
			{
				stacked = state.getValue(BlockSlope.STACKED);
			}
			
			if(stacked)
			{
				// Top
				quads.add(helper.createQuad(new Vertex(0, 0.5, 1, 0, 0), new Vertex(1, 1, 1, 0, 16), new Vertex(1, 1, 0, 16, 16), new Vertex(0, 0.5, 0, 16, 0), EnumFacing.UP));
				
				// Right
				quads.add(helper.createQuad(new Vertex(1, 0, 1, 0, 0), new Vertex(1, 1, 1, 0, 16), new Vertex(0, 0.5, 1, 16, 8), new Vertex(0, 0, 1, 16, 0), EnumFacing.SOUTH));
				
				// Left
				quads.add(helper.createQuad(new Vertex(0, 0, 0, 0, 0), new Vertex(0, 0.5, 0, 0, 8), new Vertex(1, 1, 0, 16, 16), new Vertex(1, 0, 0, 16, 0), EnumFacing.NORTH));
				
				// Back
				quads.add(helper.createQuad(new Vertex(1, 0, 0, 0, 0), new Vertex(1, 1, 0, 0, 16), new Vertex(1, 1, 1, 16, 16), new Vertex(1, 0, 1, 16, 0), EnumFacing.EAST));
				
				// Front
				quads.add(helper.createQuad(new Vertex(0, 0, 1, 0, 0), new Vertex(0, 0.5, 1, 0, 8), new Vertex(0, 0.5, 0, 16, 8), new Vertex(0, 0, 0, 16, 0), EnumFacing.WEST));
			}
			else
			{
				// Top
				quads.add(helper.createQuad(new Vertex(0, 0, 1, 0, 0), new Vertex(1, 0.5, 1, 0, 16), new Vertex(1, 0.5, 0, 16, 16), new Vertex(0, 0, 0, 16, 0), EnumFacing.UP));
				
				// Right
				quads.add(helper.createQuad(new Vertex(1, 0, 1, 0, 0), new Vertex(1, 0.5, 1, 0, 8), new Vertex(0, 0, 1, 16, 0), new Vertex(0, 0, 1, 16, 0), EnumFacing.SOUTH));
				
				// Left
				quads.add(helper.createQuad(new Vertex(0, 0, 0, 0, 0), new Vertex(0, 0, 0, 0, 0), new Vertex(1, 0.5, 0, 16, 8), new Vertex(1, 0, 0, 16, 0), EnumFacing.NORTH));
				
				// Back
				quads.add(helper.createQuad(new Vertex(1, 0, 0, 0, 0), new Vertex(1, 0.5, 0, 0, 8), new Vertex(1, 0.5, 1, 16, 8), new Vertex(1, 0, 1, 16, 0), EnumFacing.EAST));
			}
			
			quads.add(helper.createQuad(new Vertex(1, 0, 1, 0, 0), new Vertex(0, 0, 1, 0, 16), new Vertex(0, 0, 0, 16, 16), new Vertex(1, 0, 0, 16, 0), EnumFacing.DOWN));
		}
		return quads;
	}

	@Override
	public boolean isAmbientOcclusion() 
	{
		return true;
	}

	@Override
	public boolean isGui3d() 
	{
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() 
	{
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() 
	{
		return mainTexture;
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() 
	{
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides() 
	{
		return new ItemOverrideList(Lists.<ItemOverride>newArrayList());
	}

}
