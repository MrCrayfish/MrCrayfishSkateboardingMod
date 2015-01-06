package com.mrcrayfish.skateboarding.api;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.skateboarding.api.map.TrickMap;
import com.mrcrayfish.skateboarding.api.map.TrickMap.Key;
import com.mrcrayfish.skateboarding.tricks.TrickHeelflip;
import com.mrcrayfish.skateboarding.tricks.TrickKickflip;
import com.mrcrayfish.skateboarding.tricks.TrickPopShove;
import com.mrcrayfish.skateboarding.tricks.TrickTreflip;

public class TrickRegistry
{
	private static Map<Trick, Integer> trickToId = new HashMap<Trick, Integer>();
	private static Map<Integer, Trick> idToTrick = new HashMap<Integer, Trick>();

	public static void registerTrick(Trick trick)
	{
		idToTrick.put(idToTrick.size(), trick);
		trickToId.put(trick, trickToId.size());
	}

	public static Trick getTrick(int trickId)
	{
		return idToTrick.get(trickId);
	}

	public static int getTrickId(Trick trick)
	{
		return trickToId.get(trick);
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
	public static void registerCombinations()
	{
		TrickMap.addCombo(kickflip, Key.LEFT);
		TrickMap.addCombo(heelflip, Key.RIGHT);
		TrickMap.addCombo(popshove, Key.DOWN);
		TrickMap.addCombo(treflip, Key.LEFT, Key.DOWN);
	}
}
