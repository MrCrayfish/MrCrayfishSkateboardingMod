package com.mrcrayfish.skateboarding.client;

import net.minecraft.client.settings.KeyBinding;

import com.mrcrayfish.skateboarding.api.Trick;

public class Combination {
	
	private Trick trick;
	private KeyBinding[] keys = null;
	
	public Combination(Trick trick, KeyBinding ... keys)
	{
		this.trick = trick;
		this.keys = keys;
	}
	
	public boolean allPressed()
	{
		int i = 0;
		for(KeyBinding key : keys)
		{
			if(key.isKeyDown())
			{
				i++;
			}
		}
		return i == keys.length;
	}
	
	public Trick getTrick()
	{
		return trick;
	}
}
