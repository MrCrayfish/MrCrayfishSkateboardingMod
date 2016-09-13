package com.mrcrayfish.skateboarding.tricks.flip;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

import com.mrcrayfish.skateboarding.api.Difficulty;
import com.mrcrayfish.skateboarding.api.trick.Flip;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.util.TrickHelper;
import com.mrcrayfish.skateboarding.util.TrickHelper.Axis;

public class TrickVarialHeelflip implements Flip
{
	@Override
	public void updateBoard(EntitySkateboard skateboard)
	{
		TrickHelper.flipBoard(skateboard, -360F, performTime(), Axis.Z);
		TrickHelper.spinBoard(skateboard, -180F, performTime());
	}

	@Override
	public String getName(int rotation)
	{
		return "Varial Heelflip";
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
		skateboard.setFlipped();
	}

	@Override
	public double points()
	{
		return 60;
	}
	
	@Override
	public Difficulty difficulty()
	{
		return Difficulty.MEDIUM;
	}
}
