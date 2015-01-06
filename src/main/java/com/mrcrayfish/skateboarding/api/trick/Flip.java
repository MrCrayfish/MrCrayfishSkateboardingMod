package com.mrcrayfish.skateboarding.api.trick;

import net.minecraft.client.model.ModelRenderer;

public interface Flip extends Trick
{
	public int performTime();

	public void updateMovement(ModelRenderer skateboard, int inTrickTimer);
}
