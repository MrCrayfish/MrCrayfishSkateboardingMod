package com.mrcrayfish.skateboarding.init;

import com.mrcrayfish.skateboarding.MrCrayfishSkateboardingMod;
import com.mrcrayfish.skateboarding.Reference;
import com.mrcrayfish.skateboarding.item.ItemSkateboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SkateboardingItems
{
	public static Item skateboard;

	public static void init()
	{
		skateboard = new ItemSkateboard();
	}

	public static void register()
	{
		GameRegistry.registerItem(skateboard);
	}

	public static void registerRenders()
	{
		registerRender(skateboard);
	}

	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
