package com.mrcrayfish.skateboarding.init;

import com.mrcrayfish.skateboarding.item.ItemSkateboard;
import net.minecraft.item.Item;

public class ModItems
{
	public static final Item SKATEBOARD;

	static
	{
		SKATEBOARD = new ItemSkateboard();
	}

	public static void register()
	{
		register(SKATEBOARD);
	}

	private static void register(Item item)
	{
		RegistrationHandler.Items.add(item);
	}
}
