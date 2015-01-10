package com.mrcrayfish.skateboarding.tricks.grind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;

import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class TrickBoardslide implements Grind
{

	@Override
	public String getName()
	{
		return "Boardslide";
	}

	@Override
	public void updateBoard(ModelRenderer skateboard, int tick)
	{
		skateboard.rotateAngleY = (float) Math.toRadians(90);
	}

	@Override
	public void updatePlayer(ModelPlayer player, EntitySkateboard skateboard)
	{
		Minecraft.getMinecraft().thePlayer.renderYawOffset = skateboard.rotationYaw;
	}

	@Override
	public void onStart(EntitySkateboard skateboard)
	{

	}

	@Override
	public void onEnd(EntitySkateboard skateboard)
	{

	}

}
