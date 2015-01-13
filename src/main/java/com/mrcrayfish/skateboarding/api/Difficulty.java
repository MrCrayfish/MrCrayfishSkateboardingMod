package com.mrcrayfish.skateboarding.api;

public enum Difficulty
{
	EASY(50), MEDIUM(70), HARD(100), IMPOSSIBLE(150);

	public int extraComboTime;

	Difficulty(int extraComboTime)
	{
		this.extraComboTime = extraComboTime;
	}

	public int getExtraTime()
	{
		return extraComboTime;
	}
}
