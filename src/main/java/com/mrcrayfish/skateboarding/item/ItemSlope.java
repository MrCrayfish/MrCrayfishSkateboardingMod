package com.mrcrayfish.skateboarding.item;

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

public class ItemSlope extends ItemBlock 
{
	public ItemSlope(Block block) 
	{
		super(block);
	}
	
	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) 
	{
		if(!world.isRemote)
		{
			if(side == EnumFacing.UP)
			{
				IBlockState state = world.getBlockState(pos);
				Block block = state.getBlock();
				if(block instanceof BlockSlope) 
				{
					if(!state.getValue(BlockSlope.STACKED))
					{
						world.setBlockState(pos, block.getDefaultState().withProperty(BlockSlope.FACING, state.getValue(BlockSlope.FACING)).withProperty(BlockSlope.STACKED, true));
						return EnumActionResult.SUCCESS;
					}
					else
					{
						return EnumActionResult.FAIL;
					}
				}
			}
		}
		return EnumActionResult.PASS;
	}

}
