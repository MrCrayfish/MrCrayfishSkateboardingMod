package com.mrcrayfish.skateboarding.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.mrcrayfish.skateboarding.api.TrickRegistry;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class MessageTrick implements IMessage, IMessageHandler<MessageTrick, IMessage>
{
	private int entityId;
	private int trickId;

	public MessageTrick()
	{
	}

	public MessageTrick(int entityId, int trickId)
	{
		this.entityId = entityId;
		this.trickId = trickId;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityId);
		buf.writeInt(trickId);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.trickId = buf.readInt();
	}

	@Override
	public IMessage onMessage(MessageTrick message, MessageContext ctx)
	{
		World world = ctx.getServerHandler().playerEntity.worldObj;
		EntitySkateboard skateboard = (EntitySkateboard) world.getEntityByID(message.entityId);
		skateboard.startTrick(TrickRegistry.getTrick(message.trickId));
		return null;
	}
}
