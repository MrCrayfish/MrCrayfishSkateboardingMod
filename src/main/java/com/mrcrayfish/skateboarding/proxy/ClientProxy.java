package com.mrcrayfish.skateboarding.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.mrcrayfish.skateboarding.client.render.RenderSkateboard;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.init.SkateboardingItems;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenders()
	{
		SkateboardingItems.registerRenders();
		
		EntityRegistry.registerGlobalEntityID(EntitySkateboard.class, "csmSkateboard", EntityRegistry.findGlobalUniqueEntityId());
		RenderingRegistry.registerEntityRenderingHandler(EntitySkateboard.class, new RenderSkateboard(Minecraft.getMinecraft().getRenderManager()));
	}
}
