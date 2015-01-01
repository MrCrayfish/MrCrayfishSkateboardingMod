package com.mrcrayfish.skateboarding.tricks;

public class TrickHelper
{
	public static Trick getTrick(Tricks trick)
	{
		switch (trick)
		{
		case KICKFLIP:
			return Trick.kickflip;
		default:
			break;
		}
		return Trick.popshove;
	}

	public static enum Tricks
	{
		OLLIE, KICKFLIP, HEELFLIP, POPSHOVE, TREFLIP;
	}
}
