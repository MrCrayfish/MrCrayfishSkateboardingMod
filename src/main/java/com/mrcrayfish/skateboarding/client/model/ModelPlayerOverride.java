package com.mrcrayfish.skateboarding.client.model;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
		if (paramEntity.ridingEntity != null && paramEntity.ridingEntity instanceof EntitySkateboard)
		{
			this.modelPlayer.bipedLeftLeg.rotateAngleX = -1F;
			this.modelPlayer.bipedLeftLeg.rotateAngleY = 1.3F;
			this.modelPlayer.bipedLeftLeg.rotateAngleZ = -1F;

			this.modelPlayer.bipedLeftLeg.rotationPointZ = -2.0F;
			this.modelPlayer.bipedLeftLeg.rotationPointX = 0.5F;

			this.modelPlayer.bipedRightLeg.rotateAngleX = 1F;
			this.modelPlayer.bipedRightLeg.rotateAngleY = 1.3F;
			this.modelPlayer.bipedRightLeg.rotateAngleZ = 1F;

			this.modelPlayer.bipedRightLeg.rotationPointZ = 2.0F;
			this.modelPlayer.bipedRightLeg.rotationPointX = -0.5F;

			this.modelPlayer.bipedBody.rotateAngleY = 1.25F;

			this.modelPlayer.bipedLeftArm.rotateAngleZ = 5.0F;
			this.modelPlayer.bipedRightArm.rotateAngleZ = 2F;
			
			//player.rotationYaw = this.modelBiped.bipedBody.rotateAngleY;
		}
		else
		{
			this.modelPlayer.bipedLeftLeg.rotateAngleZ = 0F;
			this.modelPlayer.bipedRightLeg.rotateAngleZ = 0F;
			this.modelPlayer.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
			this.modelPlayer.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		}
	}
}
