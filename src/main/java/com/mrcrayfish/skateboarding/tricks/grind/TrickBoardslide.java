package com.mrcrayfish.skateboarding.tricks.grind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;

import com.mrcrayfish.skateboarding.api.Difficulty;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.util.TrickHelper;
import com.mrcrayfish.skateboarding.util.TrickHelper.Axis;

public class TrickBoardslide implements Grind
{

	@Override
	public String getName()
	{
		return "Boardslide";
	}

	@Override
	public void updateBoard(EntitySkateboard skateboard)
	{
		skateboard.boardRotationY = -90D;
	}
	
	@Override
	public double[] offsetBoardPosition(EntitySkateboard skateboard)
	{
		return new double[] { 0, 0, 0 };
	}

	@Override
	public void updatePlayer(EntitySkateboard skateboard)
	{
		Minecraft.getMinecraft().thePlayer.prevRenderYawOffset -= 90F;
		Minecraft.getMinecraft().thePlayer.renderYawOffset -= 90F;
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
