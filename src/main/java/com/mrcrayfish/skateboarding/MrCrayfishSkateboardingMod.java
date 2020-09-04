package com.mrcrayfish.skateboarding;

import com.mrcrayfish.skateboarding.api.TrickRegistry;
import com.mrcrayfish.skateboarding.client.ComboOverlay;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.event.SkateboardInput;
import com.mrcrayfish.skateboarding.init.RegistrationHandler;
import com.mrcrayfish.skateboarding.network.PacketHandler;
import com.mrcrayfish.skateboarding.proxy.CommonProxy;
import com.mrcrayfish.skateboarding.tileentity.TileEntityCornerSlope;
import com.mrcrayfish.skateboarding.tileentity.TileEntitySlope;
import com.mrcrayfish.skateboarding.tileentity.TileEntityStair;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions = Reference.MC_VERSION, dependencies = Reference.DEPENDENCIES)
public class MrCrayfishSkateboardingMod
{
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static CreativeTabs skateTab = new SkateTab("skateTab");
	
	public static DamageSourceSkateboard skateboardDamage = new DamageSourceSkateboard("skateboard");

	public int nextEntityId;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit();

		RegistrationHandler.init();
		PacketHandler.init();

		TrickRegistry.registerTricks();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRenders();

		registerEntity("skateboard", EntitySkateboard.class);
		
		GameRegistry.registerTileEntity(TileEntitySlope.class, Reference.MOD_ID + "TileEntitySlope");
		GameRegistry.registerTileEntity(TileEntityCornerSlope.class, Reference.MOD_ID + "TileEntityCornerSlope");
		GameRegistry.registerTileEntity(TileEntityStair.class, Reference.MOD_ID + "TileEntityStair");

		if (event.getSide() == Side.CLIENT)
		{
			MinecraftForge.EVENT_BUS.register(new SkateboardInput());
			MinecraftForge.EVENT_BUS.register(new ComboOverlay());
		}
	}

	private void registerEntity(String id, Class<? extends Entity> clazz)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, id), clazz, id, nextEntityId++, this, 64, 1, true);
	}
}
