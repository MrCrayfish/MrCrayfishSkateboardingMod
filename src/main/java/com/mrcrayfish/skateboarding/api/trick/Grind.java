package com.mrcrayfish.skateboarding.api.trick;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;

public abstract class Grind extends Trick
{
	public abstract float getHeadRotation(EntitySkateboard skateboard);
	
	public abstract float getBodyRotation(EntitySkateboard skateboard);
	
	public abstract double[] getBoardOffsetPosition(EntitySkateboard skateboard);
}
