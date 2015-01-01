package com.mrcrayfish.skateboarding.tricks;

import net.minecraft.client.model.ModelRenderer;

public class TrickKickflip extends Trick
{
	@Override
	public void updateMovement(ModelRenderer skateboard, int tick)
	{
		if (tick <= performTime())
		{
			System.out.println(tick);
			skateboard.rotateAngleZ = -24F * tick;
		}
		else
		{
			skateboard.rotateAngleZ = 0;
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
		return 10;
	}	
}
