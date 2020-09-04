package com.mrcrayfish.skateboarding;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.mrcrayfish.skateboarding.init.ModItems;
import net.minecraft.item.ItemStack;

public class SkateTab extends CreativeTabs
{
	public SkateTab(String label)
	{
		super(label);
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(ModItems.SKATEBOARD);
	}
}
