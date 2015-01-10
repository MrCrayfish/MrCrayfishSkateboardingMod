package com.mrcrayfish.skateboarding.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.skateboarding.api.trick.Flip;
import com.mrcrayfish.skateboarding.api.trick.Grab;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.api.trick.Trick;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

@SideOnly(Side.CLIENT)
public class ModelSkateboard extends ModelBase
{
	ModelRenderer boardBase;
	ModelRenderer boardBack;
	ModelRenderer boardFront;
	ModelRenderer wheelFrontLeft;
	ModelRenderer wheelFrontRight;
	ModelRenderer wheelBackLeft;
	ModelRenderer wheelBackRight;
	ModelRenderer backTruckAxel;
	ModelRenderer frontTruckAxel;
	ModelRenderer backTruckBase;
	ModelRenderer frontTruckBase;

	public ModelSkateboard()
	{

		boardBase = new ModelRenderer(this, 0, 0);
		boardBase.addBox(-3F, 0F, -7F, 6, 1, 14);
		boardBase.setRotationPoint(0F, 0F, 0F);
		boardBase.setTextureSize(64, 32);
		boardBase.mirror = true;
		setRotation(boardBase, 0F, 0F, 0F);
		boardBack = new ModelRenderer(this, 0, 15);
		boardBack.addBox(-3F, 0F, 0F, 6, 1, 3);
		boardBack.setRotationPoint(0F, 0F, 6.75F);
		boardBack.setTextureSize(64, 32);
		boardBack.mirror = true;
		setRotation(boardBack, 0.1745329F, 0F, 0F);
		boardFront = new ModelRenderer(this, 18, 15);
		boardFront.addBox(-3F, 0F, -3F, 6, 1, 3);
		boardFront.setRotationPoint(0F, 0F, -6.8F);
		boardFront.setTextureSize(64, 32);
		boardFront.mirror = true;
		setRotation(boardFront, -0.1745329F, 0F, 0F);
		wheelFrontLeft = new ModelRenderer(this, 40, 0);
		wheelFrontLeft.addBox(0F, -1F, -1F, 1, 2, 2);
		wheelFrontLeft.setRotationPoint(2F, 2F, -5.5F);
		wheelFrontLeft.setTextureSize(64, 32);
		wheelFrontLeft.mirror = true;
		setRotation(wheelFrontLeft, 0F, 0F, 0F);
		wheelFrontRight = new ModelRenderer(this, 46, 0);
		wheelFrontRight.addBox(-1F, -1F, -1F, 1, 2, 2);
		wheelFrontRight.setRotationPoint(-2F, 2F, -5.5F);
		wheelFrontRight.setTextureSize(64, 32);
		wheelFrontRight.mirror = true;
		setRotation(wheelFrontRight, 0F, 0F, 0F);
		wheelBackLeft = new ModelRenderer(this, 40, 4);
		wheelBackLeft.addBox(0F, -1F, -1F, 1, 2, 2);
		wheelBackLeft.setRotationPoint(2F, 2F, 5.5F);
		wheelBackLeft.setTextureSize(64, 32);
		wheelBackLeft.mirror = true;
		setRotation(wheelBackLeft, 0F, 0F, 0F);
		wheelBackRight = new ModelRenderer(this, 46, 4);
		wheelBackRight.addBox(-1F, -1F, -1F, 1, 2, 2);
		wheelBackRight.setRotationPoint(-2F, 2F, 5.5F);
		wheelBackRight.setTextureSize(64, 32);
		wheelBackRight.mirror = true;
		setRotation(wheelBackRight, 0F, 0F, 0F);
		backTruckAxel = new ModelRenderer(this, 0, 19);
		backTruckAxel.addBox(-2F, -0.5F, -0.5F, 4, 1, 1);
		backTruckAxel.setRotationPoint(0F, 2F, 5.5F);
		backTruckAxel.setTextureSize(64, 32);
		backTruckAxel.mirror = true;
		setRotation(backTruckAxel, 0F, 0F, 0F);
		frontTruckAxel = new ModelRenderer(this, 10, 19);
		frontTruckAxel.addBox(-2F, -0.5F, -0.5F, 4, 1, 1);
		frontTruckAxel.setRotationPoint(0F, 2F, -5.5F);
		frontTruckAxel.setTextureSize(64, 32);
		frontTruckAxel.mirror = true;
		setRotation(frontTruckAxel, 0F, 0F, 0F);
		backTruckBase = new ModelRenderer(this, 0, 21);
		backTruckBase.addBox(-1F, 0F, -1F, 2, 1, 2);
		backTruckBase.setRotationPoint(0F, 1F, 5.5F);
		backTruckBase.setTextureSize(64, 32);
		backTruckBase.mirror = true;
		setRotation(backTruckBase, 0F, 0F, 0F);
		frontTruckBase = new ModelRenderer(this, 8, 21);
		frontTruckBase.addBox(-1F, 0F, -1F, 2, 1, 2);
		frontTruckBase.setRotationPoint(0F, 1F, -5.5F);
		frontTruckBase.setTextureSize(64, 32);
		frontTruckBase.mirror = true;
		setRotation(frontTruckBase, 0F, 0F, 0F);

		boardBase.addChild(boardFront);
		boardBase.addChild(boardBack);
		boardBase.addChild(frontTruckBase);
		boardBase.addChild(backTruckBase);
		boardBase.addChild(frontTruckAxel);
		boardBase.addChild(backTruckAxel);
		boardBase.addChild(wheelFrontLeft);
		boardBase.addChild(wheelFrontRight);
		boardBase.addChild(wheelBackLeft);
		boardBase.addChild(wheelBackRight);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		boardBase.rotateAngleY = 0.0F;
		
		EntitySkateboard skateboard = (EntitySkateboard) entity;
		if (skateboard.isInTrick() && skateboard.getCurrentTrick() != null)
		{
			Trick trick = skateboard.getCurrentTrick();
			if (trick instanceof Flip)
			{
				Flip flip = (Flip) trick;
				flip.updateMovement(boardBase, skateboard.getInTrickTimer());
			}
			else if (trick instanceof Grind)
			{
				Grind grind = (Grind) trick;
				grind.updateBoard(boardBase, skateboard.getInTrickTimer());
			}
			else if (trick instanceof Grab)
			{
				Grab grab = (Grab) trick;
			}
		}
		else
		{
			boardBase.rotateAngleX = 0.0F;
			boardBase.rotateAngleZ = 0.0F;
		}
		
		if (skateboard.isFlipped())
		{
			boardBase.rotateAngleY += (float) Math.toRadians(180F);
		}

		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		boardBase.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}