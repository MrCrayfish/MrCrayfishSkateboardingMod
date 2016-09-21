package com.mrcrayfish.skateboarding.block;

import java.util.List;

import com.mrcrayfish.skateboarding.MrCrayfishSkateboardingMod;
import com.mrcrayfish.skateboarding.util.CollisionHelper;
import com.mrcrayfish.skateboarding.util.StateHelper;
import com.mrcrayfish.skateboarding.util.StateHelper.Direction;

import net.minecraft.block.BlockHorizontal;
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

public class BlockRail extends BlockObject
{
	public static final PropertyBool LEFT = PropertyBool.create("left");
	public static final PropertyBool RIGHT = PropertyBool.create("right");
	
	private static final AxisAlignedBB BOUNDING_BOX_ONE = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.25, 0.0, 0.0, 0.75, 14 * 0.0625, 1.0);
	private static final AxisAlignedBB BOUNDING_BOX_TWO = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.25, 0.0, 0.0, 0.75, 14 * 0.0625, 1.0);
	private static final AxisAlignedBB[] BOUNDING_BOXES = { BOUNDING_BOX_ONE, BOUNDING_BOX_TWO };
	
	private static final AxisAlignedBB COLLISION_BOX_ONE = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.375, 0.0, 0.0, 0.625, 14 * 0.0625, 1.0);
	private static final AxisAlignedBB COLLISION_BOX_TWO = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.375, 0.0, 0.0, 0.625, 14 * 0.0625, 1.0);
	private static final AxisAlignedBB[] COLLISION_BOXES = { COLLISION_BOX_ONE, COLLISION_BOX_TWO };
	
	
	public BlockRail(Material materialIn) 
	{
		super(materialIn);
		this.setUnlocalizedName("rail");
		this.setRegistryName("rail");
		this.setCreativeTab(MrCrayfishSkateboardingMod.skateTab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEFT, false).withProperty(RIGHT, false));
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) 
	{
		EnumFacing facing = state.getValue(FACING);
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOXES[facing.getHorizontalIndex() % 2]);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		EnumFacing facing = state.getValue(FACING);
		return BOUNDING_BOXES[facing.getHorizontalIndex() % 2];
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) 
	{
		EnumFacing facing = state.getValue(FACING);
		if(StateHelper.getBlock(worldIn, pos, facing, Direction.LEFT) == this)
		{
			Direction direction = StateHelper.getRotation(worldIn, pos, facing, Direction.LEFT);
			if(direction == Direction.DOWN || direction == Direction.UP)
			{
				state = state.withProperty(RIGHT, true);
			}
		}
		if(StateHelper.getBlock(worldIn, pos, facing, Direction.RIGHT) == this)
		{
			Direction direction = StateHelper.getRotation(worldIn, pos, facing, Direction.RIGHT);
			if(direction == Direction.DOWN || direction == Direction.UP)
			{
				state = state.withProperty(LEFT, true);
			}
		}
		return state;
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, LEFT, RIGHT });
	}
}
