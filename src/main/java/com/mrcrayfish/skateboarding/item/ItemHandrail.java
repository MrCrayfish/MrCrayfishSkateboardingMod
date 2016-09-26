package com.mrcrayfish.skateboarding.item;

import com.mrcrayfish.skateboarding.tileentity.TileEntitySlope;
import com.mrcrayfish.skateboarding.tileentity.TileEntityStair;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHandrail extends ItemBlock
{
	public ItemHandrail(Block block) 
	{
		super(block);
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		System.out.println(tileEntity);
		if(tileEntity instanceof TileEntityStair)
		{
			TileEntityStair stair = (TileEntityStair) tileEntity;
			if(!stair.rail)
			{
				stair.rail = true;
				stack.stackSize--;
				return EnumActionResult.SUCCESS;
			}
		}
		if(tileEntity instanceof TileEntitySlope)
		{
			TileEntitySlope slope = (TileEntitySlope) tileEntity;
			if(!slope.rail)
			{
				slope.rail = true;
				stack.stackSize--;
				return EnumActionResult.SUCCESS;
			}
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

}
