package com.mrcrayfish.skateboarding.tricks.flip;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

import com.mrcrayfish.skateboarding.api.Difficulty;
import com.mrcrayfish.skateboarding.api.trick.Flip;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.util.TrickHelper;

public class TrickPopShove extends Flip
{
	@Override
	public String getName(int rotation)
	{
		if(rotation == 180)
		{
			return "Bigspin";
		}
		else if(rotation > 180)
		{
			return rotation + " Pop Shove-it";
		}
		return "Pop Shove-it";
	}
	
	@Override
	public boolean hasMultipleNames() 
	{
		return true;
	}
	
	@Override
	public void updateBoard(EntitySkateboard skateboard)
	{
		TrickHelper.spinBoard(skateboard, 180F, performTime());
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
		return 30;
	}
	
	@Override
	public Difficulty difficulty()
	{
		return Difficulty.EASY;
	}
}
