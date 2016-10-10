package com.mrcrayfish.skateboarding.block;

import java.util.List;

import com.mrcrayfish.skateboarding.MrCrayfishSkateboardingMod;
import com.mrcrayfish.skateboarding.block.attributes.Angled;
import com.mrcrayfish.skateboarding.block.attributes.Grindable;
import com.mrcrayfish.skateboarding.tileentity.TileEntityStair;
import com.mrcrayfish.skateboarding.util.RotationHelper;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStair extends BlockObject implements ITileEntityProvider, Grindable, Angled
{
	public static final PropertyBool STACKED = PropertyBool.create("stacked");
	
	private static final AxisAlignedBB BASE = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.25, 1.0);
	private static final AxisAlignedBB BASE_STACKED = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.75, 1.0);
	
	private static final AxisAlignedBB NORTH_STEP = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.25, 0.0, 1.0, 0.5, 1.0);
	private static final AxisAlignedBB EAST_STEP = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.25, 0.0, 1.0, 0.5, 1.0);
	private static final AxisAlignedBB SOUTH_STEP = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.25, 0.0, 1.0, 0.5, 1.0);
	private static final AxisAlignedBB WEST_STEP = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.25, 0.0, 1.0, 0.5, 1.0);
	private static final AxisAlignedBB[] STEPS = { SOUTH_STEP, WEST_STEP, NORTH_STEP, EAST_STEP };
	
	private static final AxisAlignedBB NORTH_STEP_STACKED = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.75, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB EAST_STEP_STACKED = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.75, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB SOUTH_STEP_STACKED = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.75, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB WEST_STEP_STACKED = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.75, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB[] STEPS_STACKED = { SOUTH_STEP_STACKED, WEST_STEP_STACKED, NORTH_STEP_STACKED, EAST_STEP_STACKED };
	
	private static final AxisAlignedBB RAIL_NORTH_ONE = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.125, 0.0, 0.375, 1.0, 1.125, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_TWO = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.25, 0.0, 0.375, 1.0, 1.1875, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_THREE = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.375, 0.0, 0.375, 1.0, 1.25, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_FOUR = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.0, 0.375, 1.0, 1.3125, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_FIVE = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.625, 0.0, 0.375, 1.0, 1.375, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_SIX = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.75, 0.0, 0.375, 1.0, 1.4375, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_SEVEN = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.875, 0.0, 0.375, 1.0, 1.5, 0.625);
	
	private static final AxisAlignedBB RAIL_EAST_ONE = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.125, 0.0, 0.375, 1.0, 1.125, 0.625);
	private static final AxisAlignedBB RAIL_EAST_TWO = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.25, 0.0, 0.375, 1.0, 1.1875, 0.625);
	private static final AxisAlignedBB RAIL_EAST_THREE = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.375, 0.0, 0.375, 1.0, 1.25, 0.625);
	private static final AxisAlignedBB RAIL_EAST_FOUR = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.0, 0.375, 1.0, 1.3125, 0.625);
	private static final AxisAlignedBB RAIL_EAST_FIVE = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.625, 0.0, 0.375, 1.0, 1.375, 0.625);
	private static final AxisAlignedBB RAIL_EAST_SIX = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.75, 0.0, 0.375, 1.0, 1.4375, 0.625);
	private static final AxisAlignedBB RAIL_EAST_SEVEN = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.875, 0.0, 0.375, 1.0, 1.5, 0.625);
	
	private static final AxisAlignedBB RAIL_SOUTH_ONE = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.125, 0.0, 0.375, 1.0, 1.125, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_TWO = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.25, 0.0, 0.375, 1.0, 1.1875, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_THREE = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.375, 0.0, 0.375, 1.0, 1.25, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_FOUR = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.0, 0.375, 1.0, 1.3125, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_FIVE = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.625, 0.0, 0.375, 1.0, 1.375, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_SIX = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.75, 0.0, 0.375, 1.0, 1.4375, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_SEVEN = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.875, 0.0, 0.375, 1.0, 1.5, 0.625);
	
	private static final AxisAlignedBB RAIL_WEST_ONE = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.125, 0.0, 0.375, 1.0, 1.125, 0.625);
	private static final AxisAlignedBB RAIL_WEST_TWO = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.25, 0.0, 0.375, 1.0, 1.1875, 0.625);
	private static final AxisAlignedBB RAIL_WEST_THREE = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.375, 0.0, 0.375, 1.0, 1.25, 0.625);
	private static final AxisAlignedBB RAIL_WEST_FOUR = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.0, 0.375, 1.0, 1.3125, 0.625);
	private static final AxisAlignedBB RAIL_WEST_FIVE = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.625, 0.0, 0.375, 1.0, 1.375, 0.625);
	private static final AxisAlignedBB RAIL_WEST_SIX = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.75, 0.0, 0.375, 1.0, 1.4375, 0.625);
	private static final AxisAlignedBB RAIL_WEST_SEVEN = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.875, 0.0, 0.375, 1.0, 1.5, 0.625);

	private static final AxisAlignedBB RAIL_NORTH_ONE_STACKED = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.437, 0.0, 0.375, 1.0, 1.625, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_TWO_STACKED = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.25, 0.0, 0.375, 1.0, 1.6875, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_THREE_STACKED = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.375, 0.0, 0.375, 1.0, 1.75, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_FOUR_STACKED = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.0, 0.375, 1.0, 1.8125, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_FIVE_STACKED = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.625, 0.0, 0.375, 1.0, 1.875, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_SIX_STACKED = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.75, 0.0, 0.375, 1.0, 1.9375, 0.625);
	private static final AxisAlignedBB RAIL_NORTH_SEVEN_STACKED = RotationHelper.getBlockBounds(EnumFacing.NORTH, 0.875, 0.0, 0.375, 1.0, 2.0, 0.625);
	
	private static final AxisAlignedBB RAIL_EAST_ONE_STACKED = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.437, 0.0, 0.375, 1.0, 1.625, 0.625);
	private static final AxisAlignedBB RAIL_EAST_TWO_STACKED = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.25, 0.0, 0.375, 1.0, 1.6875, 0.625);
	private static final AxisAlignedBB RAIL_EAST_THREE_STACKED = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.375, 0.0, 0.375, 1.0, 1.75, 0.625);
	private static final AxisAlignedBB RAIL_EAST_FOUR_STACKED = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.0, 0.375, 1.0, 1.8125, 0.625);
	private static final AxisAlignedBB RAIL_EAST_FIVE_STACKED = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.625, 0.0, 0.375, 1.0, 1.875, 0.625);
	private static final AxisAlignedBB RAIL_EAST_SIX_STACKED = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.75, 0.0, 0.375, 1.0, 1.9375, 0.625);
	private static final AxisAlignedBB RAIL_EAST_SEVEN_STACKED = RotationHelper.getBlockBounds(EnumFacing.EAST, 0.875, 0.0, 0.375, 1.0, 2.0, 0.625);
	
	private static final AxisAlignedBB RAIL_SOUTH_ONE_STACKED = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.437, 0.0, 0.375, 1.0, 1.625, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_TWO_STACKED = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.25, 0.0, 0.375, 1.0, 1.6875, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_THREE_STACKED = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.375, 0.0, 0.375, 1.0, 1.75, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_FOUR_STACKED = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.0, 0.375, 1.0, 1.8125, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_FIVE_STACKED = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.625, 0.0, 0.375, 1.0, 1.875, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_SIX_STACKED = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.75, 0.0, 0.375, 1.0, 1.9375, 0.625);
	private static final AxisAlignedBB RAIL_SOUTH_SEVEN_STACKED = RotationHelper.getBlockBounds(EnumFacing.SOUTH, 0.875, 0.0, 0.375, 1.0, 2.0, 0.625);
	
	private static final AxisAlignedBB RAIL_WEST_ONE_STACKED = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.437, 0.0, 0.375, 1.0, 1.625, 0.625);
	private static final AxisAlignedBB RAIL_WEST_TWO_STACKED = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.25, 0.0, 0.375, 1.0, 1.6875, 0.625);
	private static final AxisAlignedBB RAIL_WEST_THREE_STACKED = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.375, 0.0, 0.375, 1.0, 1.75, 0.625);
	private static final AxisAlignedBB RAIL_WEST_FOUR_STACKED = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.0, 0.375, 1.0, 1.8125, 0.625);
	private static final AxisAlignedBB RAIL_WEST_FIVE_STACKED = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.625, 0.0, 0.375, 1.0, 1.875, 0.625);
	private static final AxisAlignedBB RAIL_WEST_SIX_STACKED = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.75, 0.0, 0.375, 1.0, 1.9375, 0.625);
	private static final AxisAlignedBB RAIL_WEST_SEVEN_STACKED = RotationHelper.getBlockBounds(EnumFacing.WEST, 0.875, 0.0, 0.375, 1.0, 2.0, 0.625);
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
	
	public BlockStair(Material materialIn) 
	{
		super(materialIn);
		this.setUnlocalizedName("stair");
		this.setRegistryName("stair");
		this.setCreativeTab(MrCrayfishSkateboardingMod.skateTab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(STACKED, false));
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) 
	{
		boolean hasRail = false;
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof TileEntityStair)
		{
			TileEntityStair slope = (TileEntityStair) tileEntity;
			hasRail = slope.rail;
		}
		if(state.getValue(STACKED)) 
		{
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_STACKED);
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, STEPS_STACKED[state.getValue(FACING).getHorizontalIndex()]);
			if(hasRail)
			{
				switch(state.getValue(FACING))
				{
				case NORTH:
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_ONE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_TWO_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_THREE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_FOUR_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_FIVE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_SIX_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_SEVEN_STACKED);
					break;
				case EAST:
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_ONE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_TWO_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_THREE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_FOUR_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_FIVE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_SIX_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_SEVEN_STACKED);
					break;
				case SOUTH:
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_ONE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_TWO_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_THREE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_FOUR_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_FIVE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_SIX_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_SEVEN_STACKED);
					break;
				default:
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_ONE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_TWO_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_THREE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_FOUR_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_FIVE_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_SIX_STACKED);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_SEVEN_STACKED);
					break;
				}
			}
		} 
		else 
		{
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE);
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, STEPS[state.getValue(FACING).getHorizontalIndex()]);
			if(hasRail)
			{
				switch(state.getValue(FACING))
				{
				case NORTH:
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_ONE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_TWO);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_THREE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_FOUR);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_FIVE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_SIX);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_SEVEN);
					break;
				case EAST:
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_ONE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_TWO);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_THREE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_FOUR);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_FIVE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_SIX);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_SEVEN);
					break;
				case SOUTH:
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_ONE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_TWO);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_THREE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_FOUR);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_FIVE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_SIX);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_SEVEN);
					break;
				default:
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_ONE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_TWO);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_THREE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_FOUR);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_FIVE);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_SIX);
					super.addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_SEVEN);
					break;
				}
			}
		}
	}
	
	@Override
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

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityStair();
	}

	@Override
	public boolean canGrind(World world, IBlockState state, BlockPos pos, double posX, double posY, double posZ) 
	{
		TileEntityStair stair = (TileEntityStair) world.getTileEntity(pos);
		if(stair.rail)
		{
			System.out.println(posY);
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

	@Override
	public float getAngle() 
	{
		return 22.5F;
	}
	
	@Override
	public double getYOffset(boolean grinding) 
	{
		return grinding ? -0.125 : 0.0;
	}
}
