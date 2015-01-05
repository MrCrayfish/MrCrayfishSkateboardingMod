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

import com.mrcrayfish.skateboarding.api.Trick;
import com.mrcrayfish.skateboarding.api.TrickRegistry;
import com.mrcrayfish.skateboarding.api.map.TrickMap;
import com.mrcrayfish.skateboarding.api.map.TrickMap.Key;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.network.PacketHandler;
import com.mrcrayfish.skateboarding.network.message.MessageJump;
import com.mrcrayfish.skateboarding.network.message.MessageTrick;

public class SkateboardInput {
	private List<Key> keys = new ArrayList<Key>();
	private int timeLeft;

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		Entity entity = Minecraft.getMinecraft().thePlayer.ridingEntity;
		if (entity != null && entity instanceof EntitySkateboard) {
			EntitySkateboard skateboard = (EntitySkateboard) entity;
			if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()) {
				if (!skateboard.jumping) {
					skateboard.jump();
					PacketHandler.INSTANCE.sendToServer(new MessageJump(skateboard.getEntityId()));
				}
			} else {
				if (skateboard.jumping && !skateboard.inTrick) {
					GameSettings settings = Minecraft.getMinecraft().gameSettings;
					if (keys.size() < 4) {
						if (settings.keyBindForward.isKeyDown()) {
							keys.add(Key.UP);
							timeLeft += 5;
						} else if (settings.keyBindBack.isKeyDown()) {
							System.out.println("Addign Down");
							keys.add(Key.DOWN);
							timeLeft += 5;
						} else if (settings.keyBindLeft.isKeyDown()) {
							keys.add(Key.LEFT);
							timeLeft += 5;
						} else if (settings.keyBindRight.isKeyDown()) {
							keys.add(Key.RIGHT);
							timeLeft += 5;
						}
						KeyBinding.unPressAllKeys();
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		if (keys.size() > 0 && timeLeft == 0) {
			Entity entity = Minecraft.getMinecraft().thePlayer.ridingEntity;
			if (entity != null && entity instanceof EntitySkateboard) {
				EntitySkateboard skateboard = (EntitySkateboard) entity;
				Trick trick = TrickMap.getTrick(keys.toArray(new Key[0]));
				System.out.println(trick);
				if (trick != null) {
					PacketHandler.INSTANCE.sendToServer(new MessageTrick(skateboard.getEntityId(), TrickRegistry.getTrickId(trick)));
					skateboard.startTrick(trick);
				}
				keys.clear();
				TrickMap.printTrickMap(TrickMap.trickMap);
			}
		}

		if (timeLeft > 0) {
			timeLeft--;
		}
	}
}
