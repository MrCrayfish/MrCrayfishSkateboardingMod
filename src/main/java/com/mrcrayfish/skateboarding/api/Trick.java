package com.mrcrayfish.skateboarding.api;

import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class Trick
{	
	public abstract String getName();

	public abstract int performTime();

	public abstract void updateMovement(ModelRenderer skateboard, int tick);
}
