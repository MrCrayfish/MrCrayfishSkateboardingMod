package com.mrcrayfish.skateboarding.item;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSkateboard extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		world.spawnEntityInWorld(new EntitySkateboard(world));
		return true;
    }
}
