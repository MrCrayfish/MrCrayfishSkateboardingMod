package com.mrcrayfish.skateboarding.tricks;

import net.minecraft.client.model.ModelRenderer;

public class TrickHeelflip extends Trick
{
	@Override
	public void updateMovement(ModelRenderer skateboard, int tick)
	{
		
	}

	@Override
	public String getName()
	{
		return "Heelflip";
	}

	@Override
	public int performTime()
	{
		return 20;
	}

}
