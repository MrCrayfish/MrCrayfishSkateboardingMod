package com.mrcrayfish.skateboarding.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class MessageJump implements IMessage, IMessageHandler<MessageJump, IMessage>
{
	private double height;

	public MessageJump()
	{
	}

	public MessageJump(double height)
	{
		this.height = height;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeDouble(height);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.height = Math.min(3.0, buf.readDouble());
	}

	@Override
	public IMessage onMessage(MessageJump message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		Entity entity = player.getRidingEntity();
		if(entity instanceof EntitySkateboard) {
			EntitySkateboard skateboard = (EntitySkateboard) entity;
			skateboard.jump(message.height);
		}
		return null;
	}
}
