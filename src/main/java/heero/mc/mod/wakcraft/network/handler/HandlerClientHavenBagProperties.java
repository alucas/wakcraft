package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.havenbag.HavenBagsManager;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagProperties;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientHavenBagProperties implements IMessageHandler<PacketHavenBagProperties, IMessage> {
	@Override
	public IMessage onMessage(PacketHavenBagProperties message, MessageContext ctx) {
		HavenBagsManager.setHavenBagNBT(message.tagHavenBag);

		return null;
	}
}