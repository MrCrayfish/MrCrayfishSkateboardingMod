package com.mrcrayfish.skateboarding.item;

import com.mrcrayfish.skateboarding.block.BlockSlope;
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
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(facing == EnumFacing.UP)
		{
			IBlockState state = worldIn.getBlockState(pos);
			Block block = state.getBlock();
			if(block instanceof BlockSlope) 
			{
				if(!state.getValue(BlockSlope.STACKED))
				{
					worldIn.setBlockState(pos, block.getDefaultState().withProperty(BlockSlope.FACING, state.getValue(BlockSlope.FACING)).withProperty(BlockSlope.STACKED, true));
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
