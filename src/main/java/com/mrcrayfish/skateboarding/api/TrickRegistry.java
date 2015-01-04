package com.mrcrayfish.skateboarding.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.skateboarding.client.Combination;
import com.mrcrayfish.skateboarding.tricks.TrickHeelflip;
import com.mrcrayfish.skateboarding.tricks.TrickKickflip;
import com.mrcrayfish.skateboarding.tricks.TrickPopShove;
import com.mrcrayfish.skateboarding.tricks.TrickTreflip;

public class TrickRegistry
{
	private int uniqueId;

	@SideOnly(Side.CLIENT)
	private static List<Combination> combinations = new ArrayList<Combination>();

	@SideOnly(Side.CLIENT)
	private static Map<Combination, Integer> combToId = new HashMap<Combination, Integer>();

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
	public static int getTrickId(Combination comb)
	{
		return combToId.get(comb);
	}

	@SideOnly(Side.CLIENT)
	public static ArrayList<Combination> getRegisteredCombinations()
	{
		return (ArrayList<Combination>) combinations;
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
		registerTrick(treflip);
	}

	@SideOnly(Side.CLIENT)
	public static void registerKeyBinds()
	{
		registerCombination(new Combination(kickflip, Minecraft.getMinecraft().gameSettings.keyBindLeft));
		registerCombination(new Combination(heelflip, Minecraft.getMinecraft().gameSettings.keyBindRight));
		registerCombination(new Combination(popshove, Minecraft.getMinecraft().gameSettings.keyBindBack));
		registerCombination(new Combination(treflip, Minecraft.getMinecraft().gameSettings.keyBindLeft, Minecraft.getMinecraft().gameSettings.keyBindBack));
	}

	@SideOnly(Side.CLIENT)
	public static void registerCombination(Combination comb)
	{
		if (!combinations.contains(comb))
		{
			combinations.add(comb);
			for (int i = 0; i < idToTrick.size(); i++)
			{
				if (idToTrick.get(i) == comb.getTrick())
				{
					System.out.println("Registering combToId");
					combToId.put(comb, i);
				}
			}
		}
	}
}
