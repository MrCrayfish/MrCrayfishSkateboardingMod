package com.mrcrayfish.skateboarding.api.trick;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;

public interface Grind extends Trick
{
	public void updatePlayer(ModelPlayer player, EntitySkateboard skateboard);
	
	public double[] offsetBoardPosition(EntitySkateboard skateboard);
}
