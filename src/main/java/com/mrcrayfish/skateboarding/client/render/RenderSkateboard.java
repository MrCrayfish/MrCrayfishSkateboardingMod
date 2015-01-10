package com.mrcrayfish.skateboarding.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import com.mrcrayfish.skateboarding.client.model.ModelSkateboard;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class RenderSkateboard extends Render
{

	private static final ResourceLocation minecartTextures = new ResourceLocation("csm:textures/entity/crayfish.png");
	private ModelBase modelSkateboard = new ModelSkateboard();

	public RenderSkateboard(RenderManager renderManager)
	{
		super(renderManager);
		this.shadowSize = 0.0F;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		this.doRender((EntitySkateboard) entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntitySkateboard entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		GlStateManager.pushMatrix();
		if (entity.riddenByEntity != null)
		{
			if (entity.riddenByEntity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity.riddenByEntity;
				if (player.getUniqueID().toString().equals(Minecraft.getMinecraft().thePlayer.getUniqueID().toString()))
				{
					x = 0;
					y = -0.15;
					z = 0;
				}
				if (entity.isInTrick() && !entity.isGrinding())
				{
					y = -0.3;
				}
			}
		}
		GlStateManager.translate(x, y + 1.5F, z);
		GlStateManager.rotate(-entity.rotationYaw, 0, 1, 0);
		GlStateManager.rotate(-90F, 0, 1, 0);
		this.bindEntityTexture(entity);
		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
		this.modelSkateboard.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return minecartTextures;
	}
}
