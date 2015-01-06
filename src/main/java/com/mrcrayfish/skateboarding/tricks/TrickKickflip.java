package com.mrcrayfish.skateboarding.tricks;

import net.minecraft.client.model.ModelRenderer;

import com.mrcrayfish.skateboarding.api.Trick;

public class TrickKickflip implements Trick
{
	@Override
	public void updateMovement(ModelRenderer skateboard, int tick)
	{
		if (tick <= performTime())
		{
			skateboard.rotateAngleZ = (float) Math.toRadians(-(360F / performTime()) * tick);
		}
	}

	@Override
	public String getName()
	{
		return "Kickflip";
	}

	@Override
	public int performTime()
	{
		return 8;
	}
}
