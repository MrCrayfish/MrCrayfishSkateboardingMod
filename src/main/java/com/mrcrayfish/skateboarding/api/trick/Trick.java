package com.mrcrayfish.skateboarding.api.trick;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public interface Trick
{
	public String getName();
	
	public void onStart(EntitySkateboard skateboard);
	
	public void onEnd(EntitySkateboard skateboard);
	
	public double points();
}
