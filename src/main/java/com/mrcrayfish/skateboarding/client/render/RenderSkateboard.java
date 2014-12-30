package com.mrcrayfish.skateboarding.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import com.mrcrayfish.skateboarding.client.model.ModelSkateboard;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class RenderSkateboard extends Render
{

	private static final ResourceLocation minecartTextures = new ResourceLocation("textures/entity/minecart.png");
	private ModelBase modelSkateboard = new ModelSkateboard();

	public RenderSkateboard(RenderManager renderManager)
	{
		super(renderManager);
		this.shadowSize = 0.5F;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		this.doRender((EntitySkateboard) entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntitySkateboard entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		this.bindEntityTexture(entity);
		GlStateManager.scale(-1.0F, -1.0F, 1.0F);

		float rotation = entity.rotationYaw;
		if (entity.riddenByEntity != null)
		{
			if (entity.riddenByEntity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity.riddenByEntity;
				rotation = this.interpolateRotation(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);
			}
		}
		GlStateManager.rotate(rotation, 0F, 1F, 0F);
		GlStateManager.rotate(90F, 0, 1, 0);
		this.modelSkateboard.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return minecartTextures;
	}
	
	protected float interpolateRotation(float p_77034_1_, float p_77034_2_, float p_77034_3_)
	{
		float f3;

		for (f3 = p_77034_2_ - p_77034_1_; f3 < -180.0F; f3 += 360.0F)
		{
			;
		}

		while (f3 >= 180.0F)
		{
			f3 -= 360.0F;
		}

		return p_77034_1_ + p_77034_3_ * f3;
	}

}