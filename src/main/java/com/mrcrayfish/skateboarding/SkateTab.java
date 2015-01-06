package com.mrcrayfish.skateboarding;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class SkateTab extends CreativeTabs
{

	public SkateTab(String label)
	{
		super(label);
	}

	@Override
	public Item getTabIconItem()
	{
		return Items.apple;
	}

}
