package com.mrcrayfish.skateboarding.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import com.mrcrayfish.skateboarding.api.trick.Trick;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class ComboBuilder
{
	private List<String> performedTricks = new ArrayList<String>();
	private double points;
	private int comboTimer;
	private int coolDown = 20;
	private int animation = 0;
	private boolean inCombo;
	private boolean recentlyAdded;

	public void addTrick(Trick trick, double rotation)
	{
		if (!inCombo)
		{
			reset();
			inCombo = true;
		}

		int count = getTrickCount(trick);
		int multiplier = (int) (rotation / 180) + 1;
		addPoints(decrease(trick.points(), count, 80) * multiplier);
		addTime((int) decrease(trick.difficulty().getExtraTime(), count, 50));

		String pre = "";
		rotation += 10;
		int rotCount = (int) ((rotation) / 180);
		System.out.println(rotation);
		System.out.println(rotCount);
		if (rotCount > 0)
		{
			pre += rotCount * 180 + " ";
		}
		performedTricks.add(pre + trick.getName());

		recentlyAdded = true;
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

	public void addPoints(double amount)
	{
		points += amount;
	}

	public void addTime(int amount)
	{
		comboTimer += amount;
		if (comboTimer > 500)
		{
			comboTimer = 500;
		}
	}

	public int getTime()
	{
		return comboTimer;
	}

	public void update(EntitySkateboard skateboard)
	{
		if (!skateboard.isInTrick())
		{
			if (!inCombo && performedTricks.size() > 0)
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

		if (recentlyAdded)
		{
			animation++;
			if (animation > 10)
			{
				animation = 0;
				recentlyAdded = false;
			}
		}
	}

	public String[] getTricks()
	{
		return performedTricks.toArray(new String[0]);
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

	public boolean hasRecentlyAdded()
	{
		return recentlyAdded;
	}

	public void setRecentlyAdded(boolean recentlyAdded)
	{
		this.recentlyAdded = recentlyAdded;
	}

	public int getAnimation()
	{
		return animation;
	}

	private void reset()
	{
		performedTricks.clear();
		coolDown = 20;
		points = 0;
	}
}
