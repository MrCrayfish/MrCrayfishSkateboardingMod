package com.mrcrayfish.skateboarding.util;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GrindHelper
{
	public static boolean canGrind(World world, double posX, double posY, double posZ)
	{
		double floor_x = Math.floor(posX);
		double floor_y = Math.floor(posY);
		double floor_z = Math.floor(posZ);
		return world.getBlockState(new BlockPos(floor_x, floor_y - 1, floor_z)).getBlock() == Blocks.IRON_BARS;
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
