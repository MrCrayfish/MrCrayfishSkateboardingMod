package com.mrcrayfish.skateboarding.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntitySkateboard extends Entity{

	public EntitySkateboard(World world) {
		super(world);
	}
	
	private int life;
	
	@Override
	public void onUpdate()
	{
		life++;
		if(life == 10000)
		{
			this.setDead();
		}
	}

	@Override
	protected void entityInit() {
		System.out.println("Initialized Entity");
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		
	}
	
	public void setRollingDirection(int rot)
    {
        this.dataWatcher.updateObject(18, Integer.valueOf(rot));
    }

    public int getRollingDirection()
    {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

}
