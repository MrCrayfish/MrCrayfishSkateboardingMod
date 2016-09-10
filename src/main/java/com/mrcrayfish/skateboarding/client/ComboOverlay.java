package com.mrcrayfish.skateboarding.client;

import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.util.ComboBuilder;

import net.minecraft.client.Minecraft;
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
	private ModelResourceLocation resource = new ModelResourceLocation("csm:textures/gui/overlay.png");
	
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
				int width = (mc.displayWidth / 4);
				
				mc.getTextureManager().bindTexture(resource);
				this.drawTexturedModalRect(10, 10, 0, 0, 106, 15);
				
				ComboBuilder combo = skateboard.combo;
				this.drawTexturedModalRect(15, 14, 0, 15, combo.getTime(), 7);
				
				ChatFormatting format = ChatFormatting.RESET;
				if(!combo.isInCombo())
				{
					format = ChatFormatting.GREEN;
				}
				
				if (combo.getTricks().length > 0)
				{
					for (int i = 0; i < combo.getTricks().length; i++)
					{
						boolean skip = false;
						if (combo.hasRecentlyAdded() && i == combo.getTricks().length - 1)
						{
							skip = true;
						}
						if (!skip)
						{
							int y = 40 + (combo.getTricks().length * 10) - (i * 10) + combo.getAnimation() - (combo.hasRecentlyAdded() ? 10 : 0);
							if (y * 2 < mc.displayHeight)
							{
								mc.fontRendererObj.drawStringWithShadow(format + combo.getTricks()[i], 9, y - 1, 16777215);
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
	
	public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer worldrenderer = tessellator.getBuffer();	
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)(x + 0), (double)(y + height), (double)0).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1));
        worldrenderer.pos((double)(x + width), (double)(y + height), (double)0).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1));
        worldrenderer.pos((double)(x + width), (double)(y + 0), (double)0l).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1));
        worldrenderer.pos((double)(x + 0), (double)(y + 0), (double)0).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1));
        tessellator.draw();
    }
}
