package com.mrcrayfish.skateboarding.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class StateHelper
{
	public static Block getRelativeBlock(IBlockAccess world, BlockPos pos, EnumFacing facing, RelativeFacing relativeFacing)
	{
		BlockPos target = getBlockPosRelativeTo(world, pos, facing, relativeFacing);
		return world.getBlockState(target).getBlock();
	}
	
	public static RelativeFacing getRelativeFacing(IBlockAccess world, BlockPos pos, EnumFacing facing, RelativeFacing relativeFacing)
	{
		BlockPos target = getBlockPosRelativeTo(world, pos, facing, relativeFacing);
		IBlockState state = world.getBlockState(pos);
		if(state.getProperties().containsKey(BlockHorizontal.FACING))
		{
			EnumFacing otherFacing = (EnumFacing) world.getBlockState(target).getValue(BlockHorizontal.FACING);
			return getDirectionRelativeTo(facing, otherFacing);
		}
		return RelativeFacing.NONE;
	}
	
	public static boolean isAirBlock(IBlockAccess world, BlockPos pos, EnumFacing facing, RelativeFacing dir)
	{
		BlockPos target = getBlockPosRelativeTo(world, pos, facing, dir);
		return world.getBlockState(target).getBlock() instanceof BlockAir;
	}
	
	private static BlockPos getBlockPosRelativeTo(IBlockAccess world, BlockPos pos, EnumFacing facing, RelativeFacing relativeFacing)
	{
		switch (relativeFacing)
		{
		case LEFT:
			return pos.offset(facing.rotateYCCW());
		case RIGHT:
			return pos.offset(facing.rotateY());
		case SAME:
			return pos.offset(facing);
		case OPPOSITE:
			return pos.offset(facing.getOpposite());
		case UP:
			return pos.offset(EnumFacing.UP);
		case DOWN:
			return pos.offset(EnumFacing.DOWN);
		case NONE:
			return pos;
		}
		return pos;
	}

	private static RelativeFacing getDirectionRelativeTo(EnumFacing thisBlock, EnumFacing otherBlock)
	{
		if(otherBlock.getAxis() == Axis.Y)
		{
			if(otherBlock == EnumFacing.UP)
			{
				return RelativeFacing.UP;
			}
			return RelativeFacing.DOWN;
		}
		
		int num = thisBlock.getHorizontalIndex() - otherBlock.getHorizontalIndex();
		switch (num)
		{
		case -3:
			return RelativeFacing.LEFT;
		case -2:
			return RelativeFacing.SAME;
		case -1:
			return RelativeFacing.RIGHT;
		case 0:
			return RelativeFacing.OPPOSITE;
		case 1:
			return RelativeFacing.LEFT;
		case 2:
			return RelativeFacing.SAME;
		case 3:
			return RelativeFacing.RIGHT;
		}
		return RelativeFacing.NONE;
	}

	public static enum RelativeFacing
	{
		SAME, OPPOSITE, LEFT, RIGHT, UP, DOWN, NONE;
	}
	
}
