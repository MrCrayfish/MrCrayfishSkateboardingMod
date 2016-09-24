package com.mrcrayfish.skateboarding.proxy;

import com.mrcrayfish.skateboarding.api.TrickRegistry;
import com.mrcrayfish.skateboarding.api.map.TrickMap;
import com.mrcrayfish.skateboarding.client.Keybinds;
import com.mrcrayfish.skateboarding.client.model.ModelPlayerOverride;
import com.mrcrayfish.skateboarding.client.render.RenderSkateboard;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.init.SkateboardingBlocks;
import com.mrcrayfish.skateboarding.init.SkateboardingItems;
import com.mrcrayfish.skateboarding.tileentity.TileEntityCornerSlope;
import com.mrcrayfish.skateboarding.tileentity.TileEntitySlope;
import com.mrcrayfish.skateboarding.tileentity.renderer.CornerSlopeRenderer;
import com.mrcrayfish.skateboarding.tileentity.renderer.SlopeRenderer;

import api.player.model.ModelPlayerAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

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
		SkateboardingBlocks.registerRenders();
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySlope.class, new SlopeRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCornerSlope.class, new CornerSlopeRenderer());

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
