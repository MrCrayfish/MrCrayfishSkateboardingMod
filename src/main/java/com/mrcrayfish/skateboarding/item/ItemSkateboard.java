package com.mrcrayfish.skateboarding.item;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSkateboard extends Item
{
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		if(!worldIn.isRemote)
		{
			worldIn.spawnEntityInWorld(new EntitySkateboard(worldIn, pos.getX(), pos.getY() + 1.0D, pos.getZ()));
		}
		return EnumActionResult.SUCCESS;
	}
}
