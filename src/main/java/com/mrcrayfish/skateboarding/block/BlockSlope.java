package com.mrcrayfish.skateboarding.block;

import java.util.List;

import com.mrcrayfish.skateboarding.MrCrayfishSkateboardingMod;
import com.mrcrayfish.skateboarding.block.properties.Grindable;
import com.mrcrayfish.skateboarding.tileentity.TileEntitySlope;
import com.mrcrayfish.skateboarding.tileentity.TileEntityStair;
import com.mrcrayfish.skateboarding.util.CollisionHelper;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSlope extends BlockObject implements ITileEntityProvider, Grindable
{
	public static final PropertyBool STACKED = PropertyBool.create("stacked");

	private static final AxisAlignedBB BASE = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.0625, 1.0);
	private static final AxisAlignedBB BASE_STACKED = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5625, 1.0);
	
	private static final AxisAlignedBB NORTH_ONE = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.125, 0.0625, 0.0, 1.0, 0.125, 1.0);
	private static final AxisAlignedBB NORTH_TWO = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.25, 0.125, 0.0, 1.0, 0.1875, 1.0);
	private static final AxisAlignedBB NORTH_THREE = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.375, 0.1875, 0.0, 1.0, 0.25, 1.0);
	private static final AxisAlignedBB NORTH_FOUR = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.25, 0.0, 1.0, 0.3125, 1.0);
	private static final AxisAlignedBB NORTH_FIVE = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.625, 0.3125, 0.0, 1.0, 0.375, 1.0);
	private static final AxisAlignedBB NORTH_SIX = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.75, 0.375, 0.0, 1.0, 0.4375, 1.0);
	private static final AxisAlignedBB NORTH_SEVEN = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.875, 0.4375, 0.0, 1.0, 0.5, 1.0);
	
	private static final AxisAlignedBB EAST_ONE = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.125, 0.0625, 0.0, 1.0, 0.125, 1.0);
	private static final AxisAlignedBB EAST_TWO = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.25, 0.125, 0.0, 1.0, 0.1875, 1.0);
	private static final AxisAlignedBB EAST_THREE = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.375, 0.1875, 0.0, 1.0, 0.25, 1.0);
	private static final AxisAlignedBB EAST_FOUR = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.25, 0.0, 1.0, 0.3125, 1.0);
	private static final AxisAlignedBB EAST_FIVE = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.625, 0.3125, 0.0, 1.0, 0.375, 1.0);
	private static final AxisAlignedBB EAST_SIX = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.75, 0.375, 0.0, 1.0, 0.4375, 1.0);
	private static final AxisAlignedBB EAST_SEVEN = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.875, 0.4375, 0.0, 1.0, 0.5, 1.0);
	
	private static final AxisAlignedBB SOUTH_ONE = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.125, 0.0625, 0.0, 1.0, 0.125, 1.0);
	private static final AxisAlignedBB SOUTH_TWO = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.25, 0.125, 0.0, 1.0, 0.1875, 1.0);
	private static final AxisAlignedBB SOUTH_THREE = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.375, 0.1875, 0.0, 1.0, 0.25, 1.0);
	private static final AxisAlignedBB SOUTH_FOUR = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.25, 0.0, 1.0, 0.3125, 1.0);
	private static final AxisAlignedBB SOUTH_FIVE = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.625, 0.3125, 0.0, 1.0, 0.375, 1.0);
	private static final AxisAlignedBB SOUTH_SIX = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.75, 0.375, 0.0, 1.0, 0.4375, 1.0);
	private static final AxisAlignedBB SOUTH_SEVEN = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.875, 0.4375, 0.0, 1.0, 0.5, 1.0);
	
	private static final AxisAlignedBB WEST_ONE = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.125, 0.0625, 0.0, 1.0, 0.125, 1.0);
	private static final AxisAlignedBB WEST_TWO = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.25, 0.125, 0.0, 1.0, 0.1875, 1.0);
	private static final AxisAlignedBB WEST_THREE = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.375, 0.1875, 0.0, 1.0, 0.25, 1.0);
	private static final AxisAlignedBB WEST_FOUR = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.25, 0.0, 1.0, 0.3125, 1.0);
	private static final AxisAlignedBB WEST_FIVE = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.625, 0.3125, 0.0, 1.0, 0.375, 1.0);
	private static final AxisAlignedBB WEST_SIX = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.75, 0.375, 0.0, 1.0, 0.4375, 1.0);
	private static final AxisAlignedBB WEST_SEVEN = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.875, 0.4375, 0.0, 1.0, 0.5, 1.0);
	
	private static final AxisAlignedBB NORTH_ONE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.125, 0.5625, 0.0, 1.0, 0.625, 1.0);
	private static final AxisAlignedBB NORTH_TWO_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.25, 0.625, 0.0, 1.0, 0.6875, 1.0);
	private static final AxisAlignedBB NORTH_THREE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.375, 0.6875, 0.0, 1.0, 0.75, 1.0);
	private static final AxisAlignedBB NORTH_FOUR_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.75, 0.0, 1.0, 0.8125, 1.0);
	private static final AxisAlignedBB NORTH_FIVE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.625, 0.8125, 0.0, 1.0, 0.875, 1.0);
	private static final AxisAlignedBB NORTH_SIX_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.75, 0.875, 0.0, 1.0, 0.9375, 1.0);
	private static final AxisAlignedBB NORTH_SEVEN_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.875, 0.9375, 0.0, 1.0, 1.0, 1.0);
	
	private static final AxisAlignedBB EAST_ONE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.125, 0.5625, 0.0, 1.0, 0.625, 1.0);
	private static final AxisAlignedBB EAST_TWO_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.25, 0.625, 0.0, 1.0, 0.6875, 1.0);
	private static final AxisAlignedBB EAST_THREE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.375, 0.6875, 0.0, 1.0, 0.75, 1.0);
	private static final AxisAlignedBB EAST_FOUR_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.75, 0.0, 1.0, 0.8125, 1.0);
	private static final AxisAlignedBB EAST_FIVE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.625, 0.8125, 0.0, 1.0, 0.875, 1.0);
	private static final AxisAlignedBB EAST_SIX_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.75, 0.875, 0.0, 1.0, 0.9375, 1.0);
	private static final AxisAlignedBB EAST_SEVEN_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.875, 0.9375, 0.0, 1.0, 1.0, 1.0);
	
	private static final AxisAlignedBB SOUTH_ONE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.125, 0.5625, 0.0, 1.0, 0.625, 1.0);
	private static final AxisAlignedBB SOUTH_TWO_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.25, 0.625, 0.0, 1.0, 0.6875, 1.0);
	private static final AxisAlignedBB SOUTH_THREE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.375, 0.6875, 0.0, 1.0, 0.75, 1.0);
	private static final AxisAlignedBB SOUTH_FOUR_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.75, 0.0, 1.0, 0.8125, 1.0);
	private static final AxisAlignedBB SOUTH_FIVE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.625, 0.8125, 0.0, 1.0, 0.875, 1.0);
	private static final AxisAlignedBB SOUTH_SIX_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.75, 0.875, 0.0, 1.0, 0.9375, 1.0);
	private static final AxisAlignedBB SOUTH_SEVEN_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.875, 0.9375, 0.0, 1.0, 1.0, 1.0);
	
	private static final AxisAlignedBB WEST_ONE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.125, 0.5625, 0.0, 1.0, 0.625, 1.0);
	private static final AxisAlignedBB WEST_TWO_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.25, 0.625, 0.0, 1.0, 0.6875, 1.0);
	private static final AxisAlignedBB WEST_THREE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.375, 0.6875, 0.0, 1.0, 0.75, 1.0);
	private static final AxisAlignedBB WEST_FOUR_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.75, 0.0, 1.0, 0.8125, 1.0);
	private static final AxisAlignedBB WEST_FIVE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.625, 0.8125, 0.0, 1.0, 0.875, 1.0);
	private static final AxisAlignedBB WEST_SIX_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.75, 0.875, 0.0, 1.0, 0.9375, 1.0);
	private static final AxisAlignedBB WEST_SEVEN_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.875, 0.9375, 0.0, 1.0, 1.0, 1.0);
	
	private static final AxisAlignedBB RAIL_NORTH_ONE = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.125, 0.0, 0.375, 1.0, 1.125, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_TWO = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.25, 0.0, 0.375, 1.0, 1.1875, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_THREE = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.375, 0.0, 0.375, 1.0, 1.25, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_FOUR = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.0, 0.375, 1.0, 1.3125, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_FIVE = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.625, 0.0, 0.375, 1.0, 1.375, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_SIX = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.75, 0.0, 0.375, 1.0, 1.4375, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_SEVEN = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.875, 0.0, 0.375, 1.0, 1.5, 0.625);
	
	private static final AxisAlignedBB RAIL_EAST_ONE = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.125, 0.0, 0.375, 1.0, 1.125, 0.625);
	private static final AxisAlignedBB RAIL_EAST_TWO = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.25, 0.0, 0.375, 1.0, 1.1875, 0.625);
	private static final AxisAlignedBB RAIL_EAST_THREE = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.375, 0.0, 0.375, 1.0, 1.25, 0.625);
	private static final AxisAlignedBB RAIL_EAST_FOUR = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.0, 0.375, 1.0, 1.3125, 0.625);
	private static final AxisAlignedBB RAIL_EAST_FIVE = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.625, 0.0, 0.375, 1.0, 1.375, 0.625);
	private static final AxisAlignedBB RAIL_EAST_SIX = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.75, 0.0, 0.375, 1.0, 1.4375, 0.625);
	private static final AxisAlignedBB RAIL_EAST_SEVEN = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.875, 0.0, 0.375, 1.0, 1.5, 0.625);
	
	private static final AxisAlignedBB RAIL_SOUTH_ONE = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.125, 0.0, 0.375, 1.0, 1.125, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_TWO = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.25, 0.0, 0.375, 1.0, 1.1875, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_THREE = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.375, 0.0, 0.375, 1.0, 1.25, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_FOUR = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.0, 0.375, 1.0, 1.3125, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_FIVE = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.625, 0.0, 0.375, 1.0, 1.375, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_SIX = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.75, 0.0, 0.375, 1.0, 1.4375, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_SEVEN = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.875, 0.0, 0.375, 1.0, 1.5, 0.625);
	
	private static final AxisAlignedBB RAIL_WEST_ONE = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.125, 0.0, 0.375, 1.0, 1.125, 0.625);
	private static final AxisAlignedBB RAIL_WEST_TWO = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.25, 0.0, 0.375, 1.0, 1.1875, 0.625);
	private static final AxisAlignedBB RAIL_WEST_THREE = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.375, 0.0, 0.375, 1.0, 1.25, 0.625);
	private static final AxisAlignedBB RAIL_WEST_FOUR = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.0, 0.375, 1.0, 1.3125, 0.625);
	private static final AxisAlignedBB RAIL_WEST_FIVE = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.625, 0.0, 0.375, 1.0, 1.375, 0.625);
	private static final AxisAlignedBB RAIL_WEST_SIX = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.75, 0.0, 0.375, 1.0, 1.4375, 0.625);
	private static final AxisAlignedBB RAIL_WEST_SEVEN = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.875, 0.0, 0.375, 1.0, 1.5, 0.625);

	private static final AxisAlignedBB RAIL_NORTH_ONE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.437, 0.0, 0.375, 1.0, 1.625, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_TWO_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.25, 0.0, 0.375, 1.0, 1.6875, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_THREE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.375, 0.0, 0.375, 1.0, 1.75, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_FOUR_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.0, 0.375, 1.0, 1.8125, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_FIVE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.625, 0.0, 0.375, 1.0, 1.875, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_SIX_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.75, 0.0, 0.375, 1.0, 1.9375, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_SEVEN_STACKED = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.875, 0.0, 0.375, 1.0, 2.0, 0.625);
	
	private static final AxisAlignedBB RAIL_EAST_ONE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.437, 0.0, 0.375, 1.0, 1.625, 0.625);
	private static final AxisAlignedBB RAIL_EAST_TWO_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.25, 0.0, 0.375, 1.0, 1.6875, 0.625);
	private static final AxisAlignedBB RAIL_EAST_THREE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.375, 0.0, 0.375, 1.0, 1.75, 0.625);
	private static final AxisAlignedBB RAIL_EAST_FOUR_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.0, 0.375, 1.0, 1.8125, 0.625);
	private static final AxisAlignedBB RAIL_EAST_FIVE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.625, 0.0, 0.375, 1.0, 1.875, 0.625);
	private static final AxisAlignedBB RAIL_EAST_SIX_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.75, 0.0, 0.375, 1.0, 1.9375, 0.625);
	private static final AxisAlignedBB RAIL_EAST_SEVEN_STACKED = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.875, 0.0, 0.375, 1.0, 2.0, 0.625);
	
	private static final AxisAlignedBB RAIL_SOUTH_ONE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.437, 0.0, 0.375, 1.0, 1.625, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_TWO_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.25, 0.0, 0.375, 1.0, 1.6875, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_THREE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.375, 0.0, 0.375, 1.0, 1.75, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_FOUR_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.0, 0.375, 1.0, 1.8125, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_FIVE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.625, 0.0, 0.375, 1.0, 1.875, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_SIX_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.75, 0.0, 0.375, 1.0, 1.9375, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_SEVEN_STACKED = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.875, 0.0, 0.375, 1.0, 2.0, 0.625);
	
	private static final AxisAlignedBB RAIL_WEST_ONE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.437, 0.0, 0.375, 1.0, 1.625, 0.625);
	private static final AxisAlignedBB RAIL_WEST_TWO_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.25, 0.0, 0.375, 1.0, 1.6875, 0.625);
	private static final AxisAlignedBB RAIL_WEST_THREE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.375, 0.0, 0.375, 1.0, 1.75, 0.625);
	private static final AxisAlignedBB RAIL_WEST_FOUR_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.0, 0.375, 1.0, 1.8125, 0.625);
	private static final AxisAlignedBB RAIL_WEST_FIVE_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.625, 0.0, 0.375, 1.0, 1.875, 0.625);
	private static final AxisAlignedBB RAIL_WEST_SIX_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.75, 0.0, 0.375, 1.0, 1.9375, 0.625);
	private static final AxisAlignedBB RAIL_WEST_SEVEN_STACKED = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.875, 0.0, 0.375, 1.0, 2.0, 0.625);
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
	 
	public BlockSlope(Material materialIn) 
	{
		super(materialIn);
		this.setUnlocalizedName("slope");
		this.setRegistryName("slope");
		this.setCreativeTab(MrCrayfishSkateboardingMod.skateTab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(STACKED, false));
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) 
	{
		boolean hasRail = false;
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof TileEntitySlope)
		{
			TileEntitySlope slope = (TileEntitySlope) tileEntity;
			hasRail = slope.rail;
		}
		if(state.getValue(STACKED)) 
		{
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_STACKED);
			switch(state.getValue(FACING))
			{
			case NORTH:
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_ONE_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_TWO_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_THREE_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_FOUR_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_FIVE_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_SIX_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_SEVEN_STACKED);
				if(hasRail) 
				{
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_ONE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_TWO_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_THREE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_FOUR_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_FIVE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_SIX_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_SEVEN_STACKED);
				}
				break;
			case EAST:
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_ONE_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_TWO_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_THREE_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_FOUR_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_FIVE_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_SIX_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_SEVEN_STACKED);
				if(hasRail) 
				{
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_ONE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_TWO_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_THREE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_FOUR_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_FIVE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_SIX_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_SEVEN_STACKED);
				}
				break;
			case SOUTH:
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_ONE_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_TWO_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_THREE_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_FOUR_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_FIVE_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_SIX_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_SEVEN_STACKED);
				if(hasRail) 
				{
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_ONE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_TWO_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_THREE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_FOUR_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_FIVE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_SIX_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_SEVEN_STACKED);
				}
				break;
			default:
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_ONE_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_TWO_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_THREE_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_FOUR_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_FIVE_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_SIX_STACKED);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_SEVEN_STACKED);
				if(hasRail) 
				{
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_ONE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_TWO_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_THREE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_FOUR_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_FIVE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_SIX_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_SEVEN_STACKED);
				}
				break;
			}
		} 
		else 
		{
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE);
			switch(state.getValue(FACING))
			{
			case NORTH:
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_ONE);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_TWO);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_THREE);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_FOUR);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_FIVE);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_SIX);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_SEVEN);
				if(hasRail) 
				{
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_ONE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_TWO);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_THREE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_FOUR);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_FIVE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_SIX);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_SEVEN);
				}
				break;
			case EAST:
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_ONE);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_TWO);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_THREE);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_FOUR);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_FIVE);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_SIX);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_SEVEN);
				if(hasRail) 
				{
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_ONE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_TWO);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_THREE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_FOUR);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_FIVE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_SIX);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_SEVEN);
				}
				break;
			case SOUTH:
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_ONE);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_TWO);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_THREE);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_FOUR);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_FIVE);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_SIX);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_SEVEN);
				if(hasRail) 
				{
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_ONE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_TWO);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_THREE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_FOUR);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_FIVE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_SIX);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_SEVEN);
				}
				break;
			default:
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_ONE);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_TWO);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_THREE);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_FOUR);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_FIVE);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_SIX);
				super.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_SEVEN);
				if(hasRail) 
				{
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_ONE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_TWO);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_THREE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_FOUR);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_FIVE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_SIX);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_SEVEN);
				}
				break;
			}
		}
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		if(!state.getValue(STACKED))
		{
			return BOUNDING_BOX;
		}
		return FULL_BLOCK_AABB;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex() + (((Boolean) state.getValue(STACKED)) ? 4 : 0);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(STACKED, meta / 4 == 1);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, STACKED });
	}
	
	public Axis getAxis(IBlockState state) 
	{
		 return ((EnumFacing) state.getValue(FACING)).getAxis();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntitySlope();
	}

	@Override
	public boolean canGrind(World world, IBlockState state, BlockPos pos, double posX, double posY, double posZ)
	{
		TileEntitySlope stair = (TileEntitySlope) world.getTileEntity(pos);
		if(stair.rail)
		{
			if(state.getValue(STACKED))
			{
				return posY >= 1.5;
			}
			else
			{
				return posY >= 1.0;
			}
		}
		return false;
	}
}