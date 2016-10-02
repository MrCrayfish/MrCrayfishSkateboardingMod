package com.mrcrayfish.skateboarding.event;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

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
import com.mrcrayfish.skateboarding.api.trick.Flip;
import com.mrcrayfish.skateboarding.api.trick.Trick;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.network.PacketHandler;
import com.mrcrayfish.skateboarding.network.message.MessageJump;
import com.mrcrayfish.skateboarding.network.message.MessagePush;
import com.mrcrayfish.skateboarding.network.message.MessageTrick;

public class SkateboardInput {
	private List<Key> keys = new ArrayList<Key>();
	private int timeLeft;
	
	public static boolean pumping = false;
	public static int pumpingTimer = 0;

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) 
	{
		Entity entity = Minecraft.getMinecraft().thePlayer.getRidingEntity();
		if (entity != null && entity instanceof EntitySkateboard) 
		{
			EntitySkateboard skateboard = (EntitySkateboard) entity;
			if(Keyboard.getEventKeyState())
			{
				// Switch Regular to Goofy
				if (Minecraft.getMinecraft().gameSettings.keyBindDrop.isPressed()) 
				{
					skateboard.setGoofy(!skateboard.isGoofy());
				}

				// Pumping
				if (Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed()) 
				{
					pumping = true;
				}
				
				// Pushing
				if(!skateboard.isJumping()) 
				{
					if (Minecraft.getMinecraft().gameSettings.keyBindForward.isPressed()) 
					{
						PacketHandler.INSTANCE.sendToServer(new MessagePush(skateboard.getEntityId()));
					}
				}
				
				// Trick Combinations
				GameSettings settings = Minecraft.getMinecraft().gameSettings;
				if (keys.size() < 4) 
				{
					if (settings.keyBindForward.isPressed()) 
					{
						keys.add(Key.UP);
						timeLeft = 6;
					} 
					else if (settings.keyBindBack.isPressed())
					{
						keys.add(Key.DOWN);
						timeLeft = 6;
					} 
					else if (settings.keyBindLeft.isPressed()) 
					{
						keys.add(Key.LEFT);
						timeLeft = 6;
					} 
					else if (settings.keyBindRight.isPressed()) 
					{
						keys.add(Key.RIGHT);
						timeLeft = 6;
					}
				}
			}
			else
			{
				if(!Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown() && pumping) 
				{
					if(!skateboard.isJumping()) 
					{
						skateboard.jump(pumpingTimer / 20.0);
						PacketHandler.INSTANCE.sendToServer(new MessageJump(pumpingTimer / 20.0));
					}
					pumping = false;
					pumpingTimer = 0;
				}
			}
		}
	}

	@SubscribeEvent
	public void onTick(ClientTickEvent event) 
	{
		if(pumping && pumpingTimer < 60) 
		{
			pumpingTimer++;
		}
		
		if (keys.size() > 0 && timeLeft == 0) 
		{
			Entity entity = Minecraft.getMinecraft().thePlayer.getRidingEntity();
			if (entity != null && entity instanceof EntitySkateboard) 
			{
				EntitySkateboard skateboard = (EntitySkateboard) entity;
				Trick trick = TrickMap.getTrick(keys.toArray(new Key[0]));
				if (trick != null && !skateboard.isInTrick()) 
				{
					skateboard.startTrick(trick);
					//PacketHandler.INSTANCE.sendToServer(new MessageTrick(skateboard.getEntityId(), TrickRegistry.getTrickId(trick)));
					
				}
			}
			keys.clear();
		}

		if (timeLeft > 0) 
		{
			timeLeft--;
		}
	}
}
