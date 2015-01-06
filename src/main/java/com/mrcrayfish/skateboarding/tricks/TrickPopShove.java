package com.mrcrayfish.skateboarding.tricks;

import net.minecraft.client.model.ModelRenderer;

import com.mrcrayfish.skateboarding.api.trick.Flip;

public class TrickPopShove implements Flip
{
	@Override
	public void updateMovement(ModelRenderer skateboard, int tick)
	{
		if (tick <= performTime())
		{
			skateboard.rotateAngleY = (float) Math.toRadians((180F / performTime()) * tick);
		}
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

}
