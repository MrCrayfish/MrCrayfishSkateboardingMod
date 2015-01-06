package com.mrcrayfish.skateboarding.tricks;

import net.minecraft.client.model.ModelRenderer;

import com.mrcrayfish.skateboarding.api.trick.Flip;

public class TrickHeelflip implements Flip
{
	@Override
	public void updateMovement(ModelRenderer skateboard, int tick)
	{
		if (tick <= performTime())
		{
			skateboard.rotateAngleZ = (float) Math.toRadians((360F / (float) performTime()) * (float) tick);
		}
	}

	@Override
	public String getName()
	{
		return "Heelflip";
	}

	@Override
	public int performTime()
	{
		return 8;
	}

}
