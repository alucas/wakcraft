package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.network.packet.PacketProfession;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientProfession implements IMessageHandler<PacketProfession, IMessage> {
	@Override
	public IMessage onMessage(PacketProfession message, MessageContext ctx) {
		EntityPlayer player = Wakcraft.proxy.getClientPlayer();

		for (PROFESSION profession : message.xps.keySet()) {
			ProfessionManager.setXp(player, profession, message.xps.get(profession));
		}

		return null;
	}
}