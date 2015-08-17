package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFight;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightStart;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientFightStart implements IMessageHandler<PacketFightStart, IMessage> {
	@Override
	public IMessage onMessage(PacketFightStart message, MessageContext ctx) {
		World world = Wakcraft.proxy.getClientWorld();

		FightManager.INSTANCE.startClientFight(world, message.getFightId(), PacketFight.getEntities(world, message.fightersId), message.startPositions);

		return null;
	}
}
