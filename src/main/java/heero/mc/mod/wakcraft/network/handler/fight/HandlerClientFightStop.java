package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightStop;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientFightStop implements IMessageHandler<PacketFightStop, IMessage> {
	@Override
	public IMessage onMessage(PacketFightStop message, MessageContext ctx) {
		FightManager.INSTANCE.stopFight(Wakcraft.proxy.getClientWorld(), message.getFightId());

		return null;
	}
}
