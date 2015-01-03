package com.mrcrayfish.skateboarding.tricks;

import com.mrcrayfish.skateboarding.api.Trick;
import com.mrcrayfish.skateboarding.api.TrickRegistry;

public class TrickHelper
{
	public static Trick getTrick(Tricks trick)
	{
		switch (trick)
		{
		case KICKFLIP:
			return TrickRegistry.kickflip;
		case TREFLIP:
			return TrickRegistry.treflip;
		default:
			break;
		}
		return TrickRegistry.popshove;
	}

	public static enum Tricks
	{
		OLLIE, KICKFLIP, HEELFLIP, POPSHOVE, TREFLIP;
	}
}
