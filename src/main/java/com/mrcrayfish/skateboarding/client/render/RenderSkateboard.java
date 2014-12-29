package com.mrcrayfish.skateboarding.client.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import com.mrcrayfish.skateboarding.client.model.ModelSkateboard;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class RenderSkateboard extends Render {
	
	private static final ResourceLocation minecartTextures = new ResourceLocation("textures/entity/minecart.png");
	private ModelBase modelSkateboard = new ModelSkateboard(); 

	public RenderSkateboard(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.5F;
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        this.doRender((EntitySkateboard)entity, x, y, z, p_76986_8_, partialTicks);
    }

	public void doRender(EntitySkateboard entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        this.bindEntityTexture(entity);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        this.modelSkateboard.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return minecartTextures;
	}

}
