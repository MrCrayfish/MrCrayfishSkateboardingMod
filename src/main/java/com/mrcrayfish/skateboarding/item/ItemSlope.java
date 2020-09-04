package com.mrcrayfish.skateboarding.item;

import com.mrcrayfish.skateboarding.block.BlockSlope;
import com.mrcrayfish.skateboarding.client.model.block.baked.BakedModelSlope;
import com.mrcrayfish.skateboarding.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemSlope extends ItemBlock 
{
	public ItemSlope(Block block) 
	{
		super(block);
	}
	
	public static void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.SLOPE), 0, BakedModelSlope.BAKED_MODEL);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(facing == EnumFacing.UP)
		{
			IBlockState state = worldIn.getBlockState(pos);
			Block block = state.getBlock();
			if(block instanceof BlockSlope)
			{
				if(!state.getValue(BlockSlope.STACKED))
				{
					worldIn.setBlockState(pos, block.getDefaultState().withProperty(BlockSlope.FACING, state.getValue(BlockSlope.FACING)).withProperty(BlockSlope.STACKED, true));
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
