package com.mrcrayfish.skateboarding.item;

import com.mrcrayfish.skateboarding.block.BlockCornerSlope;
import com.mrcrayfish.skateboarding.block.BlockSlope;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCornerSlope extends ItemBlock 
{
	public ItemCornerSlope(Block block) 
	{
		super(block);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(facing == EnumFacing.UP)
		{
			IBlockState state = worldIn.getBlockState(pos);
			Block block = state.getBlock();
			if(block instanceof BlockCornerSlope)
			{
				if(!state.getValue(BlockCornerSlope.STACKED))
				{
					worldIn.setBlockState(pos, block.getDefaultState().withProperty(BlockCornerSlope.FACING, state.getValue(BlockCornerSlope.FACING)).withProperty(BlockCornerSlope.STACKED, true));
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
