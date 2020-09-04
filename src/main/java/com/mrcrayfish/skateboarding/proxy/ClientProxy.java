package com.mrcrayfish.skateboarding.proxy;

import com.mrcrayfish.skateboarding.api.TrickRegistry;
import com.mrcrayfish.skateboarding.api.map.TrickMap;
import com.mrcrayfish.skateboarding.client.ClientEvents;
import com.mrcrayfish.skateboarding.client.Keybinds;
import com.mrcrayfish.skateboarding.client.model.block.CustomLoader;
import com.mrcrayfish.skateboarding.client.render.RenderSkateboard;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit() 
	{
		RenderingRegistry.registerEntityRenderingHandler(EntitySkateboard.class, new RenderFactory());
		
		ModelLoaderRegistry.registerLoader(new CustomLoader());
	}
	
	@Override
	public void registerRenders()
	{
		MinecraftForge.EVENT_BUS.register(new ClientEvents());

		Keybinds.init();
		Keybinds.register();

		TrickRegistry.registerCombinations();
		TrickMap.printTrickMap(TrickMap.trickMap);
	}

	@Override
	public World getClientWorld()
	{
		return Minecraft.getMinecraft().world;
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
