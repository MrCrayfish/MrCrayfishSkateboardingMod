package com.mrcrayfish.skateboarding;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.mrcrayfish.skateboarding.api.TrickRegistry;
import com.mrcrayfish.skateboarding.client.ComboOverlay;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.event.SkateboardInput;
import com.mrcrayfish.skateboarding.init.SkateboardingItems;
import com.mrcrayfish.skateboarding.network.PacketHandler;
import com.mrcrayfish.skateboarding.proxy.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions = "[1.9.4]", dependencies = "required-after:RenderPlayerAPI")
public class MrCrayfishSkateboardingMod
{
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static CreativeTabs skateTab = new SkateTab("skateTab");
	
	public static DamageSourceSkateboard skateboardDamage = new DamageSourceSkateboard("skateboard");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit();
		
		SkateboardingItems.init();
		SkateboardingItems.register();

		PacketHandler.init();

		TrickRegistry.registerTricks();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRenders();
		
		EntityRegistry.registerModEntity(EntitySkateboard.class, "csmSkateboard", 0, this, 64, 20, false);

		if (event.getSide() == Side.CLIENT)
		{
			FMLCommonHandler.instance().bus().register(new SkateboardInput());
			FMLCommonHandler.instance().bus().register(new ComboOverlay());
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}

}
