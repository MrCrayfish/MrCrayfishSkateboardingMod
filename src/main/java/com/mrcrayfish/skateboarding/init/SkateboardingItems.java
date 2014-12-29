package com.mrcrayfish.skateboarding.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.mrcrayfish.skateboarding.MrCrayfishSkateboardingMod;
import com.mrcrayfish.skateboarding.Reference;
import com.mrcrayfish.skateboarding.item.ItemSkateboard;

public class SkateboardingItems {
	public static Item skateboard;
	
	public static void init()
	{
		skateboard = new ItemSkateboard().setUnlocalizedName("skateboard").setCreativeTab(MrCrayfishSkateboardingMod.skateTab);
	}
	
	public static void register()
	{
		GameRegistry.registerItem(skateboard, skateboard.getUnlocalizedName().substring(5));
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
