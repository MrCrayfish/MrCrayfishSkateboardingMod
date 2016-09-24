package com.mrcrayfish.skateboarding.init;

import com.mrcrayfish.skateboarding.Reference;
import com.mrcrayfish.skateboarding.block.BlockCornerSlope;
import com.mrcrayfish.skateboarding.block.BlockSlope;
import com.mrcrayfish.skateboarding.item.ItemCornerSlope;
import com.mrcrayfish.skateboarding.item.ItemSlope;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SkateboardingBlocks
{
	public static Block slope;
	public static Block corner_slope;
	
	public static void init()
	{
		slope = new BlockSlope(Material.WOOD);
		corner_slope = new BlockCornerSlope(Material.ROCK);
	}
	
	public static void register()
	{
		registerBlock(slope, new ItemSlope(slope));
		registerBlock(corner_slope, new ItemCornerSlope(corner_slope));
	}
	
	public static void registerBlock(Block block) 
	{
		registerBlock(block, new ItemBlock(block));
	}
	
	public static void registerBlock(Block block, ItemBlock item) 
	{
		GameRegistry.register(block);
		item.setRegistryName(block.getRegistryName());
		GameRegistry.register(item);
	}
	
	public static void registerRenders()
	{
		registerRender(slope);
		registerRender(corner_slope);
	}
	
	private static void registerRender(Block block)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
}
