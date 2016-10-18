package com.mrcrayfish.skateboarding.event;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.mrcrayfish.skateboarding.api.map.TrickMap;
import com.mrcrayfish.skateboarding.api.map.TrickMap.Key;
import com.mrcrayfish.skateboarding.api.trick.Trick;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.network.PacketHandler;
import com.mrcrayfish.skateboarding.network.message.MessageJump;
import com.mrcrayfish.skateboarding.network.message.MessagePush;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class SkateboardInput {
	
	private List<Key> keys = new LinkedList<Key>();
	private int timeLeft;
	
	public static boolean pumping = false;
	public static int pumpingTimer = 0;

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) 
	{
		Entity entity = Minecraft.getMinecraft().thePlayer.getRidingEntity();
		if (entity != null && entity instanceof EntitySkateboard) 
		{
			char c = Keyboard.getEventCharacter();
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
				if (keys.size() < 4) 
				{
					switch(c) {
					case 'w':
						addKeyToCombo(Key.UP);
						break;
					case 's':
						addKeyToCombo(Key.DOWN);
						break;
					case 'a':
						addKeyToCombo(Key.LEFT);
						break;
					case 'd':
						addKeyToCombo(Key.RIGHT);
						break;
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
	
	public void addKeyToCombo(Key key)
	{
		keys.add(key);
		timeLeft = 6;
	}

	@SubscribeEvent
	public void onTick(ClientTickEvent event) 
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(player != null)
		{
			Entity entity = player.getRidingEntity();
			if (entity instanceof EntitySkateboard) 
			{
				if(pumping && pumpingTimer < 60) 
				{
					pumpingTimer++;
				}
				
				if (keys.size() > 0 && timeLeft == 0) 
				{
					EntitySkateboard skateboard = (EntitySkateboard) entity;
					Trick trick = TrickMap.getTrick(keys.iterator());
					System.out.println(trick);
					if (trick != null && !skateboard.isInTrick()) 
					{
						skateboard.startTrick(trick);
						//PacketHandler.INSTANCE.sendToServer(new MessageTrick(skateboard.getEntityId(), TrickRegistry.getTrickId(trick)));
					}
					keys.clear();
				}
				
				if (timeLeft > 0) 
				{
					timeLeft--;
				}
			}
		}
	}
}
