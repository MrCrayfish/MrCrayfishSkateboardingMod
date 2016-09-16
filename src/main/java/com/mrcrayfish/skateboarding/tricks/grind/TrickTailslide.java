package com.mrcrayfish.skateboarding.tricks.grind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

import com.mrcrayfish.skateboarding.api.Difficulty;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.util.GrindHelper;

public class TrickTailslide implements Grind
{
	@Override
	public String getName(int rotation)
	{
		return "Tailslide";
	}

	@Override
	public void updateBoard(EntitySkateboard skateboard)
	{
		skateboard.boardRotationY = 90F;
	}

	@Override
	public double[] getBoardOffsetPosition(EntitySkateboard skateboard)
	{
		return new double[] { 0.49, 0.0, 0.0 };
	}
	
	@Override
	public float getHeadRotation(EntitySkateboard skateboard) 
	{
		return 35F;
	}

	@Override
	public float getBodyRotation(EntitySkateboard skateboard)
	{
		return 90F;
	}

	@Override
	public void onStart(EntitySkateboard skateboard)
	{

	}

	@Override
	public void onEnd(EntitySkateboard skateboard)
	{

	}

	@Override
	public double points()
	{
		return 2;
	}

	@Override
	public Difficulty difficulty()
	{
		return Difficulty.MEDIUM;
	}
}
