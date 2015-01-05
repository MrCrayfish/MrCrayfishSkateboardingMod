package com.mrcrayfish.skateboarding.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import api.player.model.ModelPlayerAPI;

import com.mrcrayfish.skateboarding.api.TrickRegistry;
import com.mrcrayfish.skateboarding.api.map.TrickMap;
import com.mrcrayfish.skateboarding.client.Keybinds;
import com.mrcrayfish.skateboarding.client.model.ModelPlayerOverride;
import com.mrcrayfish.skateboarding.client.render.RenderSkateboard;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.init.SkateboardingItems;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenders()
	{
		SkateboardingItems.registerRenders();
		
		ModelPlayerAPI.register("csm", ModelPlayerOverride.class);
		
		EntityRegistry.registerGlobalEntityID(EntitySkateboard.class, "csmSkateboard", EntityRegistry.findGlobalUniqueEntityId());
		RenderingRegistry.registerEntityRenderingHandler(EntitySkateboard.class, new RenderSkateboard(Minecraft.getMinecraft().getRenderManager()));
		
		Keybinds.init();
		Keybinds.register();
		
		TrickRegistry.registerCombinations();
		TrickMap.printTrickMap(TrickMap.trickMap);
	}
	
	public World getWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
}
