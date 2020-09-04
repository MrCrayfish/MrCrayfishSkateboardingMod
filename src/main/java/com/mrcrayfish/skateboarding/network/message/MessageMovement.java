package com.mrcrayfish.skateboarding.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.mrcrayfish.skateboarding.MrCrayfishSkateboardingMod;
import com.mrcrayfish.skateboarding.entity.EntitySkateboard;
import com.mrcrayfish.skateboarding.network.PacketHandler;

public class MessageMovement implements IMessage, IMessageHandler<MessageMovement, IMessage> {

	private int entityId;
	private double motionX, motionY, motionZ;

	public MessageMovement() {

	}

	public MessageMovement(int entityId, double motionX, double motionY, double motionZ) {
		this.entityId = entityId;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}

	@Override
	public IMessage onMessage(MessageMovement message, MessageContext ctx) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		Entity riding = player.getRidingEntity();
		if(riding != null && riding.getEntityId() == message.entityId) return null;
		Entity target = player.world.getEntityByID(message.entityId);
		if(target instanceof EntitySkateboard) {
			EntitySkateboard skateboard = (EntitySkateboard)target;
			skateboard.motionX = message.motionX;
			skateboard.motionY = message.motionY;
			skateboard.motionZ = message.motionZ;
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityId = buf.readInt();
		this.motionX = buf.readDouble();
		this.motionY = buf.readDouble();
		this.motionZ = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityId);
		buf.writeDouble(this.motionX);
		buf.writeDouble(this.motionY);
		buf.writeDouble(this.motionZ);
	}

}
