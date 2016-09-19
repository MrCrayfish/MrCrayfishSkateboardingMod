package com.mrcrayfish.skateboarding.tileentity.renderer;

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.skateboarding.block.BlockSlope;
import com.mrcrayfish.skateboarding.tileentity.TileEntitySlope;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class SlopeRenderer extends TileEntitySpecialRenderer<TileEntitySlope> 
{
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/blocks/hardened_clay.png");
	
	@Override
	public void renderTileEntityAt(TileEntitySlope te, double x, double y, double z, float partialTicks, int destroyStage) 
	{
		IBlockState state = te.getWorld().getBlockState(te.getPos());
		int meta = te.getBlockType().getMetaFromState(state);
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(x, y, z);
			GlStateManager.translate(0.5, 0, 0.5);
			GlStateManager.rotate((meta % 4) * -90F - 90F, 0, 1, 0);
			GlStateManager.translate(-0.5, 0, -0.5);
			GlStateManager.translate(0, (int) (meta / 4) * 0.5, 0);
			GlStateManager.disableLighting();
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer buffer = tessellator.getBuffer();
			GlStateManager.color(1.0F, 1.0F, 1.0F);
			
			// Sides
			if((meta / 4) > 0)
			{
				getLighting(te.getWorld(), te.getPos(), state.getValue(BlockSlope.FACING).rotateYCCW(), 1);
				buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
				buffer.pos(0, -0.5, 0).tex(1, 1).endVertex();
				buffer.pos(0, 0, 0).tex(1, 0.5).endVertex();
				buffer.pos(1, 0.5, 0).tex(0, 0).endVertex();
				buffer.pos(1, -0.5, 0).tex(0, 1).endVertex();
				tessellator.draw();
				
				getLighting(te.getWorld(), te.getPos(), state.getValue(BlockSlope.FACING).rotateY(), 1);
				buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
				buffer.pos(1, -0.5, 1).tex(0, 1).endVertex();
				buffer.pos(1, 0.5, 1).tex(0, 0).endVertex();
				buffer.pos(0, 0, 1).tex(1, 0.5).endVertex();
				buffer.pos(0, -0.5,1).tex(1, 1).endVertex();
				tessellator.draw();
			}
			else
			{
				getLighting(te.getWorld(), te.getPos(), state.getValue(BlockSlope.FACING).rotateYCCW(), 1);
				buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
				buffer.pos(0, 0, 0).tex(0, 1).endVertex();
				buffer.pos(1, 0.5, 0).tex(1, 0.5).endVertex();
				buffer.pos(1, 0, 0).tex(1, 1).endVertex();
				tessellator.draw();
				
				getLighting(te.getWorld(), te.getPos(), state.getValue(BlockSlope.FACING).rotateY(), 1);
				buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
				buffer.pos(1, 0, 1).tex(1, 1).endVertex();
				buffer.pos(1, 0.5, 1).tex(1, 0.5).endVertex();
				buffer.pos(0, 0, 1).tex(0, 1).endVertex();
				tessellator.draw();
			}
			
			getLighting(te.getWorld(), te.getPos(), EnumFacing.UP, 0);
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buffer.pos(0, 0, 0).tex(1, 1).endVertex();
			buffer.pos(0, 0, 1).tex(1, 0).endVertex();
			buffer.pos(1, 0.5, 1).tex(0, 0).endVertex();
			buffer.pos(1, 0.5, 0).tex(0, 1).endVertex();
			tessellator.draw();
			
			getLighting(te.getWorld(), te.getPos(), EnumFacing.DOWN, 1);
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buffer.pos(1, 0, 0).tex(0, 1).endVertex();
			buffer.pos(1, 0, 1).tex(0, 0).endVertex();
			buffer.pos(0, 0, 1).tex(1, 0).endVertex();
			buffer.pos(0, 0, 0).tex(1, 1).endVertex();
			tessellator.draw();
			
			getLighting(te.getWorld(), te.getPos(), state.getValue(BlockSlope.FACING), 2);
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buffer.pos(1, 0, 0).tex(0, 0.5).endVertex();
			buffer.pos(1, 0.5, 0).tex(0, 0).endVertex();
			buffer.pos(1, 0.5, 1).tex(1, 0).endVertex();
			buffer.pos(1, 0, 1).tex(1, 0.5).endVertex();
			tessellator.draw();
		}
		GlStateManager.popMatrix();
	}
	
	public void getLighting(World world, BlockPos pos, EnumFacing facing, int modify) 
	{
		pos = pos.offset(facing);
		int light = world.getLightFor(EnumSkyBlock.SKY, pos) - modify;
		int torch = world.getLightFor(EnumSkyBlock.BLOCK, pos);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, torch * 16F, light * 16F);
	}
}
