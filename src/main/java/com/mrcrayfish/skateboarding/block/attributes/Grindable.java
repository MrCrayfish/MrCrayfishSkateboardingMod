package com.mrcrayfish.skateboarding.block.attributes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface Grindable 
{
	public boolean canGrind(World world, IBlockState state, BlockPos pos, double posX, double posY, double posZ);
}
