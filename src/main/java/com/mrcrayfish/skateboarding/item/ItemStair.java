package com.mrcrayfish.skateboarding.item;

import com.mrcrayfish.skateboarding.block.BlockSlope;
import com.mrcrayfish.skateboarding.block.BlockStair;
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

public class ItemStair extends ItemBlock 
{
	public ItemStair(Block block) 
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
			if(block instanceof BlockStair) 
			{
				if(!state.getValue(BlockStair.STACKED))
				{
					worldIn.setBlockState(pos, block.getDefaultState().withProperty(BlockStair.FACING, state.getValue(BlockStair.FACING)).withProperty(BlockStair.STACKED, true));
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
