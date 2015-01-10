package com.mrcrayfish.skateboarding.tricks.flip;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

import com.mrcrayfish.skateboarding.api.trick.Flip;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class TrickPopShove implements Flip
{
	@Override
	public void updateMovement(ModelRenderer skateboard, int tick)
	{
		GlStateManager.rotate((180F / performTime()) * tick, 0, 1, 0);
	}

	@Override
	public String getName()
	{
		return "Pop Shove-it";
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

}
