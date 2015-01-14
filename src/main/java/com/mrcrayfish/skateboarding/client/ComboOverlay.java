package com.mrcrayfish.skateboarding.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.util.ComboBuilder;

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
			Entity entity = player.ridingEntity;
			if (entity instanceof EntitySkateboard)
			{
				EntitySkateboard skateboard = (EntitySkateboard) entity;
				Minecraft mc = Minecraft.getMinecraft();
				int width = (mc.displayWidth / 4);
				
				mc.getTextureManager().bindTexture(resource);
				this.drawTexturedModalRect(10, 10, 0, 0, 104, 16);
				
				ComboBuilder combo = skateboard.combo;
				this.drawTexturedModalRect(12, 12, 0, 16, combo.getTime(), 14);
				
				EnumChatFormatting format = EnumChatFormatting.RESET;
				if(!combo.isInCombo())
				{
					format = EnumChatFormatting.GREEN;
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
					mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.YELLOW + Integer.toString((int) combo.getPoints()), (width) - stringWidth - 4, 25, 16777215);
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
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.startDrawingQuads();
        worldrenderer.addVertexWithUV((double)(x + 0), (double)(y + height), (double)0, (double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1));
        worldrenderer.addVertexWithUV((double)(x + width), (double)(y + height), (double)0, (double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1));
        worldrenderer.addVertexWithUV((double)(x + width), (double)(y + 0), (double)0l, (double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1));
        worldrenderer.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)0, (double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1));
        tessellator.draw();
    }
}
