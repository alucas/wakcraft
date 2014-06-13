package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.helper.FightHelper;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightSelectPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientFightSelectPosition implements IMessageHandler<PacketFightSelectPosition, IMessage> {
	@Override
	public IMessage onMessage(PacketFightSelectPosition message, MessageContext ctx) {
		Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(message.fighterId);
		if (entity == null) {
			throw new RuntimeException("No entity found for id " + message.fighterId);
		}

		if (!FightHelper.isFighter(entity)) {
			throw new RuntimeException("The entity " + entity + " is not a valid fighter");
		}

		FightHelper.setStartPosition(entity, message.selectedPosition);

		return null;
	}
}
