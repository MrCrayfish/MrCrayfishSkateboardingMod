package com.mrcrayfish.skateboarding.util;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GrindHelper
{
	public static boolean canGrind(World world, double posX, double posY, double posZ, float rotationYaw)
	{
		double floor_x = Math.floor(posX);
		double floor_z = Math.floor(posZ);

		EnumFacing face = EnumFacing.fromAngle(rotationYaw);
		if (face == EnumFacing.NORTH | face == EnumFacing.SOUTH)
			return world.isSideSolid(new BlockPos(floor_x, posY - 1, floor_z), EnumFacing.UP) | world.isSideSolid(new BlockPos(floor_x - 1, posY - 1, floor_z), EnumFacing.UP);

		if (face == EnumFacing.EAST | face == EnumFacing.WEST)
			return world.isSideSolid(new BlockPos(floor_x, posY - 1, floor_z), EnumFacing.UP) | world.isSideSolid(new BlockPos(floor_x, posY - 1, floor_z + 1), EnumFacing.UP);

		return false;
	}
}
