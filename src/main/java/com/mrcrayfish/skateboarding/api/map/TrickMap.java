package com.mrcrayfish.skateboarding.api.map;

import java.util.HashMap;
import java.util.Map;

import com.mrcrayfish.skateboarding.api.Trick;

public class TrickMap {

	private static Map<Key, TrickEntry> trickMap = new HashMap<Key, TrickEntry>();

	public static void addCombo(Trick trick, Key... combo) {
		Map<Key, TrickEntry> prevMap = trickMap;
		for (int i = 0; i < combo.length; i++) {
			if (!prevMap.containsKey(combo[i])) {
				if (i == combo.length - 1) {
					prevMap.put(combo[i], new TrickEntry().setTrick(trick));
				} else {
					Map<Key, TrickEntry> newMap = new HashMap<Key, TrickEntry>();
					prevMap.put(combo[i], new TrickEntry());
					prevMap = newMap;
				}
			} else {
				if (i == combo.length - 1) {
					if (prevMap.get(combo[i]).getTrick() == null) {
						prevMap.get(combo[i]).setTrick(trick);
					}
				} else {
					prevMap = prevMap.get(combo[i]).getTrickMap();
				}
			}
		}

	}

	public static Trick getTrick(Key... combo) {
		if (combo.length == 1) {
			TrickEntry entry = trickMap.get(combo[0]);
			return entry.getTrick();
		} else {
			Map<Key, TrickEntry> prevMap = trickMap;
			for(int i = 0; i < combo.length; i++)
			{
				if(i == combo.length - 1)
				{
					return prevMap.get(combo[i]).getTrick();
				} else {
					prevMap = prevMap.get(combo[i]).getTrickMap();
				}
			}
		}
		return null;
	}

	public static enum Key {
		UP, DOWN, LEFT, RIGHT;
	}
}
