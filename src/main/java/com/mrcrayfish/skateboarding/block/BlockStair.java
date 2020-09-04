package com.mrcrayfish.skateboarding.block;

import java.util.List;

import com.mrcrayfish.skateboarding.MrCrayfishSkateboardingMod;
import com.mrcrayfish.skateboarding.block.attributes.Angled;
import com.mrcrayfish.skateboarding.block.attributes.Grindable;
import com.mrcrayfish.skateboarding.client.model.block.properties.UnlistedBooleanProperty;
import com.mrcrayfish.skateboarding.client.model.block.properties.UnlistedTextureProperty;
import com.mrcrayfish.skateboarding.init.ModBlocks;
import com.mrcrayfish.skateboarding.tileentity.TileEntityStair;
import com.mrcrayfish.skateboarding.tileentity.TileEntityTextureable;
import com.mrcrayfish.skateboarding.tileentity.attributes.Railable;
import com.mrcrayfish.skateboarding.util.RotationHelper;
import com.mrcrayfish.skateboarding.util.StateHelper;
import com.mrcrayfish.skateboarding.util.StateHelper.RelativeFacing;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockStair extends BlockObject implements ITileEntityProvider, Grindable, Angled
{
	public static final PropertyBool STACKED = PropertyBool.create("stacked");
	
	public static final UnlistedTextureProperty TEXTURE = new UnlistedTextureProperty();
	public static final UnlistedBooleanProperty RAIL_ATTACHED = new UnlistedBooleanProperty();
	public static final UnlistedBooleanProperty RAIL_FRONT = new UnlistedBooleanProperty();
	public static final UnlistedBooleanProperty RAIL_BEHIND = new UnlistedBooleanProperty();
	
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
	public boolean isBlockNormalCube(IBlockState blockState) 
	{
		return false;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) 
    {
        return false;
    }

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if(heldItem.isEmpty())
		{
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if(tileEntity instanceof TileEntityTextureable)
			{
				if(((TileEntityTextureable) tileEntity).setTexture(heldItem))
				{
					worldIn.markBlockRangeForRenderUpdate(pos, pos);
					heldItem.shrink(1);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
	{
		boolean hasRail = false;
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof TileEntityStair)
		{
			TileEntityStair slope = (TileEntityStair) tileEntity;
			hasRail = slope.isRailAttached();
		}
		if(state.getValue(STACKED))
		{
			addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_STACKED);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, STEPS_STACKED[state.getValue(FACING).getHorizontalIndex()]);
			if(hasRail)
			{
				switch(state.getValue(FACING))
				{
					case NORTH:
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_ONE_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_TWO_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_THREE_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_FOUR_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_FIVE_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_SIX_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_SEVEN_STACKED);
						break;
					case EAST:
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_ONE_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_TWO_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_THREE_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_FOUR_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_FIVE_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_SIX_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_SEVEN_STACKED);
						break;
					case SOUTH:
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_ONE_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_TWO_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_THREE_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_FOUR_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_FIVE_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_SIX_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_SEVEN_STACKED);
						break;
					default:
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_ONE_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_TWO_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_THREE_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_FOUR_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_FIVE_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_SIX_STACKED);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_SEVEN_STACKED);
						break;
				}
			}
		}
		else
		{
			addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, STEPS[state.getValue(FACING).getHorizontalIndex()]);
			if(hasRail)
			{
				switch(state.getValue(FACING))
				{
					case NORTH:
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_ONE);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_TWO);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_THREE);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_FOUR);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_FIVE);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_SIX);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_NORTH_SEVEN);
						break;
					case EAST:
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_ONE);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_TWO);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_THREE);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_FOUR);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_FIVE);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_SIX);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_EAST_SEVEN);
						break;
					case SOUTH:
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_ONE);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_TWO);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_THREE);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_FOUR);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_FIVE);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_SIX);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_SOUTH_SEVEN);
						break;
					default:
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_ONE);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_TWO);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_THREE);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_FOUR);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_FIVE);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_SIX);
						addCollisionBoxToList(pos, entityBox, collidingBoxes, RAIL_WEST_SEVEN);
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
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) 
	{
		IExtendedBlockState extendedState = (IExtendedBlockState) state;
		
		extendedState = extendedState.withProperty(RAIL_FRONT, false).withProperty(RAIL_BEHIND, false);
		
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof TileEntityTextureable)
		{
			TileEntityTextureable textureable = (TileEntityTextureable) tileEntity;
			if(textureable.hasTexture())
			{
				extendedState = extendedState.withProperty(TEXTURE, textureable.getTexture().toString());
			}
		}
		
		if(tileEntity instanceof Railable)
		{
			Railable railable = (Railable) tileEntity;
			extendedState = extendedState.withProperty(RAIL_ATTACHED, railable.isRailAttached());
		}
		
		if(StateHelper.getRelativeBlock(world, pos.up(), state.getValue(FACING), RelativeFacing.SAME) == ModBlocks.HANDRAIL)
		{
			RelativeFacing relativeFacing = StateHelper.getRelativeFacing(world, pos.up(), state.getValue(FACING), RelativeFacing.SAME);
			extendedState = extendedState.withProperty(RAIL_FRONT, relativeFacing == RelativeFacing.LEFT || relativeFacing == RelativeFacing.RIGHT);
		}
		
		if(StateHelper.getRelativeBlock(world, pos, state.getValue(FACING), RelativeFacing.OPPOSITE) == ModBlocks.HANDRAIL)
		{
			RelativeFacing relativeFacing = StateHelper.getRelativeFacing(world, pos, state.getValue(FACING), RelativeFacing.OPPOSITE);
			extendedState = extendedState.withProperty(RAIL_BEHIND, relativeFacing == RelativeFacing.LEFT || relativeFacing == RelativeFacing.RIGHT);
		}
		
		return extendedState;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		BlockStateContainer.Builder builder = new BlockStateContainer.Builder(this);
		builder.add(FACING, STACKED);
		builder.add(TEXTURE, RAIL_ATTACHED, RAIL_FRONT, RAIL_BEHIND);
		return builder.build();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityStair();
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() 
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean canGrind(World world, IBlockState state, BlockPos pos, double posX, double posY, double posZ) 
	{
		TileEntityStair stair = (TileEntityStair) world.getTileEntity(pos);
		if(stair.isRailAttached())
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
