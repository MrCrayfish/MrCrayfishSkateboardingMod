package com.mrcrayfish.skateboarding.tricks;

import com.mrcrayfish.skateboarding.api.Trick;

import net.minecraft.client.model.ModelRenderer;

public class TrickTreflip implements Trick
{
	@Override
	public void updateMovement(ModelRenderer skateboard, int tick)
	{	
		if (tick < performTime())
		{
			skateboard.rotateAngleX = (float) Math.toRadians(-(360F /performTime()) * (tick <= 6 ? tick : 12 - tick));
			skateboard.rotateAngleY = (float) Math.toRadians(-(360F / performTime()) * (tick <= 2 ? -1 : (tick <= 3 ? 0 : (tick <= 5 ? 1 : (tick <= 6 ? 0 : (tick <= 8 ? -1 : (tick <= 9 ? 0 : 1)))))));
			skateboard.rotateAngleZ = (float) Math.toRadians(-(360F /performTime()) * (tick <= 3 ? tick : (tick <= 9 ? 6 - tick : -12 + tick)));
		} 
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

}
