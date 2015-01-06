package com.mrcrayfish.skateboarding.api.map;

import java.util.HashMap;
import java.util.Map;

import com.mrcrayfish.skateboarding.api.Trick;
import com.mrcrayfish.skateboarding.api.map.TrickMap.Key;

public class TrickEntry
{
	private Trick trick = null;
	private Map<Key, TrickEntry> trickMap = null;

	public TrickEntry setTrick(Trick trick)
	{
		this.trick = trick;
		return this;
	}

	public Trick getTrick()
	{
		return this.trick;
	}

	public Map<Key, TrickEntry> getTrickMap()
	{
		if (trickMap == null)
		{
			trickMap = new HashMap<Key, TrickEntry>();
		}
		return trickMap;
	}
}
