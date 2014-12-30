package com.mrcrayfish.skateboarding;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import api.player.model.ModelPlayerAPI;

import com.mrcrayfish.skateboarding.client.model.ModelPlayerOverride;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.event.SkateboardingEvents;
import com.mrcrayfish.skateboarding.init.SkateboardingItems;
import com.mrcrayfish.skateboarding.proxy.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class MrCrayfishSkateboardingMod {
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static CreativeTabs skateTab = new SkateTab("skateTab");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		SkateboardingItems.init();
		SkateboardingItems.register();
		
		EntityRegistry.registerModEntity(EntitySkateboard.class, "csmSkateboard", 0, this, 80, 3, true);
		
		ModelPlayerAPI.register("csm", ModelPlayerOverride.class);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRenders();
		
		MinecraftForge.EVENT_BUS.register(new SkateboardingEvents());
		FMLCommonHandler.instance().bus().register(new SkateboardingEvents());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}

}
