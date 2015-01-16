package com.mrcrayfish.skateboarding.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.mrcrayfish.skateboarding.MrCrayfishSkateboardingMod;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;

public class MessageMovement implements IMessage, IMessageHandler<MessageMovement, IMessage> {

	private int entityId;
	private double posX, posY, posZ;
	private float rotationYaw;

	public MessageMovement() {

	}

	public MessageMovement(int entityId, double posX, double posY, double posZ, float rotationYaw) {
		this.entityId = entityId;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.rotationYaw = rotationYaw;
	}

	@Override
	public IMessage onMessage(MessageMovement message, MessageContext ctx) {
		World world = MrCrayfishSkateboardingMod.proxy.getClientWorld();
		Entity entity = world.getEntityByID(message.entityId);
		if(entity instanceof EntitySkateboard)
		{
			EntitySkateboard skateboard = (EntitySkateboard)entity;
			skateboard.setDestination(message.posX, message.posY, message.posZ);
			skateboard.setAngles(message.rotationYaw, skateboard.rotationPitch);
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityId = buf.readInt();
		this.posX = buf.readDouble();
		this.posY = buf.readDouble();
		this.posZ = buf.readDouble();
		this.rotationYaw = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityId);
		buf.writeDouble(this.posX);
		buf.writeDouble(this.posY);
		buf.writeDouble(this.posZ);
		buf.writeFloat(this.rotationYaw);
	}

}
