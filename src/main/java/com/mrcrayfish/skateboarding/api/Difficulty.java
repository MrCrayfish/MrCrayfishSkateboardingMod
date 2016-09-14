package com.mrcrayfish.skateboarding.api;

public enum Difficulty {
	
	EASY(6, 50), MEDIUM(8, 70), HARD(10, 100), IMPOSSIBLE(12, 150);

	private int performTime;
	private int extraComboTime;

	Difficulty(int performTime, int extraComboTime) {
		this.performTime = performTime;
		this.extraComboTime = extraComboTime;
	}
	
	public int getPerformTime() {
		return performTime;
	}

	public int getExtraTime() {
		return extraComboTime;
	}
}
