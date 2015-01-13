package com.mrcrayfish.skateboarding.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class ComboOverlay
{
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
				int height = (mc.displayHeight / 3) + 10;

				if (skateboard.combo.getTricks() != null)
				{
					GL11.glColor4d(150, 1, 1, 100);
					for (int i = 0; i < skateboard.combo.getTricks().length; i++)
					{
						int stringWidth = mc.fontRendererObj.getStringWidth(skateboard.combo.getTricks()[i]);
						mc.fontRendererObj.drawString(skateboard.combo.getTricks()[i], width - (stringWidth / 2), height + (i * 10), 1677721500);
					}
					
					int y = height + skateboard.combo.getTricks().length * 10 - (skateboard.combo.getSize() % 4 == 0 ? 10 : 0);
					int stringWidth = mc.fontRendererObj.getStringWidth(Integer.toString((int) skateboard.combo.getPoints()));
					mc.fontRendererObj.drawString(EnumChatFormatting.YELLOW + Integer.toString((int) skateboard.combo.getPoints()), width - (stringWidth / 2), y, 0);
				}
			}
		}
	}
}
