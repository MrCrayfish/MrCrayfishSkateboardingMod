package com.mrcrayfish.skateboarding.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class MessagePush implements IMessage, IMessageHandler<MessagePush, IMessage>
{
	private int entityId;

	public MessagePush()
	{
	}

	public MessagePush(int entityId)
	{
		this.entityId = entityId;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityId);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = buf.readInt();
	}

	@Override
	public IMessage onMessage(MessagePush message, MessageContext ctx)
	{
		System.out.println("Pushing");
		World world = ctx.getServerHandler().playerEntity.worldObj;
		EntitySkateboard skateboard = (EntitySkateboard) world.getEntityByID(message.entityId);
		skateboard.push();
		return null;
	}
}
