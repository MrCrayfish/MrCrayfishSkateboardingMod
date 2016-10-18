package com.mrcrayfish.skateboarding.api.map;

import java.util.HashMap;
import java.util.Map;

import com.mrcrayfish.skateboarding.api.map.TrickMap.Key;
import com.mrcrayfish.skateboarding.api.trick.Trick;

public class TrickEntry
{
	private Trick trick = null;
	private Map<Key, TrickEntry> trickMap = null;
	
	public TrickEntry() 
	{
		this.trickMap = new HashMap();
	}

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
		return trickMap;
	}
}
