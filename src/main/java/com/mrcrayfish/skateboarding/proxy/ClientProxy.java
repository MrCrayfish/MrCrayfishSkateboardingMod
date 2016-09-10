package com.mrcrayfish.skateboarding.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import api.player.model.ModelPlayerAPI;
import api.player.render.RenderPlayerAPI;

import com.mrcrayfish.skateboarding.api.TrickRegistry;
import com.mrcrayfish.skateboarding.api.map.TrickMap;
import com.mrcrayfish.skateboarding.client.Keybinds;
import com.mrcrayfish.skateboarding.client.model.ModelPlayerOverride;
import com.mrcrayfish.skateboarding.client.render.RenderSkateboard;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.init.SkateboardingItems;

public class ClientProxy extends CommonProxy
{
	
	@Override
	public void preInit() 
	{
		RenderingRegistry.registerEntityRenderingHandler(EntitySkateboard.class, new RenderFactory());
	}
	
	@Override
	public void registerRenders()
	{
		SkateboardingItems.registerRenders();

		ModelPlayerAPI.register("csm", ModelPlayerOverride.class);

		Keybinds.init();
		Keybinds.register();

		TrickRegistry.registerCombinations();
		TrickMap.printTrickMap(TrickMap.trickMap);
	}

	@Override
	public World getClientWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
	
	private static class RenderFactory implements IRenderFactory<EntitySkateboard>
	{
		@Override
		public Render<? super EntitySkateboard> createRenderFor(RenderManager manager) 
		{
			return new RenderSkateboard(manager);
		}
	}
}
