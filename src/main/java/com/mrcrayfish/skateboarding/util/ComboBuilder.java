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
		if (!inCombo)
		{
			reset();
			inCombo = true;
		}
		
		int count = getTrickCount(trick);
		addPoints(decrease(trick.points(), count, 80));
		addExtraTime((int) decrease(trick.difficulty().getExtraTime(), count, 50));
		
		performedTricks.add(trick.getName());
		compileString();
	}

	private int getTrickCount(Trick trick)
	{
		int count = 0;
		for (String name : performedTricks)
		{
			if (name.equals(trick.getName()))
			{
				count++;
			}
		}
		return count;
	}

	private double decrease(double value, int trickCount, int percent)
	{
		if (trickCount > 0)
		{
			value = ((value / trickCount) / 100) * percent;
		}
		return value;
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
				pre += EnumChatFormatting.BLACK.toString() + " + " + EnumChatFormatting.RESET.toString() + performedTricks.get(i);
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

	public void addExtraTime(int amount)
	{
		comboTimer += amount;
		if (comboTimer > 500)
		{
			comboTimer = 500;
		}
	}

	public void update()
	{
		if (!inCombo && compiledTricks != null)
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
	
	public int getSize()
	{
		return performedTricks.size();
	}

	private void reset()
	{
		performedTricks.clear();
		compiledTricks = null;
		coolDown = 20;
		points = 0;
	}
}
