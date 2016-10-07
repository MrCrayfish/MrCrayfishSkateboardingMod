package com.mrcrayfish.skateboarding.client.render;

import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.block.BlockSlope;
import com.mrcrayfish.skateboarding.block.properties.Angled;
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
			this.bindEntityTexture(skateboard);
			
			skateboard.updateAngledBlock();
			
			if (skateboard.isInTrick() && !skateboard.isGrinding()) 
			{
				y = -0.3;
			}
			
			GlStateManager.translate(x, y + 0.18, z);
			
			IBlockState state = skateboard.getAngledBlockState();
			if(state != null)
			{
				Angled angled = (Angled) state.getBlock();
				EnumFacing facing = state.getValue(BlockSlope.FACING);
				switch(facing) 
				{
				case NORTH:
					GlStateManager.rotate(angled.getAngle(), 1, 0, 0);
					break;
				case EAST:
					GlStateManager.rotate(angled.getAngle(), 0, 0, 1);
					break;
				case SOUTH:
					GlStateManager.rotate(-angled.getAngle(), 1, 0, 0);
					break;
				default:
					GlStateManager.rotate(-angled.getAngle(), 0, 0, 1);
					break;
				}
				GlStateManager.translate(0, angled.getYOffset(skateboard.isGrinding()), 0);
			}
			
			GlStateManager.rotate(-(skateboard.prevRotationYaw + (skateboard.rotationYaw - skateboard.prevRotationYaw) * partialTicks), 0, 1, 0);
			GlStateManager.rotate(-90F, 0, 1, 0);
			GlStateManager.scale(-1.0F, -1.0F, 1.0F);
			
			if (skateboard.getControllingPassenger() != null) 
			{
				if (skateboard.getControllingPassenger() instanceof EntityPlayer) 
				{
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
					
					if(skateboard.isGrinding()) 
					{
						if(skateboard.getCurrentTrick() instanceof Grind) 
						{
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

	@Override
	protected ResourceLocation getEntityTexture(EntitySkateboard entity) 
	{
		return minecartTextures;
	}
}
