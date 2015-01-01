package com.mrcrayfish.skateboarding.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommonProxy
{
	public void registerRenders()
	{
	}

	public World getWorld()
	{
		return MinecraftServer.getServer().getEntityWorld();
	}
}
