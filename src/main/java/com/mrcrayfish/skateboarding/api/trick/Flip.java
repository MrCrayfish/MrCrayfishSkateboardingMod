package com.mrcrayfish.skateboarding.api.trick;

import net.minecraft.client.model.ModelRenderer;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public abstract class Flip extends Trick 
{
	public int performTime() 
	{
		return difficulty().getPerformTime();
	}
}