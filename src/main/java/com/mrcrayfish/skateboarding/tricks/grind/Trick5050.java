package com.mrcrayfish.skateboarding.tricks.grind;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;

import com.mrcrayfish.skateboarding.api.Difficulty;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class Trick5050 extends Grind
{
	
	@Override
	public String getName(int rotation)
	{
		return "5050";
	}

	@Override
	public void updateBoard(EntitySkateboard skateboard)
	{
		
	}
	
	@Override
	public double[] getBoardOffsetPosition(EntitySkateboard skateboard)
	{
		return new double[] { 0, -0.02, 0 };
	}
	
	@Override
	public float getHeadRotation(EntitySkateboard skateboard) 
	{
		return 0F;
	}

	@Override
	public float getBodyRotation(EntitySkateboard skateboard)
	{
		return 0F;
	}

	@Override
	public void onStart(EntitySkateboard skateboard)
	{
		
	}

	@Override
	public void onEnd(EntitySkateboard skateboard)
	{
		
	}
	
	@Override
	public double points()
	{
		return 1;
	}
	
	@Override
	public Difficulty difficulty()
	{
		return Difficulty.EASY;
	}
}
