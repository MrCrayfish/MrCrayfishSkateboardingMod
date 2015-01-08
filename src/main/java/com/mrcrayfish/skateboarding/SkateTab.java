package com.mrcrayfish.skateboarding;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.mrcrayfish.skateboarding.init.SkateboardingItems;

public class SkateTab extends CreativeTabs
{
	public SkateTab(String label)
	{
		super(label);
	}

	@Override
	public Item getTabIconItem()
	{
		return SkateboardingItems.skateboard;
	}
}
