package com.mrcrayfish.skateboarding.tricks.flip;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

import com.mrcrayfish.skateboarding.api.trick.Flip;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.util.TrickHelper;
import com.mrcrayfish.skateboarding.util.TrickHelper.Axis;

public class TrickVarialHeelflip implements Flip
{
	@Override
	public void updateMovement(EntitySkateboard skateboard, ModelRenderer boardModel)
	{
		TrickHelper.flipBoard(skateboard, boardModel, -180F, performTime(), Axis.Z);
		TrickHelper.spinBoard(skateboard, boardModel, 360F, performTime());
	}

	@Override
	public String getName()
	{
		return "Varial Heelflip";
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
