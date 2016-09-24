package com.mrcrayfish.skateboarding.block;

import java.util.List;

import com.mrcrayfish.skateboarding.MrCrayfishSkateboardingMod;
import com.mrcrayfish.skateboarding.util.CollisionHelper;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStair extends BlockObject 
{
	public static final PropertyBool STACKED = PropertyBool.create("stacked");
	
	private static final AxisAlignedBB BASE = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.25, 1.0);
	private static final AxisAlignedBB BASE_STACKED = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.75, 1.0);
	
	private static final AxisAlignedBB NORTH_STEP = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.25, 0.0, 1.0, 0.5, 1.0);
	private static final AxisAlignedBB EAST_STEP = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.25, 0.0, 1.0, 0.5, 1.0);
	private static final AxisAlignedBB SOUTH_STEP = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.25, 0.0, 1.0, 0.5, 1.0);
	private static final AxisAlignedBB WEST_STEP = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.25, 0.0, 1.0, 0.5, 1.0);
	private static final AxisAlignedBB[] STEPS = { SOUTH_STEP, WEST_STEP, NORTH_STEP, EAST_STEP };
	
	private static final AxisAlignedBB NORTH_STEP_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.75, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB EAST_STEP_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.75, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB SOUTH_STEP_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.75, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB WEST_STEP_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.75, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB[] STEPS_STACKED = { SOUTH_STEP_STACKED, WEST_STEP_STACKED, NORTH_STEP_STACKED, EAST_STEP_STACKED };
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
	
	public BlockStair(Material materialIn) 
	{
		super(materialIn);
		this.setUnlocalizedName("stair");
		this.setRegistryName("stair");
		this.setCreativeTab(MrCrayfishSkateboardingMod.skateTab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(STACKED, false));
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) 
	{
		if(state.getValue(STACKED)) 
		{
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_STACKED);
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, STEPS_STACKED[state.getValue(FACING).getHorizontalIndex()]);
		} 
		else 
		{
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE);
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, STEPS[state.getValue(FACING).getHorizontalIndex()]);
		}
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		if(!state.getValue(STACKED))
		{
			return BOUNDING_BOX;
		}
		return FULL_BLOCK_AABB;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex() + (((Boolean) state.getValue(STACKED)) ? 4 : 0);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(STACKED, meta / 4 == 1);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, STACKED });
	}
}
