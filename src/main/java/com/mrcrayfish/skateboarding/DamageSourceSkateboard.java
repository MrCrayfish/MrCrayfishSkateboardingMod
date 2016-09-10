package com.mrcrayfish.skateboarding;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class DamageSourceSkateboard extends DamageSource
{
	public DamageSourceSkateboard(String damageTypeIn)
	{
		super(damageTypeIn);
	}

	@Override
	public ITextComponent getDeathMessage(EntityLivingBase living) 
	{
		return new TextComponentString(living.getName() + " got knocked out from a skateboarding accident");
	}
}
