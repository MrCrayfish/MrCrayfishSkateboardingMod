package com.mrcrayfish.skateboarding.client;

import com.mrcrayfish.obfuscate.client.event.ModelPlayerEvent;
import com.mrcrayfish.skateboarding.api.trick.Grind;
import com.mrcrayfish.skateboarding.api.trick.Trick;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Author: MrCrayfish
 */
public class ClientEvents
{
    @SubscribeEvent
    public void onSetupPlayerAngles(ModelPlayerEvent.SetupAngles.Pre event)
    {
        EntityPlayer player = event.getEntityPlayer();
        Entity entity = player.getRidingEntity();
        if(entity instanceof EntitySkateboard)
        {
            event.setCanceled(true);

            ModelPlayer modelPlayer = event.getModelPlayer();
            EntitySkateboard skateboard = (EntitySkateboard) entity;
            if ((!skateboard.isGoofy() && !skateboard.isSwitch_()) || (skateboard.isGoofy() && skateboard.isSwitch_()))
            {
                modelPlayer.bipedLeftLeg.rotateAngleX = -1F;
                modelPlayer.bipedLeftLeg.rotateAngleY = 1.3F;
                modelPlayer.bipedLeftLeg.rotateAngleZ = -1F;

                modelPlayer.bipedLeftLeg.rotationPointZ = -2.0F;
                modelPlayer.bipedLeftLeg.rotationPointX = 0.5F;

                modelPlayer.bipedRightLeg.rotateAngleX = 1F;
                modelPlayer.bipedRightLeg.rotateAngleY = 1.3F;
                modelPlayer.bipedRightLeg.rotateAngleZ = 1F;

                modelPlayer.bipedRightLeg.rotationPointZ = 2.0F;
                modelPlayer.bipedRightLeg.rotationPointX = -0.5F;

                modelPlayer.bipedBody.rotateAngleY = 1.25F;

                modelPlayer.bipedLeftArm.rotateAngleX = -1F;
                modelPlayer.bipedLeftArm.rotateAngleY = 1.3F;
                modelPlayer.bipedLeftArm.rotateAngleZ = -1F;

                modelPlayer.bipedLeftArm.rotationPointZ = -5F;
                modelPlayer.bipedLeftArm.rotationPointY = 2F;
                modelPlayer.bipedLeftArm.rotationPointX = 1.3F;

                modelPlayer.bipedRightArm.rotateAngleX = 1F;
                modelPlayer.bipedRightArm.rotateAngleY = 1.3F;
                modelPlayer.bipedRightArm.rotateAngleZ = 1F;

                modelPlayer.bipedRightArm.rotationPointZ = 5F;
                modelPlayer.bipedRightArm.rotationPointY = 2F;
                modelPlayer.bipedRightArm.rotationPointX = -1.3F;
            }
            else
            {
                modelPlayer.bipedLeftLeg.rotateAngleX = 1F;
                modelPlayer.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(106);
                modelPlayer.bipedLeftLeg.rotateAngleZ = 1F;

                modelPlayer.bipedLeftLeg.rotationPointZ = -2.0F;
                modelPlayer.bipedLeftLeg.rotationPointX = -0.5F;

                modelPlayer.bipedRightLeg.rotateAngleX = -1F;
                modelPlayer.bipedRightLeg.rotateAngleY = (float) Math.toRadians(106);
                modelPlayer.bipedRightLeg.rotateAngleZ = -1F;

                modelPlayer.bipedRightLeg.rotationPointZ = 2.0F;
                modelPlayer.bipedRightLeg.rotationPointX = 0.5F;

                modelPlayer.bipedBody.rotateAngleY = (float) Math.toRadians(-71);

                modelPlayer.bipedLeftArm.rotateAngleX = 1F;
                modelPlayer.bipedLeftArm.rotateAngleY = (float) Math.toRadians(106);
                modelPlayer.bipedLeftArm.rotateAngleZ = 1F;

                modelPlayer.bipedLeftArm.rotationPointZ = -5F;
                modelPlayer.bipedLeftArm.rotationPointY = 2F;
                modelPlayer.bipedLeftArm.rotationPointX = -1.3F;

                modelPlayer.bipedRightArm.rotateAngleX = -1F;
                modelPlayer.bipedRightArm.rotateAngleY = (float) Math.toRadians(106);
                modelPlayer.bipedRightArm.rotateAngleZ = -1F;

                modelPlayer.bipedRightArm.rotationPointZ = 5F;
                modelPlayer.bipedRightArm.rotationPointY = 2F;
                modelPlayer.bipedRightArm.rotationPointX = 1.3F;
            }
        }
    }

    @SubscribeEvent
    public void onRenderPlayer(ModelPlayerEvent.Render.Pre event)
    {
        EntityPlayer player = event.getEntityPlayer();
        Entity entity = player.getRidingEntity();
        if (entity instanceof EntitySkateboard)
        {
            EntitySkateboard skateboard = (EntitySkateboard) entity;
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
                    //super.render(paramEntity, paramFloat1, paramFloat2, paramFloat3, -rotation, paramFloat5, paramFloat6);
                }
            }
        }
    }
}
