package com.mrcrayfish.skateboarding.client.model;

import api.player.model.ModelPlayerAPI;
import api.player.model.ModelPlayerBase;

public class ModelPlayerOverride extends ModelPlayerBase
{

	public ModelPlayerOverride(ModelPlayerAPI modelPlayerAPI)
	{
		super(modelPlayerAPI);
	}
	
	@Override
	public void afterSetRotationAngles(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, net.minecraft.entity.Entity paramEntity)
	{
		this.modelPlayer.bipedLeftLeg.rotateAngleX = 0;
		this.modelPlayer.bipedLeftLeg.rotateAngleY = 0;
		this.modelPlayer.bipedLeftLeg.rotateAngleZ = 0;
	}

}
