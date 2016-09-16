package com.mrcrayfish.skateboarding.client;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.event.SkateboardInput;
import com.mrcrayfish.skateboarding.util.ComboBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ComboOverlay
{
	private static final ModelResourceLocation RESOURCE = new ModelResourceLocation("csm:textures/gui/overlay.png");
	
	private static final Color PUMP_FULL = new Color(0, 131, 183, 180);
	private static final Color PUMP_ONE = new Color(183, 33, 0, 180);
	private static final Color PUMP_TWO = new Color(183, 168, 0, 180);
	private static final Color PUMP_THREE = new Color(48, 183, 0, 180);
	
	@SubscribeEvent
	public void onTick(TickEvent.RenderTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.START))
		{
			return;
		}
		renderCombo();
	}

	public void renderCombo()
	{
		if (Minecraft.getMinecraft().inGameHasFocus)
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			Entity entity = player.getRidingEntity();
			if (entity instanceof EntitySkateboard)
			{
				EntitySkateboard skateboard = (EntitySkateboard) entity;
				Minecraft mc = Minecraft.getMinecraft();
				ScaledResolution scaledresolution = new ScaledResolution(mc);
				int width = scaledresolution.getScaledWidth() / 2;
				int height = scaledresolution.getScaledHeight() / 2;
				
				mc.getTextureManager().bindTexture(RESOURCE);
				GlStateManager.enableBlend();

				// Pumping
				if(SkateboardInput.pumping)
				{
					Color color;
					if(SkateboardInput.pumpingTimer < 20){
						color = PUMP_ONE;
					} else if(SkateboardInput.pumpingTimer < 40){
						color = PUMP_TWO;
					} else if(SkateboardInput.pumpingTimer < 60){
						color = PUMP_THREE;
					} else {
						color = PUMP_FULL;
					}
					this.drawRect(width - 40,  height + 60 - SkateboardInput.pumpingTimer, 6, SkateboardInput.pumpingTimer, color);
					this.drawRectWithTexture(width - 41, height - 1, 0, 8, 22, 8, 62, 8, 62);
				}
				
				GlStateManager.color(1.0F, 1.0F, 1.0F);
				ComboBuilder combo = skateboard.combo;
				
				// Combo Timer Frame
				this.drawRectWithTexture(10, 10, 0, 0, 0, 106, 15, 106, 15);
				
				// Juice
				this.drawRectWithTexture(15, 14, 0, 0, 15, Math.min(96, combo.getTime()), 7, Math.min(96, combo.getTime()), 7);
				
				ChatFormatting format = ChatFormatting.RESET;
				if(!combo.isInCombo())
				{
					format = ChatFormatting.GREEN;
				}
				
				if (combo.getTricks().length > 0)
				{
					String[] tricks = combo.getTricks();
					for (int i = 0; i < tricks.length; i++)
					{
						boolean skip = false;
						if (combo.hasRecentlyAdded() && i == combo.getTricks().length - 1)
						{
							skip = true;
						}
						if (!skip)
						{
							int y = 40 + (tricks.length * 10) - (i * 10) + combo.getAnimation() - (combo.hasRecentlyAdded() ? 10 : 0);
							if (y * 2 < mc.displayHeight)
							{
								if(i == tricks.length - 1 && skateboard.isGrinding()) {
									format = ChatFormatting.YELLOW;
								} else {
									format = ChatFormatting.WHITE;
								}
								mc.fontRendererObj.drawStringWithShadow(format + tricks[i], 9, y - 1, 16777215);
							}
						}
					}
					int stringWidth = mc.fontRendererObj.getStringWidth(Integer.toString((int) combo.getPoints()));
					GL11.glScalef(2.0F, 2.0F, 2.0F);
					mc.fontRendererObj.drawStringWithShadow(ChatFormatting.YELLOW + Integer.toString((int) combo.getPoints()), 5, 15, 16777215);
					GL11.glScalef(1.0F, 1.0F, 1.0F);
				}
			}
		}
	}
	
	public void drawRect(int x, int y, int width, int height, Color color)
    {
		GlStateManager.disableTexture2D();
		GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 0.5F);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer worldrenderer = tessellator.getBuffer();	
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double)(x + 0), (double)(y + height), (double)0).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), (double)0).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + 0), (double)0).endVertex();
        worldrenderer.pos((double)(x + 0), (double)(y + 0), (double)0).endVertex();
        tessellator.draw();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        
    }
	
	public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer worldrenderer = tessellator.getBuffer();	
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)(x + 0), (double)(y + height), (double)0).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), (double)0).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + 0), (double)0).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        worldrenderer.pos((double)(x + 0), (double)(y + 0), (double)0).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        tessellator.draw();
    }
	
	public static void drawRectWithTexture(double x, double y, double z, float u, float v, int width, int height, float textureWidth, float textureHeight)
    {
		float scale = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer worldrenderer = tessellator.getBuffer();	
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)x, (double)(y + height), z).tex((double)(u * scale), (double)(v + textureHeight) * scale).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), z).tex((double)(u + textureWidth) * scale, (double)(v + textureHeight) * scale).endVertex();
        worldrenderer.pos((double)(x + width), (double)y, z).tex((double)(u + textureWidth) * scale, (double)(v * scale)).endVertex();
        worldrenderer.pos((double)x, (double)y, z).tex((double)(u * scale), (double)(v * scale)).endVertex();
        tessellator.draw();
    }
}
