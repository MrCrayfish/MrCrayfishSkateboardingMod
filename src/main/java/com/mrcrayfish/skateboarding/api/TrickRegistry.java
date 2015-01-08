package com.mrcrayfish.skateboarding.api;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.skateboarding.api.map.TrickMap;
import com.mrcrayfish.skateboarding.api.map.TrickMap.Key;
import com.mrcrayfish.skateboarding.api.trick.Trick;
import com.mrcrayfish.skateboarding.tricks.flip.Trick360Heelflip;
import com.mrcrayfish.skateboarding.tricks.flip.Trick360Kickflip;
import com.mrcrayfish.skateboarding.tricks.flip.TrickHeelflip;
import com.mrcrayfish.skateboarding.tricks.flip.TrickKickflip;
import com.mrcrayfish.skateboarding.tricks.flip.TrickPopShove;
import com.mrcrayfish.skateboarding.tricks.flip.TrickVarialHeelflip;
import com.mrcrayfish.skateboarding.tricks.flip.TrickVarialKickflip;
import com.mrcrayfish.skateboarding.tricks.grind.Trick5050;
import com.mrcrayfish.skateboarding.tricks.grind.TrickBoardslide;

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

	/** Flips */
	public static final Trick kickflip = new TrickKickflip();
	public static final Trick kickflip_varial = new TrickVarialKickflip();
	public static final Trick heelflip = new TrickHeelflip();
	public static final Trick heelflip_varial = new TrickVarialHeelflip();
	public static final Trick popshove = new TrickPopShove();
	public static final Trick three_kickflip = new Trick360Kickflip();
	public static final Trick three_heelflip = new Trick360Heelflip();
	
	/** Grinds */
	public static final Trick fifty_fifty = new Trick5050(); 
	public static final Trick boardslide = new TrickBoardslide(); 
	
	/** Grabs */

	public static void registerTricks()
	{
		registerTrick(kickflip);
		registerTrick(kickflip_varial);
		registerTrick(three_kickflip);
		registerTrick(heelflip);
		registerTrick(heelflip_varial);
		registerTrick(three_heelflip);
		registerTrick(popshove);
		registerTrick(fifty_fifty);
		registerTrick(boardslide);
	}

	@SideOnly(Side.CLIENT)
	public static void registerCombinations()
	{
		TrickMap.addCombo(kickflip, Key.LEFT);
		TrickMap.addCombo(kickflip_varial, Key.LEFT, Key.DOWN);
		TrickMap.addCombo(three_kickflip, Key.LEFT, Key.DOWN, Key.DOWN);
		TrickMap.addCombo(heelflip, Key.RIGHT);
		TrickMap.addCombo(heelflip_varial, Key.RIGHT, Key.DOWN);
		TrickMap.addCombo(three_heelflip, Key.RIGHT, Key.DOWN, Key.DOWN);
		TrickMap.addCombo(popshove, Key.LEFT, Key.RIGHT);
		TrickMap.addCombo(fifty_fifty, Key.UP);
		TrickMap.addCombo(boardslide, Key.DOWN);
	}
}
