package com.mrcrayfish.skateboarding.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.skateboarding.api.trick.Trick;

public class ComboBuilder
{
	private List<String> performedTricks = new ArrayList<String>();
	private String[] compiledTricks = null;

	private double points;

	private int comboTimer;
	private boolean inCombo;
	private int coolDown = 20;

	public void addTrick(Trick trick)
	{
		int count = 1;
		for (String name : performedTricks)
		{
			if (name.equals(trick.getName()))
			{
				count++;
			}
		}
		double points = trick.points();
		if (count > 1)
		{
			if (count > 5)
			{
				count = 5;
			}
			points -= ((points / count) / 100) * 20;
		}
		addPoints(points);

		performedTricks.add(trick.getName());
		compileString();
	}

	private void compileString()
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
				pre += " + " + EnumChatFormatting.AQUA.toString() + performedTricks.get(i);
			}
			if (compiledTricks[i / 4] == null)
			{
				compiledTricks[i / 4] = "";
			}
			compiledTricks[i / 4] += pre;
		}
	}

	public void addPoints(double amount)
	{
		points += amount;
	}

	public void update()
	{
		if (!inCombo)
		{
			if (coolDown > 0)
			{
				coolDown--;
			}
			else
			{
				reset();
			}
		}

		if (inCombo)
		{
			if (comboTimer > 0)
			{
				comboTimer--;
			}
			else
			{
				inCombo = false;
			}
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

	public boolean isInCombo()
	{
		return inCombo;
	}

	public void setInCombo(boolean inCombo)
	{
		this.inCombo = inCombo;
	}

	public void reset()
	{
		performedTricks.clear();
		compiledTricks = null;
		points = 0;
	}
}
