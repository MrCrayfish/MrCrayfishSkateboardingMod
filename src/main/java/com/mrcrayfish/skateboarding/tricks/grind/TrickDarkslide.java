package com.mrcrayfish.skateboarding.tricks.grind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

import com.mrcrayfish.skateboarding.api.Difficulty;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.util.TrickHelper;
import com.mrcrayfish.skateboarding.util.TrickHelper.Axis;

public class TrickDarkslide implements Grind
{
	@Override
	public String getName(int rotation)
	{
		return "Darkslide";
	}

	@Override
	public void updateBoard(EntitySkateboard skateboard)
	{
		skateboard.boardRotationY = -90F;
		skateboard.boardRotationZ = 180F;
	}
	
	@Override
	public double[] getBoardOffsetPosition(EntitySkateboard skateboard)
	{
		return new double[] { 0, -0.18, 0 };
	}
	
	@Override
	public float getHeadRotation(EntitySkateboard skateboard) 
	{
		return 0F;
	}

	@Override
	public float getBodyRotation(EntitySkateboard skateboard)
	{
		return -90F;
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
		return 1;
	}
	
	@Override
	public Difficulty difficulty()
	{
		return Difficulty.EASY;
	}
}
