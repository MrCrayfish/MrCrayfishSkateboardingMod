package com.mrcrayfish.skateboarding.api;

import java.util.HashMap;
import java.util.Map;

import com.mrcrayfish.skateboarding.tricks.Trick;

public class TrickRegistry
{
	private static Map<Integer, Trick> tricks = new HashMap<Integer, Trick>();
	
	public static void registerTrick(Trick trick)
	{
		tricks.put(tricks.size(), trick);
	}
	
	public Trick getTrick(int trickId)
	{
		return tricks.get(trickId);
	}
}
