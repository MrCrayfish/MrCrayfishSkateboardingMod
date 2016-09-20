package com.mrcrayfish.skateboarding.api.trick;

import net.minecraft.client.model.ModelRenderer;

import com.mrcrayfish.skateboarding.api.Difficulty;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public abstract class Trick
{
	public abstract String getName(int rotation);
	
	public boolean hasMultipleNames() 
	{
		return false;
	}
	
	public abstract void onStart(EntitySkateboard skateboard);
	
	public abstract void onEnd(EntitySkateboard skateboard);
	
	public abstract void updateBoard(EntitySkateboard skateboard);
	
	public abstract double points();
	
	public abstract Difficulty difficulty();
}
