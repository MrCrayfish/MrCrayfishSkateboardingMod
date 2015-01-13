package com.mrcrayfish.skateboarding.client;

import net.minecraft.client.Minecraft;
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

				ComboBuilder combo = skateboard.combo;

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
								mc.fontRendererObj.drawStringWithShadow(combo.getTricks()[i], 9, y - 1, 16777215);
							}
						}
					}
					int stringWidth = mc.fontRendererObj.getStringWidth(Integer.toString((int) combo.getPoints()));
					GL11.glColor4d(1, 1, 1, 100);
					GL11.glScalef(2.0F, 2.0F, 2.0F);
					mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.YELLOW + Integer.toString((int) combo.getPoints()), (width) - stringWidth - 4, 25, 16777215);
					GL11.glScalef(1.0F, 1.0F, 1.0F);
				}
			}
		}
	}
}
