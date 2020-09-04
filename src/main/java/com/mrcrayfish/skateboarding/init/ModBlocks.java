package com.mrcrayfish.skateboarding.init;

import com.mrcrayfish.skateboarding.block.*;
import com.mrcrayfish.skateboarding.item.ItemCornerSlope;
import com.mrcrayfish.skateboarding.item.ItemHandrail;
import com.mrcrayfish.skateboarding.item.ItemSlope;
import com.mrcrayfish.skateboarding.item.ItemStair;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class ModBlocks
{
	public static final BlockSlope SLOPE;
	public static final Block CORNER_SLOPE;
	public static final Block STAIR;
	public static final Block HANDRAIL;
	public static final Block FLAT_BAR;
	
	static
	{
		SLOPE = new BlockSlope(Material.WOOD);
		CORNER_SLOPE = new BlockCornerSlope(Material.ROCK);
		STAIR = new BlockStair(Material.ROCK);
		HANDRAIL = new BlockHandRail(Material.ANVIL);
		FLAT_BAR = new BlockFlatBar(Material.ANVIL);
	}
	
	public static void register()
	{
		registerBlock(SLOPE, new ItemSlope(SLOPE));
		registerBlock(CORNER_SLOPE, new ItemCornerSlope(CORNER_SLOPE));
		registerBlock(STAIR, new ItemStair(STAIR));
		registerBlock(HANDRAIL, new ItemHandrail(HANDRAIL));
		registerBlock(FLAT_BAR);
	}

	private static void registerBlock(Block block)
	{
		registerBlock(block, new ItemBlock(block));
	}

	private static void registerBlock(Block block, ItemBlock item)
	{
		if(block.getRegistryName() == null)
			throw new IllegalArgumentException("A block being registered does not have a registry name and could be successfully registered.");

		RegistrationHandler.Blocks.add(block);
		item.setRegistryName(block.getRegistryName());
		RegistrationHandler.Items.add(item);
	}
}
