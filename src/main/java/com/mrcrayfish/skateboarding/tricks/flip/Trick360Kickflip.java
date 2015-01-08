package com.mrcrayfish.skateboarding.tricks.flip;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

import com.mrcrayfish.skateboarding.api.trick.Flip;

public class Trick360Kickflip implements Flip
{
	@Override
	public void updateMovement(ModelRenderer skateboard, int tick)
	{
		skateboard.rotateAngleZ = (float) Math.toRadians(-360F / performTime() * tick);
		GlStateManager.rotate((360F / performTime()) * tick, 0, 1, 0);
	}

	@Override
	public String getName()
	{
		return "360 Flip";
	}

	@Override
	public int performTime()
	{
		return 10;
	}

}
