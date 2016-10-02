package com.mrcrayfish.skateboarding.client.render;

import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.block.BlockSlope;
import com.mrcrayfish.skateboarding.client.model.ModelSkateboard;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RenderSkateboard extends Render<EntitySkateboard>
{
	private static final ResourceLocation minecartTextures = new ResourceLocation("csm:textures/entity/mojang.png");
	private ModelSkateboard modelSkateboard = new ModelSkateboard();

	public RenderSkateboard(RenderManager renderManager)
	{
		super(renderManager);
		this.shadowSize = 0.0F;
	}
	
	@Override
	public void doRender(EntitySkateboard skateboard, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		GlStateManager.pushMatrix();
		{
			if (skateboard.isInTrick() && !skateboard.isGrinding()) {
				y = -0.3;
			}
			
			GlStateManager.translate(x, y + 0.18, z);
			
			if(skateboard.isOnSlope()) 
			{
				IBlockState state = getSlopeState(skateboard.worldObj, skateboard.posX, skateboard.posY, skateboard.posZ);
				if(state != null)
				{
					EnumFacing facing = state.getValue(BlockSlope.FACING);
					switch(facing) {
					case NORTH:
						GlStateManager.rotate(22.5F, 1, 0, 0);
						break;
					case EAST:
						GlStateManager.rotate(27F, 0, 0, 1);
						break;
					case SOUTH:
						GlStateManager.rotate(-22.5F, 1, 0, 0);
						break;
					default:
						GlStateManager.rotate(-22.5F, 0, 0, 1);
						break;
					}
					GlStateManager.translate(0, -0.125, 0);
				}
			}
			
			GlStateManager.rotate(-(skateboard.prevRotationYaw + (skateboard.rotationYaw - skateboard.prevRotationYaw) * partialTicks), 0, 1, 0);
			GlStateManager.rotate(-90F, 0, 1, 0);
			this.bindEntityTexture(skateboard);
			GlStateManager.scale(-1.0F, -1.0F, 1.0F);
			
			if (skateboard.getControllingPassenger() != null) {
				if (skateboard.getControllingPassenger() instanceof EntityPlayer) {
					
					EntityPlayer player = (EntityPlayer) skateboard.getControllingPassenger();
					player.prevRenderYawOffset = skateboard.prevRotationYaw + 90F;
					player.renderYawOffset = skateboard.rotationYaw + 90F;
					
					if(skateboard.needsCameraUpdate) 
					{
						if(skateboard.canCameraIncrement)
						{
							player.rotationYaw += skateboard.cameraIncrement;
							skateboard.canCameraIncrement = false;
						}
					}
					
					if(skateboard.isGrinding()) {
						if(skateboard.getCurrentTrick() instanceof Grind) {
							Grind grind = (Grind) skateboard.getCurrentTrick();
							double[] offset = grind.getBoardOffsetPosition(skateboard);
							GlStateManager.translate(offset[0], -offset[1], offset[2]);
						}
					}
				}
			}

			modelSkateboard.setRotationAngles(0F, 0F, 0F, 0F, 0F, 0F, skateboard);
			
			// Board Rotation (Global)
			GlStateManager.rotate((float) (skateboard.prevBoardYaw + (skateboard.boardYaw - skateboard.prevBoardYaw) * partialTicks), 0, 1, 0);
			
			// Board Rotation (Local)
			modelSkateboard.boardBase.rotateAngleX = (float) Math.toRadians(skateboard.prevBoardRotationX + (skateboard.boardRotationX - skateboard.prevBoardRotationX) * partialTicks);
			modelSkateboard.boardBase.rotateAngleY = 0F;
			if(skateboard.isFlipped()) modelSkateboard.boardBase.rotateAngleY += Math.toRadians(180F);
			modelSkateboard.boardBase.rotateAngleY += (float) Math.toRadians(skateboard.prevBoardRotationY + (skateboard.boardRotationY - skateboard.prevBoardRotationY) * partialTicks);
			modelSkateboard.boardBase.rotateAngleZ = (float) Math.toRadians(skateboard.prevBoardRotationZ + (skateboard.boardRotationZ - skateboard.prevBoardRotationZ) * partialTicks);
			
			modelSkateboard.render(skateboard, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		}
		GlStateManager.popMatrix();
		//super.doRender(skateboard, x, y, z, p_76986_8_, partialTicks);
	}
	
	public IBlockState getSlopeState(World world, double x, double y, double z) 
	{
		IBlockState stateOne = world.getBlockState(new BlockPos(x - 0.25, y, z - 0.25));
		if(stateOne.getBlock() instanceof BlockSlope) {
			return stateOne;
		}
		
		IBlockState stateTwo = world.getBlockState(new BlockPos(x + 0.25, y, z - 0.25));
		if(stateTwo.getBlock() instanceof BlockSlope) {
			return stateTwo;
		}
		
		IBlockState stateThree = world.getBlockState(new BlockPos(x - 0.25, y, z + 0.25));
		if(stateThree.getBlock() instanceof BlockSlope) {
			return stateThree;
		}
		
		IBlockState stateFour = world.getBlockState(new BlockPos(x - 0.25, y, z - 0.25));
		if(stateFour.getBlock() instanceof BlockSlope) {
			return stateFour;
		}
		
		IBlockState stateFive = world.getBlockState(new BlockPos(x - 0.25, y - 0.5, z - 0.25));
		if(stateFive.getBlock() instanceof BlockSlope) {
			return stateFive;
		}
		
		IBlockState stateSix = world.getBlockState(new BlockPos(x - 0.25, y - 0.5, z + 0.25));
		if(stateSix.getBlock() instanceof BlockSlope) {
			return stateSix;
		}
		
		IBlockState stateSeven = world.getBlockState(new BlockPos(x + 0.25, y - 0.5, z - 0.25));
		if(stateSeven.getBlock() instanceof BlockSlope) {
			return stateSeven;
		}
		
		IBlockState stateEight = world.getBlockState(new BlockPos(x + 0.25, y - 0.5, z + 0.25));
		if(stateEight.getBlock() instanceof BlockSlope) {
			return stateEight;
		}
		
		return null;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySkateboard entity) 
	{
		return minecartTextures;
	}
}
