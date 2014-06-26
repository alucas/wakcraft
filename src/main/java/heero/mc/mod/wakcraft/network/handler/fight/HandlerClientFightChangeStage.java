package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightChangeStage;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientFightChangeStage implements IMessageHandler<PacketFightChangeStage, IMessage> {
	@Override
	public IMessage onMessage(PacketFightChangeStage message, MessageContext ctx) {
		FightManager.INSTANCE.changeFightStage(Wakcraft.proxy.getClientWorld(), message.getFightId(), message.stage);
		return null;
	}
}
