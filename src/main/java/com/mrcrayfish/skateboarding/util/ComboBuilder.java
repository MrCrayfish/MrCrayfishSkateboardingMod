package com.mrcrayfish.skateboarding.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;

import com.mrcrayfish.skateboarding.api.trick.Trick;

public class ComboBuilder
{
	private List<String> performedTricks = new ArrayList<String>();
	private String[] compiledTricks = null;
	private double points;

	public void addTrick(Trick trick)
	{
		points += 10;
		performedTricks.add(trick.getName());
		compileString();
	}

	public void compileString()
	{
		int lines = (int) performedTricks.size() / 4;
		compiledTricks = new String[lines + 1];
		for (int i = 0; i < performedTricks.size(); i++)
		{
			String pre = "";
			if (i == 0)
			{
				pre += performedTricks.get(i);
			}
			else
			{
				pre += " + "+ EnumChatFormatting.AQUA.toString() + performedTricks.get(i);
			}
			if (compiledTricks[i / 4] == null)
			{
				compiledTricks[i / 4] = "";
			}
			compiledTricks[i / 4] += pre;
		}
	}

	public String[] getTricks()
	{
		return compiledTricks;
	}

	public double getPoints()
	{
		return points;
	}

	public void reset()
	{
		compiledTricks = null;
		points = 0;
	}
}
