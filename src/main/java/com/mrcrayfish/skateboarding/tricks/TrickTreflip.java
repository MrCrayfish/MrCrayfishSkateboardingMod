package com.mrcrayfish.skateboarding.tricks;

import net.minecraft.client.model.ModelRenderer;

public class TrickTreflip extends Trick
{
	@Override
	public void updateMovement(ModelRenderer skateboard, int tick)
	{
		// performTime @ 0 = 0:0:0
		// tick < 3 ? tick
		// performTime @ 3 = 3:0:3
		// tick < 9 ? 6 - tick
		// performTime @ 6 = 6:0:0
		// tick < 9 ? 6 - tick
		// performTime @ 9 = 3:6:-3
		// performTime @ 12 = 0:0:0
		
		//purple 126
		//Orange 247
		//Black 147
		//Blue 356
		
		//tick = 11;
		//boolean flag = true;
		
		if (tick < performTime())
		{
			skateboard.rotateAngleX = (float) Math.toRadians(-(360 /performTime()) * (tick <= 6 ? tick : 12 - tick));
			skateboard.rotateAngleY = (float) Math.toRadians(-(360 / performTime()) * (tick <= 2 ? -1 : (tick <= 3 ? 0 : (tick <= 5 ? 1 : (tick <= 6 ? 0 : (tick <= 8 ? -1 : (tick <= 9 ? 0 : 1)))))));
			skateboard.rotateAngleZ = (float) Math.toRadians(-(360 /performTime()) * (tick <= 3 ? tick : (tick <= 9 ? 6 - tick : -12 + tick)));
			//System.out.println("RotX @ " + tick + " = " + (tick <= 6 ? tick : 12 - tick));
			//System.out.println("RotY @ " + tick + " = " + (tick <= 2 ? -1 : (tick <= 3 ? 0 : (tick <= 5 ? 1 : (tick <= 6 ? 0 : (tick <= 8 ? -1 : (tick <= 9 ? 0 : 1)))))));
			//System.out.println("RotZ @ " + tick + " = " + (tick <= 3 ? tick : (tick <= 9 ? 6 - tick : 12 - tick)));
			
			switch (tick)
			{

			case 1:
				skateboard.rotateAngleX = (float) Math.toRadians(-(360 / performTime()) * 1);
				skateboard.rotateAngleY = (float) Math.toRadians(-(360 / performTime()) * -1);
				skateboard.rotateAngleZ = (float) Math.toRadians(-(360 / performTime()) * 1);
				break;
			case 2:
				skateboard.rotateAngleX = (float) Math.toRadians(-(360 / performTime()) * 2);
				skateboard.rotateAngleY = (float) Math.toRadians(-(360 / performTime()) * -1);
				skateboard.rotateAngleZ = (float) Math.toRadians(-(360 / performTime()) * 2);
				break;
			case 3:
				skateboard.rotateAngleX = (float) Math.toRadians(-(360 / performTime()) * 3);
				skateboard.rotateAngleY = (float) Math.toRadians(-(360 / performTime()) * 0);
				skateboard.rotateAngleZ = (float) Math.toRadians(-(360 / performTime()) * 3);
				break;
			case 4:
				skateboard.rotateAngleX = (float) Math.toRadians(-(360 / performTime()) * 4);
				skateboard.rotateAngleY = (float) Math.toRadians(-(360 / performTime()) * 1);
				skateboard.rotateAngleZ = (float) Math.toRadians(-(360 / performTime()) * 2);
				break;
			case 5:
				skateboard.rotateAngleX = (float) Math.toRadians(-(360 / performTime()) * 5);
				skateboard.rotateAngleY = (float) Math.toRadians(-(360 / performTime()) * 1);
				skateboard.rotateAngleZ = (float) Math.toRadians(-(360 / performTime()) * 1);
				break;
			case 6:
				skateboard.rotateAngleX = (float) Math.toRadians(-(360 / performTime()) * 6);
				skateboard.rotateAngleY = (float) Math.toRadians(-(360 / performTime()) * 0);
				skateboard.rotateAngleZ = (float) Math.toRadians(-(360 / performTime()) * 0);
				break;
			case 7:
				skateboard.rotateAngleX = (float) Math.toRadians(-(360 / performTime()) * 5);
				skateboard.rotateAngleY = (float) Math.toRadians(-(360 / performTime()) * -1);
				skateboard.rotateAngleZ = (float) Math.toRadians(-(360 / performTime()) * -1);
				break;
			case 8:
				skateboard.rotateAngleX = (float) Math.toRadians(-(360 / performTime()) * 4);
				skateboard.rotateAngleY = (float) Math.toRadians(-(360 / performTime()) * -1);
				skateboard.rotateAngleZ = (float) Math.toRadians(-(360 / performTime()) * -2);
				break;
			case 9:
				skateboard.rotateAngleX = (float) Math.toRadians(-(360 / performTime()) * 3);
				skateboard.rotateAngleY = (float) Math.toRadians(-(360 / performTime()) * 0);
				skateboard.rotateAngleZ = (float) Math.toRadians(-(360 / performTime()) * -3);
				break;
			case 10:
				skateboard.rotateAngleX = (float) Math.toRadians(-(360 / performTime()) * 2);
				skateboard.rotateAngleY = (float) Math.toRadians(-(360 / performTime()) * 1);
				skateboard.rotateAngleZ = (float) Math.toRadians(-(360 / performTime()) * -2);
				break;
			case 11:
				skateboard.rotateAngleX = (float) Math.toRadians(-(360 / performTime()) * 1);
				skateboard.rotateAngleY = (float) Math.toRadians(-(360 / performTime()) * 1);
				skateboard.rotateAngleZ = (float) Math.toRadians(-(360 / performTime()) * -1);
				break;
			}
		} 
	}

	@Override
	public String getName()
	{
		return "360 Flip";
	}

	@Override
	public int performTime()
	{
		return 12;
	}

}
