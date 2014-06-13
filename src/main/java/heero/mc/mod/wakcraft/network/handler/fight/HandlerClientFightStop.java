package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightStop;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientFightStop implements IMessageHandler<PacketFightStop, IMessage> {
	@Override
	public IMessage onMessage(PacketFightStop message, MessageContext ctx) {
		FightManager.INSTANCE.stopFight(Minecraft.getMinecraft().theWorld, message.getFightId());

		return null;
	}
}
