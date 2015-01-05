package com.mrcrayfish.skateboarding.api.map;

import java.util.HashMap;
import java.util.Map;

import com.mrcrayfish.skateboarding.api.Trick;

public class TrickMap {

	public static Map<Key, TrickEntry> trickMap = new HashMap<Key, TrickEntry>();

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
		Map<Key, TrickEntry> prevMap = trickMap;
		for (int i = 0; i < combo.length; i++) {
			if (prevMap.get(combo[i]) != null) {
				if (i == combo.length - 1) {
					return prevMap.get(combo[i]).getTrick();
				} else {
					System.out.println("Getting Trick Map for Key: " + combo[i].name());
					prevMap = prevMap.get(combo[i]).getTrickMap();
				}
			}
		}
		return null;
	}

	static int spacing = 0;

	public static void printTrickMap(Map<Key, TrickEntry> map) {
		for (Key key : map.keySet()) {
			System.out.println(getSpacing() + key.name());
			if (map.get(key).getTrick() != null) {
				System.out.println(getSpacing() + ":" + map.get(key).getTrick());
			}
			spacing++;
			printTrickMap(map.get(key).getTrickMap());
			spacing--;
		}
	}

	public static String getSpacing() {
		String result = "";
		for (int i = 0; i < spacing; i++) {
			result += "    ";
		}
		return result;
	}

	public static enum Key {
		UP, DOWN, LEFT, RIGHT;
	}
}
