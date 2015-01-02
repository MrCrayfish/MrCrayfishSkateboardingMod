package com.mrcrayfish.skateboarding.tricks;

import net.minecraft.client.model.ModelRenderer;

public abstract class Trick
{
	public static final Trick kickflip = new TrickKickflip();
	public static final Trick heelflip = new TrickKickflip();
	public static final Trick popshove = new TrickKickflip();
	public static final Trick treflip = new TrickTreflip();
	
	public abstract String getName();

	public abstract int performTime();

	public abstract void updateMovement(ModelRenderer skateboard, int tick);
}
