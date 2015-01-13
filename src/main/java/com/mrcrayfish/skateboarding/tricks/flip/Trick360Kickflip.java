package com.mrcrayfish.skateboarding.tricks.flip;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

import com.mrcrayfish.skateboarding.api.Difficulty;
import com.mrcrayfish.skateboarding.api.trick.Flip;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.util.TrickHelper;
import com.mrcrayfish.skateboarding.util.TrickHelper.Axis;

public class Trick360Kickflip implements Flip
{
	@Override
	public void updateMovement(EntitySkateboard skateboard, ModelRenderer boardModel)
	{
		TrickHelper.flipBoard(skateboard, boardModel, 360F, performTime(), Axis.Z);
		TrickHelper.spinBoard(skateboard, boardModel, 360F, performTime());
	}

	@Override
	public String getName()
	{
		return "360 Flip";
	}

	@Override
	public int performTime()
	{
		return 12;
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
		return 80;
	}
	
	@Override
	public Difficulty difficulty()
	{
		return Difficulty.HARD;
	}
}
