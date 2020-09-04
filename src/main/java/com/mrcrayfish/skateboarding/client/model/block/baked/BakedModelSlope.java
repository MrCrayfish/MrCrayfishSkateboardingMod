package com.mrcrayfish.skateboarding.client.model.block.baked;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mrcrayfish.skateboarding.block.BlockSlope;
import com.mrcrayfish.skateboarding.util.QuadHelper;
import com.mrcrayfish.skateboarding.util.QuadHelper.Vertex;
import com.mrcrayfish.skateboarding.util.TransformationBuilder;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BakedModelSlope implements IBakedModel
{
	public static final ModelResourceLocation BAKED_MODEL = new ModelResourceLocation("csm:slope");
	
	private static final ImmutableMap<TransformType, Matrix4f> cameraTransformations;
	
	static
	{
		ImmutableMap.Builder<TransformType, Matrix4f> builder = ImmutableMap.builder();
		builder.put(TransformType.FIXED, new TransformationBuilder().setScale(0.5F).build().getMatrix());
		builder.put(TransformType.GUI, new TransformationBuilder().setTranslation(0.1F, 2, 0).setRotation(20, 110.5F, 0).setScale(0.7F).build().getMatrix());
		builder.put(TransformType.GROUND, new TransformationBuilder().setTranslation(0, 1, 0).setScale(0.25F).build().getMatrix());
		builder.put(TransformType.FIRST_PERSON_LEFT_HAND, new TransformationBuilder().setTranslation(0, 4, 0).setRotation(0, -45, 0).setScale(0.4F).build().getMatrix());
		builder.put(TransformType.FIRST_PERSON_RIGHT_HAND, new TransformationBuilder().setTranslation(0, 4, 0).setRotation(0, 135, 0).setScale(0.4F).build().getMatrix());
		builder.put(TransformType.THIRD_PERSON_LEFT_HAND, new TransformationBuilder().setTranslation(0, 2.5F, 3.5F).setRotation(75, 315, 0).setScale(0.375F).build().getMatrix());
		builder.put(TransformType.THIRD_PERSON_RIGHT_HAND, new TransformationBuilder().setTranslation(0, 2.5F, 3.5F).setRotation(75, 135, 0).setScale(0.375F).build().getMatrix());
		cameraTransformations = builder.build();
	}
	
	private VertexFormat format;
	private TextureAtlasSprite mainTexture;
	private TextureAtlasSprite metalTexture;
	private TextureAtlasSprite railTexture;
	
	public BakedModelSlope(VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
	{
		this.format = format;
		this.mainTexture = bakedTextureGetter.apply(new ResourceLocation("minecraft", "blocks/hardened_clay"));
		this.metalTexture = bakedTextureGetter.apply(new ResourceLocation("minecraft", "blocks/stone_slab_top"));
		this.railTexture = bakedTextureGetter.apply(new ResourceLocation("minecraft", "blocks/anvil_base"));
	}


	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) 
	{
		List<BakedQuad> quads = new ArrayList<BakedQuad>();
		
		if(side != null) {
			return quads;
		}

		QuadHelper helper = new QuadHelper(format, mainTexture);
		
		if(state != null)
		{
			IExtendedBlockState extendedState = (IExtendedBlockState) state;

			if(extendedState.getBlock() instanceof BlockHorizontal)
			{
				EnumFacing facing = state.getValue(BlockHorizontal.FACING);
				helper.setFacing(facing);
			}
			
			boolean stacked = extendedState.getValue(BlockSlope.STACKED);
			boolean railAttached = extendedState.getValue(BlockSlope.RAIL_ATTACHED);
			boolean railFront = extendedState.getValue(BlockSlope.RAIL_FRONT);
			boolean railBehind = extendedState.getValue(BlockSlope.RAIL_BEHIND);
			boolean metal = extendedState.getValue(BlockSlope.METAL);
			String texture = extendedState.getValue(BlockSlope.TEXTURE);

			TextureAtlasSprite main = getTexture(texture);
			if(main != null)
			{
				helper.setSprite(main);
			}
			
			quads.add(helper.createQuad(new Vertex(1, 0, 1, 0, 0), new Vertex(0, 0, 1, 0, 16), new Vertex(0, 0, 0, 16, 16), new Vertex(1, 0, 0, 16, 0), EnumFacing.DOWN)); // Down
			
			if(stacked)
			{
				quads.add(helper.createQuad(new Vertex(0, 0.5, 1, 0, 0), new Vertex(1, 1, 1, 0, 16), new Vertex(1, 1, 0, 16, 16), new Vertex(0, 0.5, 0, 16, 0), EnumFacing.UP)); //Top
				quads.add(helper.createQuad(new Vertex(1, 0, 1, 0, 0), new Vertex(1, 1, 1, 0, 16), new Vertex(0, 0.5, 1, 16, 8), new Vertex(0, 0, 1, 16, 0), EnumFacing.SOUTH)); // Right
				quads.add(helper.createQuad(new Vertex(0, 0, 0, 0, 0), new Vertex(0, 0.5, 0, 0, 8), new Vertex(1, 1, 0, 16, 16), new Vertex(1, 0, 0, 16, 0), EnumFacing.NORTH)); // Left
				quads.add(helper.createQuad(new Vertex(1, 0, 0, 0, 0), new Vertex(1, 1, 0, 0, 16), new Vertex(1, 1, 1, 16, 16), new Vertex(1, 0, 1, 16, 0), EnumFacing.EAST)); // Back
				quads.add(helper.createQuad(new Vertex(0, 0, 1, 0, 0), new Vertex(0, 0.5, 1, 0, 8), new Vertex(0, 0.5, 0, 16, 8), new Vertex(0, 0, 0, 16, 0), EnumFacing.WEST)); // Front
			}
			else
			{
				quads.add(helper.createQuad(new Vertex(0, 0, 1, 0, 0), new Vertex(1, 0.5, 1, 0, 16), new Vertex(1, 0.5, 0, 16, 16), new Vertex(0, 0, 0, 16, 0), EnumFacing.UP)); // Top
				quads.add(helper.createQuad(new Vertex(1, 0, 1, 0, 0), new Vertex(1, 0.5, 1, 0, 8), new Vertex(0, 0, 1, 16, 0), new Vertex(0, 0, 1, 16, 0), EnumFacing.SOUTH)); // Right
				quads.add(helper.createQuad(new Vertex(0, 0, 0, 0, 0), new Vertex(0, 0, 0, 0, 0), new Vertex(1, 0.5, 0, 16, 8), new Vertex(1, 0, 0, 16, 0), EnumFacing.NORTH)); // Left
				quads.add(helper.createQuad(new Vertex(1, 0, 0, 0, 0), new Vertex(1, 0.5, 0, 0, 8), new Vertex(1, 0.5, 1, 16, 8), new Vertex(1, 0, 1, 16, 0), EnumFacing.EAST)); // Back
				
			}
			
			if(railAttached)
			{
				helper.setSprite(railTexture);
				if(stacked)
				{
					quads.addAll(helper.createAngledCuboid(new Vec3d(0, 22 * 0.0625, 7 * 0.0625), new Vec3d(1, 24 * 0.0625, 9 * 0.0625), 8 * 0.0625));
					quads.addAll(helper.createAngledCuboid(new Vec3d(0, 15 * 0.0625, 7.5 * 0.0625), new Vec3d(1, 16 * 0.0625, 8.5 * 0.0625), 8 * 0.0625));
					quads.addAll(helper.createCuboid(new Vec3d(7.2 * 0.0625, 11 * 0.0625, 7.2 * 0.0625), new Vec3d(8.8 * 0.0625, 27 * 0.0625, 8.8 * 0.0625)));
					if(railFront)
					{
						quads.addAll(helper.createCuboid(new Vec3d(1, 23 * 0.0625, 7.5 * 0.0625), new Vec3d(20 * 0.0625, 24 * 0.0625, 8.5 * 0.0625)));
					}
				}
				else
				{
					quads.addAll(helper.createAngledCuboid(new Vec3d(0, 14 * 0.0625, 7 * 0.0625), new Vec3d(1, 1, 9 * 0.0625), 8 * 0.0625));
					quads.addAll(helper.createAngledCuboid(new Vec3d(0, 7 * 0.0625, 7.5 * 0.0625), new Vec3d(1, 8 * 0.0625, 8.5 * 0.0625), 8 * 0.0625));
					quads.addAll(helper.createCuboid(new Vec3d(7.2 * 0.0625, 3 * 0.0625, 7.2 * 0.0625), new Vec3d(8.8 * 0.0625, 19 * 0.0625, 8.8 * 0.0625)));
					if(railBehind)
					{
						quads.addAll(helper.createCuboid(new Vec3d(-4 * 0.0625, 7 * 0.0625, 7.5 * 0.0625), new Vec3d(0, 8 * 0.0625, 8.5 * 0.0625)));
					}
				}
			}
			
			if(!stacked && metal)
			{
				helper.setSprite(metalTexture);
				quads.add(helper.createQuad(new Vertex(0, 0.001, 1, 0, 0), new Vertex(0.25, 0.126, 1, 0, 4), new Vertex(0.25, 0.126, 0, 16, 4), new Vertex(0, 0.001, 0, 16, 0), EnumFacing.UP));
			}
		}
		else
		{
			quads.add(helper.createQuad(new Vertex(0, 0, 1, 0, 0), new Vertex(1, 0.5, 1, 0, 16), new Vertex(1, 0.5, 0, 16, 16), new Vertex(0, 0, 0, 16, 0), EnumFacing.UP));
			quads.add(helper.createQuad(new Vertex(1, 0, 1, 0, 0), new Vertex(1, 0.5, 1, 0, 8), new Vertex(0, 0, 1, 16, 0), new Vertex(0, 0, 1, 16, 0), EnumFacing.SOUTH));
			quads.add(helper.createQuad(new Vertex(0, 0, 0, 0, 0), new Vertex(0, 0, 0, 0, 0), new Vertex(1, 0.5, 0, 16, 8), new Vertex(1, 0, 0, 16, 0), EnumFacing.NORTH));
			quads.add(helper.createQuad(new Vertex(1, 0, 0, 0, 0), new Vertex(1, 0.5, 0, 0, 8), new Vertex(1, 0.5, 1, 16, 8), new Vertex(1, 0, 1, 16, 0), EnumFacing.EAST));
			quads.add(helper.createQuad(new Vertex(1, 0, 1, 0, 0), new Vertex(0, 0, 1, 0, 16), new Vertex(0, 0, 0, 16, 16), new Vertex(1, 0, 0, 16, 0), EnumFacing.DOWN));
			helper.setSprite(metalTexture);
			quads.add(helper.createQuad(new Vertex(0, 0.001, 1, 0, 0), new Vertex(0.25, 0.126, 1, 0, 4), new Vertex(0.25, 0.126, 0, 16, 4), new Vertex(0, 0.001, 0, 16, 0), EnumFacing.UP));
		}
		return quads;
	}
	
	public TextureAtlasSprite getTexture(String texture)
	{
		return Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(texture);
	}

	@Override
	public boolean isAmbientOcclusion() 
	{
		return true;
	}

	@Override
	public boolean isGui3d() 
	{
		return true;
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

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) 
	{
		return Pair.of(this, cameraTransformations.get(cameraTransformType));
	}
}
