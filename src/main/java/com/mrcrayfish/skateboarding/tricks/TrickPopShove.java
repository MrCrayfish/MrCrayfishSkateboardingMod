package com.mrcrayfish.skateboarding.tricks;

import com.mrcrayfish.skateboarding.api.Trick;

import net.minecraft.client.model.ModelRenderer;

public class TrickPopShove extends Trick
{
	@Override
	public void updateMovement(ModelRenderer skateboard, int tick)
	{
		if(tick <= performTime())
		{
			skateboard.rotateAngleY = (float) Math.toRadians((180 / performTime()) * tick);
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
