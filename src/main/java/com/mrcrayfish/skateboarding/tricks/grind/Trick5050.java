package com.mrcrayfish.skateboarding.tricks.grind;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;

import com.mrcrayfish.skateboarding.api.Difficulty;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class Trick5050 implements Grind
{
	
	@Override
	public String getName(int rotation)
	{
		return "5050";
	}

	@Override
	public void updateBoard(EntitySkateboard skateboardl)
	{
		
	}
	
	@Override
	public double[] offsetBoardPosition(EntitySkateboard skateboard)
	{
		return new double[] { 0, 0, 0 };
	}

	@Override
	public void updatePlayer(EntitySkateboard skateboard)
	{
		
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
