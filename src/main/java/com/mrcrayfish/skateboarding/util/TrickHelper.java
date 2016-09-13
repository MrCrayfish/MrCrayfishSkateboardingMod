package com.mrcrayfish.skateboarding.util;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class TrickHelper
{
	public static void spinBoard(EntitySkateboard skateboard, float totalSpin, int performTime)
	{
		totalSpin = skateboard.isGoofy() ? -totalSpin : totalSpin;
		skateboard.boardYaw = (totalSpin / performTime) * skateboard.getInTrickTimer();
	}

	public static void flipBoard(EntitySkateboard skateboard, float totalSpin, int performTime, Axis axis)
	{
		totalSpin = skateboard.isGoofy() ? totalSpin : -totalSpin;
		switch (axis)
		{
		case X:
			skateboard.boardRotationX = (totalSpin / performTime) * skateboard.getInTrickTimer(); 
			break;
		case Y:
			skateboard.boardRotationY = (totalSpin / performTime) * skateboard.getInTrickTimer();
			break;
		case Z:
			skateboard.boardRotationZ = (totalSpin / performTime) * skateboard.getInTrickTimer();
			break;
		}
	}

	public static enum Axis
	{
		X, Y, Z;
	}
}
