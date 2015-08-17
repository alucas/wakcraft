package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightChangeStage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientFightChangeStage implements IMessageHandler<PacketFightChangeStage, IMessage> {
	@Override
	public IMessage onMessage(PacketFightChangeStage message, MessageContext ctx) {
		FightManager.INSTANCE.changeFightStage(Wakcraft.proxy.getClientWorld(), message.getFightId(), message.stage);
		return null;
	}
}
