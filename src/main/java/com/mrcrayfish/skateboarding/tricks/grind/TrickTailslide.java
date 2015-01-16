package com.mrcrayfish.skateboarding.tricks.grind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;

import com.mrcrayfish.skateboarding.api.Difficulty;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.util.GrindHelper;

public class TrickTailslide implements Grind
{
	@Override
	public String getName()
	{
		return "Tailslide";
	}

	@Override
	public void updateBoard(EntitySkateboard skateboard, ModelRenderer boardModel)
	{
		boardModel.rotateAngleY = (float) Math.toRadians(90);
	}

	@Override
	public double[] offsetBoardPosition(EntitySkateboard skateboard)
	{
		return GrindHelper.setOffset(skateboard, 0.0, 0.0, 0.49);
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
