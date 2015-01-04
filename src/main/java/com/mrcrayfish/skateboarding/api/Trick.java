package com.mrcrayfish.skateboarding.api;

import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface Trick
{	
	public String getName();

	public int performTime();

	public void updateMovement(ModelRenderer skateboard, int tick);
}
