package com.mrcrayfish.skateboarding.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import api.player.model.ModelPlayerAPI;
import api.player.model.ModelPlayerBase;

import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.api.trick.Trick;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class ModelPlayerOverride extends ModelPlayerBase
{
	public ModelPlayerOverride(ModelPlayerAPI modelPlayerAPI)
	{
		super(modelPlayerAPI);
	}

	@Override
	public void afterSetRotationAngles(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, net.minecraft.entity.Entity paramEntity)
	{
		if (paramEntity.getRidingEntity() instanceof EntitySkateboard)
		{
			EntitySkateboard skateboard = (EntitySkateboard) paramEntity.getRidingEntity();
			if (!skateboard.isGoofy())
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

				this.modelPlayer.bipedLeftArm.rotateAngleX = -1F;
				this.modelPlayer.bipedLeftArm.rotateAngleY = 1.3F;
				this.modelPlayer.bipedLeftArm.rotateAngleZ = -1F;

				this.modelPlayer.bipedLeftArm.rotationPointZ = -5F;
				this.modelPlayer.bipedLeftArm.rotationPointY = 2F;
				this.modelPlayer.bipedLeftArm.rotationPointX = 1.3F;

				this.modelPlayer.bipedRightArm.rotateAngleX = 1F;
				this.modelPlayer.bipedRightArm.rotateAngleY = 1.3F;
				this.modelPlayer.bipedRightArm.rotateAngleZ = 1F;

				this.modelPlayer.bipedRightArm.rotationPointZ = 5F;
				this.modelPlayer.bipedRightArm.rotationPointY = 2F;
				this.modelPlayer.bipedRightArm.rotationPointX = -1.3F;
			}
			else
			{
				this.modelPlayer.bipedLeftLeg.rotateAngleX = 1F;
				this.modelPlayer.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(106);
				this.modelPlayer.bipedLeftLeg.rotateAngleZ = 1F;

				this.modelPlayer.bipedLeftLeg.rotationPointZ = -2.0F;
				this.modelPlayer.bipedLeftLeg.rotationPointX = -0.5F;

				this.modelPlayer.bipedRightLeg.rotateAngleX = -1F;
				this.modelPlayer.bipedRightLeg.rotateAngleY = (float) Math.toRadians(106);
				this.modelPlayer.bipedRightLeg.rotateAngleZ = -1F;

				this.modelPlayer.bipedRightLeg.rotationPointZ = 2.0F;
				this.modelPlayer.bipedRightLeg.rotationPointX = 0.5F;

				this.modelPlayer.bipedBody.rotateAngleY = (float) Math.toRadians(109);

				this.modelPlayer.bipedLeftArm.rotateAngleX = 1F;
				this.modelPlayer.bipedLeftArm.rotateAngleY = (float) Math.toRadians(106);
				this.modelPlayer.bipedLeftArm.rotateAngleZ = 1F;

				this.modelPlayer.bipedLeftArm.rotationPointZ = -5F;
				this.modelPlayer.bipedLeftArm.rotationPointY = 2F;
				this.modelPlayer.bipedLeftArm.rotationPointX = -1.3F;

				this.modelPlayer.bipedRightArm.rotateAngleX = -1F;
				this.modelPlayer.bipedRightArm.rotateAngleY = (float) Math.toRadians(106);
				this.modelPlayer.bipedRightArm.rotateAngleZ = -1F;

				this.modelPlayer.bipedRightArm.rotationPointZ = 5F;
				this.modelPlayer.bipedRightArm.rotationPointY = 2F;
				this.modelPlayer.bipedRightArm.rotationPointX = 1.3F;
			}

			
		}
		else
		{
			this.modelPlayer.bipedLeftLeg.rotateAngleZ = 0F;
			this.modelPlayer.bipedRightLeg.rotateAngleZ = 0F;
			this.modelPlayer.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
			this.modelPlayer.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		}
	}
	
	@Override
	public void beforeRender(Entity paramEntity, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) 
	{
				
	}
	
	@Override
	public void render(Entity paramEntity, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
		float rotation = 0F;
		EntityPlayer player = (EntityPlayer) paramEntity;
		if (paramEntity.getRidingEntity() instanceof EntitySkateboard)
		{
			EntitySkateboard skateboard = (EntitySkateboard) paramEntity.getRidingEntity();
			if (skateboard.isInTrick() && skateboard.getCurrentTrick() != null)
			{
				Trick trick = skateboard.getCurrentTrick();
				if (trick instanceof Grind)
				{
					Grind grind = (Grind) trick;
					double[] offset = grind.getBoardOffsetPosition(skateboard);
					GlStateManager.translate(-offset[0], -offset[1], offset[2]);
					rotation = grind.getBodyRotation(skateboard);
					GlStateManager.rotate(rotation, 0, 1, 0);
					rotation -= grind.getHeadRotation(skateboard);
				}
			}
		}
		super.render(paramEntity, paramFloat1, paramFloat2, paramFloat3, -rotation, paramFloat5, paramFloat6);
	}
}
