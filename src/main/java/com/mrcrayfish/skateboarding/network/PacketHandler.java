/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2014  MrCrayfish (http://www.mrcrayfish.com/)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.skateboarding.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import com.mrcrayfish.skateboarding.Reference;
import com.mrcrayfish.skateboarding.network.message.MessageJump;
import com.mrcrayfish.skateboarding.network.message.MessageMovement;
import com.mrcrayfish.skateboarding.network.message.MessagePush;
import com.mrcrayfish.skateboarding.network.message.MessageStack;
import com.mrcrayfish.skateboarding.network.message.MessageTrick;

public class PacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

	public static void init()
	{
		INSTANCE.registerMessage(MessagePush.class, MessagePush.class, 0, Side.SERVER);
		INSTANCE.registerMessage(MessageJump.class, MessageJump.class, 1, Side.SERVER);
		INSTANCE.registerMessage(MessageTrick.class, MessageTrick.class, 2, Side.SERVER);
		INSTANCE.registerMessage(MessageStack.class, MessageStack.class, 3, Side.SERVER);
		INSTANCE.registerMessage(MessageMovement.class, MessageMovement.class, 4, Side.CLIENT);
	}
}
