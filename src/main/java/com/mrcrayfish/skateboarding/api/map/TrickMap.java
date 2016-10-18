package com.mrcrayfish.skateboarding.api.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mrcrayfish.skateboarding.api.map.TrickMap.Key;
import com.mrcrayfish.skateboarding.api.trick.Trick;

public class TrickMap
{
	public static Map<Key, TrickEntry> trickMap = new HashMap<Key, TrickEntry>();

	public static void addCombo(Trick trick, Key... keys)
	{
		Map<Key, TrickEntry> prevMap = trickMap;
		for (int i = 0; i < keys.length - 1; i++)
		{
			Key key = keys[i];
			if(!prevMap.containsKey(key)) 
			{
				prevMap.put(key, new TrickEntry());
			}
			prevMap = prevMap.get(key).getTrickMap();
		}
		prevMap.put(keys[keys.length - 1], new TrickEntry().setTrick(trick));
	}

	public static Trick getTrick(Iterator<Key> it)
	{
		Map<Key, TrickEntry> prevMap = trickMap;
		Trick trick = null;
		while (it.hasNext())
		{
			if(prevMap == null) break;
			TrickEntry entry = prevMap.get(it.next());
			if(entry == null) break;
			trick = entry.getTrick();
			prevMap = entry.getTrickMap();
		}
		return trick;
	}

	static int spacing = 0;

	public static void printTrickMap(Map<Key, TrickEntry> map)
	{
		if(map == null) return;
		for (Key key : map.keySet())
		{
			System.out.println(getSpacing() + key.name());
			if (map.get(key).getTrick() != null)
			{
				System.out.println(getSpacing() + ":" + map.get(key).getTrick());
			}
			spacing++;
			printTrickMap(map.get(key).getTrickMap());
			spacing--;
		}
	}

	public static String getSpacing()
	{
		String result = "";
		for (int i = 0; i < spacing; i++)
		{
			result += "    ";
		}
		return result;
	}

	public static enum Key
	{
		UP, DOWN, LEFT, RIGHT;
	}
}
