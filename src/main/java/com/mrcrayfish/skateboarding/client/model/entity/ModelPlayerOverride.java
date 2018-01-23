package com.mrcrayfish.skateboarding.client.model.entity;

import api.player.model.ModelPlayerAPI;
import api.player.model.ModelPlayerBase;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.api.trick.Trick;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelPlayerOverride extends ModelPlayerBase
{
	ModelBiped INSTANCE;
	public ModelPlayerOverride(ModelPlayerAPI modelPlayerAPI)
	{
		super(modelPlayerAPI);
		if (this.modelPlayer instanceof ModelBiped)
			this.INSTANCE = this.modelPlayer;
		else
			this.INSTANCE = this.modelBiped;
	}

	@Override
	public void afterSetRotationAngles(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, net.minecraft.entity.Entity paramEntity)
	{
		if (paramEntity.getRidingEntity() instanceof EntitySkateboard)
		{
			EntitySkateboard skateboard = (EntitySkateboard) paramEntity.getRidingEntity();
			if ((!skateboard.isGoofy() && !skateboard.isSwitch_()) || (skateboard.isGoofy() && skateboard.isSwitch_()))
			{
				this.INSTANCE.bipedLeftLeg.rotateAngleX = -1F;
				this.INSTANCE.bipedLeftLeg.rotateAngleY = 1.3F;
				this.INSTANCE.bipedLeftLeg.rotateAngleZ = -1F;

				this.INSTANCE.bipedLeftLeg.rotationPointZ = -2.0F;
				this.INSTANCE.bipedLeftLeg.rotationPointX = 0.5F;

				this.INSTANCE.bipedRightLeg.rotateAngleX = 1F;
				this.INSTANCE.bipedRightLeg.rotateAngleY = 1.3F;
				this.INSTANCE.bipedRightLeg.rotateAngleZ = 1F;

				this.INSTANCE.bipedRightLeg.rotationPointZ = 2.0F;
				this.INSTANCE.bipedRightLeg.rotationPointX = -0.5F;

				this.INSTANCE.bipedBody.rotateAngleY = 1.25F;

				this.INSTANCE.bipedLeftArm.rotateAngleX = -1F;
				this.INSTANCE.bipedLeftArm.rotateAngleY = 1.3F;
				this.INSTANCE.bipedLeftArm.rotateAngleZ = -1F;

				this.INSTANCE.bipedLeftArm.rotationPointZ = -5F;
				this.INSTANCE.bipedLeftArm.rotationPointY = 2F;
				this.INSTANCE.bipedLeftArm.rotationPointX = 1.3F;

				this.INSTANCE.bipedRightArm.rotateAngleX = 1F;
				this.INSTANCE.bipedRightArm.rotateAngleY = 1.3F;
				this.INSTANCE.bipedRightArm.rotateAngleZ = 1F;

				this.INSTANCE.bipedRightArm.rotationPointZ = 5F;
				this.INSTANCE.bipedRightArm.rotationPointY = 2F;
				this.INSTANCE.bipedRightArm.rotationPointX = -1.3F;
			}
			else
			{
				this.INSTANCE.bipedLeftLeg.rotateAngleX = 1F;
				this.INSTANCE.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(106);
				this.INSTANCE.bipedLeftLeg.rotateAngleZ = 1F;

				this.INSTANCE.bipedLeftLeg.rotationPointZ = -2.0F;
				this.INSTANCE.bipedLeftLeg.rotationPointX = -0.5F;

				this.INSTANCE.bipedRightLeg.rotateAngleX = -1F;
				this.INSTANCE.bipedRightLeg.rotateAngleY = (float) Math.toRadians(106);
				this.INSTANCE.bipedRightLeg.rotateAngleZ = -1F;

				this.INSTANCE.bipedRightLeg.rotationPointZ = 2.0F;
				this.INSTANCE.bipedRightLeg.rotationPointX = 0.5F;

				this.INSTANCE.bipedBody.rotateAngleY = (float) Math.toRadians(-71);

				this.INSTANCE.bipedLeftArm.rotateAngleX = 1F;
				this.INSTANCE.bipedLeftArm.rotateAngleY = (float) Math.toRadians(106);
				this.INSTANCE.bipedLeftArm.rotateAngleZ = 1F;

				this.INSTANCE.bipedLeftArm.rotationPointZ = -5F;
				this.INSTANCE.bipedLeftArm.rotationPointY = 2F;
				this.INSTANCE.bipedLeftArm.rotationPointX = -1.3F;

				this.INSTANCE.bipedRightArm.rotateAngleX = -1F;
				this.INSTANCE.bipedRightArm.rotateAngleY = (float) Math.toRadians(106);
				this.INSTANCE.bipedRightArm.rotateAngleZ = -1F;

				this.INSTANCE.bipedRightArm.rotationPointZ = 5F;
				this.INSTANCE.bipedRightArm.rotationPointY = 2F;
				this.INSTANCE.bipedRightArm.rotationPointX = 1.3F;
			}

			
		}
		else
		{
			this.INSTANCE.bipedLeftLeg.rotateAngleZ = 0F;
			this.INSTANCE.bipedRightLeg.rotateAngleZ = 0F;
			this.INSTANCE.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
			this.INSTANCE.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		}
	}
	
	@Override
	public void beforeRender(Entity paramEntity, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) 
	{
				
	}
	
	@Override
	public void render(Entity paramEntity, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
		
		if (paramEntity.getRidingEntity() instanceof EntitySkateboard)
		{
			EntitySkateboard skateboard = (EntitySkateboard) paramEntity.getRidingEntity();
			if(skateboard.isOnAngledBlock()) 
			{
				GlStateManager.translate(0, -skateboard.getAngledBlock().getYOffset(skateboard.isGrinding()), 0);
			}
			
			if (skateboard.isInTrick() && skateboard.getCurrentTrick() != null)
			{
				Trick trick = skateboard.getCurrentTrick();
				if (trick instanceof Grind)
				{
					float rotation = 0F;
					Grind grind = (Grind) trick;
					double[] offset = grind.getBoardOffsetPosition(skateboard);
					GlStateManager.translate(-offset[0], -offset[1], offset[2]);
					rotation = grind.getBodyRotation(skateboard);
					GlStateManager.rotate(rotation, 0, 1, 0);
					rotation -= grind.getHeadRotation(skateboard);
					super.render(paramEntity, paramFloat1, paramFloat2, paramFloat3, -rotation, paramFloat5, paramFloat6);
					return;
				}
			}
		}
		super.render(paramEntity, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
	}
}