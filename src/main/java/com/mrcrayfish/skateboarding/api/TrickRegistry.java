package com.mrcrayfish.skateboarding.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.skateboarding.tricks.TrickHeelflip;
import com.mrcrayfish.skateboarding.tricks.TrickKickflip;
import com.mrcrayfish.skateboarding.tricks.TrickPopShove;
import com.mrcrayfish.skateboarding.tricks.TrickTreflip;

public class TrickRegistry
{
	private int uniqueId;

	@SideOnly(Side.CLIENT)
	private static List<KeyBinding> keys = new ArrayList<KeyBinding>();

	@SideOnly(Side.CLIENT)
	private static Map<KeyBinding, Integer> bindToId = new HashMap<KeyBinding, Integer>();

	private static Map<Integer, Trick> idToTrick = new HashMap<Integer, Trick>();

	public static void registerTrick(Trick trick)
	{
		idToTrick.put(idToTrick.size(), trick);
	}

	public static Trick getTrick(int trickId)
	{
		return idToTrick.get(trickId);
	}

	@SideOnly(Side.CLIENT)
	public static int getTrickId(KeyBinding key)
	{
		return bindToId.get(key);
	}

	@SideOnly(Side.CLIENT)
	public static ArrayList<KeyBinding> getRegisteredKeys()
	{
		return (ArrayList<KeyBinding>) keys;
	}

	public static final Trick kickflip = new TrickKickflip();
	public static final Trick heelflip = new TrickHeelflip();
	public static final Trick popshove = new TrickPopShove();
	public static final Trick treflip = new TrickTreflip();

	public static void registerTricks()
	{
		registerTrick(kickflip);
		registerTrick(heelflip);
		registerTrick(popshove);
	}

	@SideOnly(Side.CLIENT)
	public static void registerKeyBinds()
	{
		registerKeyBind(Minecraft.getMinecraft().gameSettings.keyBindLeft, kickflip);
		registerKeyBind(Minecraft.getMinecraft().gameSettings.keyBindRight, heelflip);
		registerKeyBind(Minecraft.getMinecraft().gameSettings.keyBindBack, popshove);
	}

	@SideOnly(Side.CLIENT)
	public static void registerKeyBind(KeyBinding key, Trick trick)
	{
		if (!keys.contains(key))
		{
			keys.add(key);
			for (int i = 0; i < idToTrick.size(); i++)
			{
				if (idToTrick.get(i) == trick)
				{
					bindToId.put(key, i);
				}
			}
		}
	}
}
