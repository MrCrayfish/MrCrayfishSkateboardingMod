package com.mrcrayfish.skateboarding.client.model.block;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import com.mrcrayfish.skateboarding.util.TransformationBuilder;

import net.minecraftforge.common.model.TRSRTransformation;

public class CommonTransforms 
{
	public static final TRSRTransformation BLOCK_INVENTORY;
	
	static
	{
		BLOCK_INVENTORY = new TransformationBuilder().setTranslation(0, 0, 0).setRotation(30, 225, 0).setScale(0.625F).build();
	}
}
