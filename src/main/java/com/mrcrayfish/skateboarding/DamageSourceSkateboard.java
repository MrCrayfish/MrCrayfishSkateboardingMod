package com.mrcrayfish.skateboarding;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class DamageSourceSkateboard extends DamageSource
{
	public DamageSourceSkateboard(String damageTypeIn)
	{
		super(damageTypeIn);
	}

	@Override
	public IChatComponent getDeathMessage(EntityLivingBase living)
	{
		return new ChatComponentText(living.getName() + " got knocked out from a skateboarding accident");
	}
}
