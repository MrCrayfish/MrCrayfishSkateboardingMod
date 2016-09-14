package com.mrcrayfish.skateboarding.api.trick;

import net.minecraft.client.model.ModelRenderer;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public abstract class Flip implements Trick 
{
	public final int performTime() 
	{
		return difficulty().getPerformTime();
	}
}