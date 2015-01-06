package com.mrcrayfish.skateboarding.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GrindHelper
{
	public static boolean canGrind(World world, double posX, double posY, double posZ)
	{
		double floor_x = Math.floor(posX);
		double floor_y = Math.floor(posY);
		double floor_z = Math.floor(posZ);
		return world.getBlockState(new BlockPos(floor_x, floor_y - 1, floor_z)).getBlock() == Blocks.iron_bars;
	}
}
