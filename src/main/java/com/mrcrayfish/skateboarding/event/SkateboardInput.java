package com.mrcrayfish.skateboarding.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

import com.mrcrayfish.skateboarding.api.TrickRegistry;
import com.mrcrayfish.skateboarding.api.map.TrickMap;
import com.mrcrayfish.skateboarding.api.map.TrickMap.Key;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.api.trick.Trick;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.network.PacketHandler;
import com.mrcrayfish.skateboarding.network.message.MessageJump;
import com.mrcrayfish.skateboarding.network.message.MessageTrick;
import com.mrcrayfish.skateboarding.util.GrindHelper;

public class SkateboardInput
{
	private List<Key> keys = new ArrayList<Key>();
	private int timeLeft;

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		Entity entity = Minecraft.getMinecraft().thePlayer.ridingEntity;
		if (entity != null && entity instanceof EntitySkateboard)
		{
			EntitySkateboard skateboard = (EntitySkateboard) entity;
			if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown())
			{
				if (!skateboard.isJumping())
				{
					skateboard.jump();
					PacketHandler.INSTANCE.sendToServer(new MessageJump(skateboard.getEntityId()));
				}
			}
			else
			{
				if (skateboard.isJumping())
				{
					GameSettings settings = Minecraft.getMinecraft().gameSettings;
					if (keys.size() < 4)
					{
						if (settings.keyBindForward.isKeyDown())
						{
							keys.add(Key.UP);
							timeLeft = 6;
						}
						else if (settings.keyBindBack.isKeyDown())
						{
							keys.add(Key.DOWN);
							timeLeft = 6;
						}
						else if (settings.keyBindLeft.isKeyDown())
						{
							keys.add(Key.LEFT);
							timeLeft = 6;
						}
						else if (settings.keyBindRight.isKeyDown())
						{
							keys.add(Key.RIGHT);
							timeLeft = 6;
						}
					}
					KeyBinding.unPressAllKeys();
				}
			}
		}
	}

	@SubscribeEvent
	public void onTick(ClientTickEvent event)
	{
		if (keys.size() > 0 && timeLeft == 0)
		{
			System.out.println("Perforing Combo: ");
			for(Key key : keys)
			{
				System.out.println(key.name() + " ");
			}
			System.out.println("");
			Entity entity = Minecraft.getMinecraft().thePlayer.ridingEntity;
			if (entity != null && entity instanceof EntitySkateboard)
			{
				EntitySkateboard skateboard = (EntitySkateboard) entity;
				Trick trick = TrickMap.getTrick(keys.toArray(new Key[0]));

				if (trick != null && !skateboard.isInTrick())
				{
					PacketHandler.INSTANCE.sendToServer(new MessageTrick(skateboard.getEntityId(), TrickRegistry.getTrickId(trick)));
					skateboard.startTrick(trick);
				}
			}
			keys.clear();
		}

		if (timeLeft > 0)
		{
			System.out.println(timeLeft);
			timeLeft--;
		}
	}
}
