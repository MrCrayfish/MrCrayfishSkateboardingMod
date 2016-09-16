package com.mrcrayfish.skateboarding.api.trick;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;

public interface Grind extends Trick
{
	public float getHeadRotation(EntitySkateboard skateboard);
	
	public float getBodyRotation(EntitySkateboard skateboard);
	
	public double[] getBoardOffsetPosition(EntitySkateboard skateboard);
}
