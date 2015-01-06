package com.mrcrayfish.skateboarding.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.mrcrayfish.skateboarding.MrCrayfishSkateboardingMod;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class MessageUpdatePos implements IMessage, IMessageHandler<MessageUpdatePos, IMessage>
{
	private int entityId;
	private double posX = 0.0;
	private double posY = 0.0;
	private double posZ = 0.0;
	private double motionX = 0.0;
	private double motionY = 0.0;
	private double motionZ = 0.0;
	private float rotationYaw = 0.0F;

	public MessageUpdatePos()
	{

	}

	public MessageUpdatePos(int entityId, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float rotationYaw)
	{
		this.entityId = entityId;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
		this.rotationYaw = rotationYaw;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityId);
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
		buf.writeDouble(motionX);
		buf.writeDouble(motionY);
		buf.writeDouble(motionZ);
		buf.writeFloat(rotationYaw);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.posX = buf.readDouble();
		this.posY = buf.readDouble();
		this.posZ = buf.readDouble();
		this.motionX = buf.readDouble();
		this.motionY = buf.readDouble();
		this.motionZ = buf.readDouble();
		this.rotationYaw = buf.readFloat();
	}

	@Override
	public IMessage onMessage(MessageUpdatePos message, MessageContext ctx)
	{
		// System.out.println("Got packet!");
		World world = ctx.getServerHandler().playerEntity.worldObj;
		Entity entity = world.getEntityByID(message.entityId);
		if (entity instanceof EntitySkateboard)
		{
			EntitySkateboard skateboard = (EntitySkateboard) entity;
			skateboard.setPosition(message.posX, message.posY, message.posZ);
			skateboard.motionX = message.motionX;
			skateboard.motionY = message.motionY;
			skateboard.motionZ = message.motionZ;
			skateboard.rotationYaw = message.rotationYaw;
		}
		return null;
	}

}
