package com.mrcrayfish.skateboarding.tileentity.renderer;

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.skateboarding.block.BlockSlope;
import com.mrcrayfish.skateboarding.init.SkateboardingBlocks;
import com.mrcrayfish.skateboarding.tileentity.TileEntityStair;
import com.mrcrayfish.skateboarding.util.StateHelper;
import com.mrcrayfish.skateboarding.util.StateHelper.RelativeFacing;

import net.minecraft.block.BlockHorizontal;
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

public class StairRenderer extends TileEntitySpecialRenderer<TileEntityStair> 
{
	private static final ResourceLocation METAL_TEXTURE = new ResourceLocation("textures/blocks/anvil_base.png");
	
	@Override
	public void renderTileEntityAt(TileEntityStair te, double x, double y, double z, float partialTicks, int destroyStage) 
	{
		if(!te.rail) return;
		
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
			
			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer buffer = tessellator.getBuffer();
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(METAL_TEXTURE);
			
			drawCuboid(te.getWorld(), te.getPos(), tessellator, buffer, 6.4, 4, 7.2, 1.6, 15, 1.6);
			//drawCuboid(te.getWorld(), te.getPos(), tessellator, buffer, 11.2, 8, 7.2, 1.6, 13, 1.6);
			
			getLighting(te.getWorld(), te.getPos(), EnumFacing.UP, 1);
			drawAngledCuboid(tessellator, buffer, 0, 14, 7, 16, 2, 2, 8);
			
			getLighting(te.getWorld(), te.getPos(), EnumFacing.UP, 1);
			drawAngledCuboid(tessellator, buffer, 0, 7, 7.5, 16, 1, 1, 8);
			
			if(StateHelper.getRelativeBlock(te.getWorld(), te.getPos().up(), state.getValue(BlockHorizontal.FACING), RelativeFacing.SAME) == SkateboardingBlocks.handrail)
			{
				drawCuboid(te.getWorld(), te.getPos(), tessellator, buffer, 16, 15, 7.5, 4, 1, 1);
			}
			
			if(StateHelper.getRelativeBlock(te.getWorld(), te.getPos(), state.getValue(BlockHorizontal.FACING), RelativeFacing.OPPOSITE) == SkateboardingBlocks.handrail)
			{
				drawCuboid(te.getWorld(), te.getPos(), tessellator, buffer, -4, 7, 7.5, 4, 1, 1);
			}
			
			GlStateManager.enableLighting();
		}
		GlStateManager.popMatrix();
	}
	
	public void drawCuboid(World world, BlockPos pos, Tessellator tessellator, VertexBuffer buffer, double posX, double posY, double posZ, double width, double height, double depth)
	{	
		posX   /= 16;
		posY   /= 16;
		posZ   /= 16;
		width  /= 16;
		height /= 16;
		depth  /= 16;
		
		// Front
		getLighting(world, pos, EnumFacing.SOUTH, 1);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX, posY, posZ).tex(0, 0).endVertex();
		buffer.pos(posX, posY + height, posZ).tex(0, height).endVertex();
		buffer.pos(posX + width, posY + height, posZ).tex(width, height).endVertex();
		buffer.pos(posX + width, posY, posZ).tex(width, 0).endVertex();
		tessellator.draw();
		
		// Back
		getLighting(world, pos, EnumFacing.NORTH, 1);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX + width, posY, posZ + depth).tex(0, 0).endVertex();
		buffer.pos(posX + width, posY + height, posZ + depth).tex(0, height).endVertex();
		buffer.pos(posX, posY + height, posZ + depth).tex(width, height).endVertex();
		buffer.pos(posX, posY, posZ + depth).tex(width, 0).endVertex();
		tessellator.draw();
		
		// Left
		getLighting(world, pos, EnumFacing.WEST, 0);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX, posY, posZ + depth).tex(0, 0).endVertex();
		buffer.pos(posX, posY + height, posZ + depth).tex(0, height).endVertex();
		buffer.pos(posX, posY + height, posZ).tex(depth, height).endVertex();
		buffer.pos(posX, posY, posZ).tex(depth, 0).endVertex();
		tessellator.draw();
		
		// Right
		getLighting(world, pos, EnumFacing.EAST, 2);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX + width, posY, posZ).tex(0, 0).endVertex();
		buffer.pos(posX + width, posY + height, posZ).tex(0, height).endVertex();
		buffer.pos(posX + width, posY + height, posZ + depth).tex(depth, height).endVertex();
		buffer.pos(posX + width, posY, posZ + depth).tex(depth, 0).endVertex();
		tessellator.draw();
		
		// Bottom
		getLighting(world, pos, EnumFacing.DOWN, 0);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX + width, posY, posZ + depth).tex(1, 1).endVertex();
		buffer.pos(posX, posY, posZ + depth).tex(1, 0).endVertex();
		buffer.pos(posX, posY, posZ).tex(0, 0).endVertex();
		buffer.pos(posX + width, posY, posZ).tex(0, 1).endVertex();
		tessellator.draw();
		
		// Top
		getLighting(world, pos, EnumFacing.UP, 0);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX, posY + height, posZ).tex(0, 0).endVertex();
		buffer.pos(posX, posY + height, posZ + depth).tex(0, depth).endVertex();
		buffer.pos(posX + width, posY + height, posZ + depth).tex(width, depth).endVertex();
		buffer.pos(posX + width, posY + height, posZ).tex(width, 0).endVertex();
		tessellator.draw();
	}
	
	public void drawAngledCuboid(Tessellator tessellator, VertexBuffer buffer, double posX, double posY, double posZ, double width, double height, double depth, double heightOffset)
	{	
		posX   /= 16;
		posY   /= 16;
		posZ   /= 16;
		width  /= 16;
		height /= 16;
		depth  /= 16;
		heightOffset /= 16;
		
		// Front
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX, posY, posZ).tex(0, 0).endVertex();
		buffer.pos(posX, posY + height, posZ).tex(0, height).endVertex();
		buffer.pos(posX + width, posY + height + heightOffset, posZ).tex(width, height).endVertex();
		buffer.pos(posX + width, posY + heightOffset, posZ).tex(width, 0).endVertex();
		tessellator.draw();
		
		// Back
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX + width, posY + heightOffset, posZ + depth).tex(0, 0).endVertex();
		buffer.pos(posX + width, posY + height + heightOffset, posZ + depth).tex(0, height).endVertex();
		buffer.pos(posX, posY + height, posZ + depth).tex(width, height).endVertex();
		buffer.pos(posX, posY, posZ + depth).tex(width, 0).endVertex();
		tessellator.draw();
		
		// Left
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX, posY, posZ + depth).tex(0, 0).endVertex();
		buffer.pos(posX, posY + height, posZ + depth).tex(0, height).endVertex();
		buffer.pos(posX, posY + height, posZ).tex(depth, height).endVertex();
		buffer.pos(posX, posY, posZ).tex(depth, 0).endVertex();
		tessellator.draw();
		
		// Right
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX + width, posY + heightOffset, posZ).tex(0, 0).endVertex();
		buffer.pos(posX + width, posY + height + heightOffset, posZ).tex(0, height).endVertex();
		buffer.pos(posX + width, posY + height + heightOffset, posZ + depth).tex(depth, height).endVertex();
		buffer.pos(posX + width, posY + heightOffset, posZ + depth).tex(depth, 0).endVertex();
		tessellator.draw();
		
		// Bottom
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX + width, posY + heightOffset, posZ + depth).tex(1, 1).endVertex();
		buffer.pos(posX, posY, posZ + depth).tex(1, 0).endVertex();
		buffer.pos(posX, posY, posZ).tex(0, 0).endVertex();
		buffer.pos(posX + width, posY + heightOffset, posZ).tex(0, 1).endVertex();
		tessellator.draw();
		
		// Top
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX, posY + height, posZ).tex(0, 0).endVertex();
		buffer.pos(posX, posY + height, posZ + depth).tex(0, depth).endVertex();
		buffer.pos(posX + width, posY + height + heightOffset, posZ + depth).tex(width, depth).endVertex();
		buffer.pos(posX + width, posY + height + heightOffset, posZ).tex(width, 0).endVertex();
		tessellator.draw();
	}
	
	public void getLighting(World world, BlockPos pos, EnumFacing facing, int modify) 
	{
		pos = pos.offset(facing);
		int light = world.getLightFor(EnumSkyBlock.SKY, pos) - modify;
		int torch = world.getLightFor(EnumSkyBlock.BLOCK, pos);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, torch * 16F, light * 16F);
	}
}
