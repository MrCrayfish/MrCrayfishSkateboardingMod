package com.mrcrayfish.skateboarding.util;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class TrickHelper
{
	public static void spinBoard(EntitySkateboard skateboard, ModelRenderer board, float totalSpin, int performTime)
	{
		totalSpin = skateboard.isGoofy() ? -totalSpin : totalSpin;
		GlStateManager.rotate((totalSpin / performTime) * skateboard.getInTrickTimer(), 0, 1, 0);
	}

	public static void flipBoard(EntitySkateboard skateboard, ModelRenderer board, float totalSpin, int performTime, Axis axis)
	{
		totalSpin = skateboard.isGoofy() ? totalSpin : -totalSpin;
		switch (axis)
		{
		case X:
			board.rotateAngleX = (float) Math.toRadians((totalSpin / performTime) * skateboard.getInTrickTimer());
			break;
		case Y:
			board.rotateAngleY = (float) Math.toRadians((totalSpin / performTime) * skateboard.getInTrickTimer());
			break;
		case Z:
			board.rotateAngleZ = (float) Math.toRadians((totalSpin / performTime) * skateboard.getInTrickTimer());
			break;
		}
	}

	public static enum Axis
	{
		X, Y, Z;
	}
}
