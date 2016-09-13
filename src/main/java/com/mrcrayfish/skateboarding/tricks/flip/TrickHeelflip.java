package com.mrcrayfish.skateboarding.tricks.flip;

import net.minecraft.client.model.ModelRenderer;

import com.mrcrayfish.skateboarding.api.Difficulty;
import com.mrcrayfish.skateboarding.api.trick.Flip;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.util.TrickHelper;
import com.mrcrayfish.skateboarding.util.TrickHelper.Axis;

public class TrickHeelflip implements Flip
{
	@Override
	public void updateBoard(EntitySkateboard skateboard)
	{
		TrickHelper.flipBoard(skateboard, -360F, performTime(), Axis.Z);
	}

	@Override
	public String getName(int rotation)
	{
		return "Heelflip";
	}

	@Override
	public int performTime()
	{
		return 8;
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
		return 30;
	}
	
	@Override
	public Difficulty difficulty()
	{
		return Difficulty.EASY;
	}
}
