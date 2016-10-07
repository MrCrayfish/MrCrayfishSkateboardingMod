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
	private double posX;
	private double posY;
	private double posZ;

	public MessageUpdatePos() {}

	public MessageUpdatePos(int entityId, double posX, double posY, double posZ)
	{
		this.entityId = entityId;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityId);
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.posX = buf.readDouble();
		this.posY = buf.readDouble();
		this.posZ = buf.readDouble();
	}

	@Override
	public IMessage onMessage(MessageUpdatePos message, MessageContext ctx)
	{
		World world = ctx.getServerHandler().playerEntity.worldObj;
		Entity entity = world.getEntityByID(message.entityId);
		if (entity instanceof EntitySkateboard)
		{
			EntitySkateboard skateboard = (EntitySkateboard) entity;
			skateboard.setPosition(message.posX, message.posY, message.posZ);
			skateboard.updatePassenger(skateboard.getControllingPassenger());
		}
		return null;
	}

}
