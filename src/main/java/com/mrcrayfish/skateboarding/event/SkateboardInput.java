package com.mrcrayfish.skateboarding.event;

import com.mrcrayfish.skateboarding.api.TrickRegistry;
import com.mrcrayfish.skateboarding.client.Combination;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.network.PacketHandler;
import com.mrcrayfish.skateboarding.network.message.MessageJump;
import com.mrcrayfish.skateboarding.network.message.MessageTrick;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class SkateboardInput
{
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		Entity entity = Minecraft.getMinecraft().thePlayer.ridingEntity;
		if (entity != null && entity instanceof EntitySkateboard)
		{
			EntitySkateboard skateboard = (EntitySkateboard) entity;
			if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown())
			{
				if (!skateboard.jumping)
				{
					skateboard.jump();
					PacketHandler.INSTANCE.sendToServer(new MessageJump(skateboard.getEntityId()));
				}
			}
			else
			{
				for (Combination comb : TrickRegistry.getRegisteredCombinations()) 
				{
					if (comb.allPressed())
					{
						if (skateboard.jumping && !skateboard.inTrick)
						{
							System.out.println("Key Pressed");
							int trickId = TrickRegistry.getTrickId(comb);
							skateboard.startTrick(TrickRegistry.getTrick(trickId));
							PacketHandler.INSTANCE.sendToServer(new MessageTrick(skateboard.getEntityId(), trickId));
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onTick(ClientTickEvent event)
	{

	}
}
