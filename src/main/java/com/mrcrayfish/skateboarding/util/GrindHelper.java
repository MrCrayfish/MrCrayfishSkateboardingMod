package com.mrcrayfish.skateboarding.util;

import com.mrcrayfish.skateboarding.block.properties.Grindable;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GrindHelper
{
	public static boolean canGrind(World world, double posX, double posY, double posZ)
	{
		BlockPos pos = new BlockPos(posX, posY - 1, posZ);
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		return block == Blocks.IRON_BARS || (block instanceof Grindable && ((Grindable) block).canGrind(world, state, pos, posX, posY, posZ));
	}

	public static double[] setOffset(EntitySkateboard skateboard, double x, double y, double z)
	{
		EnumFacing facing = EnumFacing.fromAngle(skateboard.angleOnJump);
		switch (facing)
		{
		case EAST:
			return new double[]{z,y,x}; 
		case SOUTH:
			return new double[]{-x,y,-z}; 
		case WEST:
			return new double[]{-z,y,-x};
		default:
			return new double[]{x,y,z};
		}
	}
}
