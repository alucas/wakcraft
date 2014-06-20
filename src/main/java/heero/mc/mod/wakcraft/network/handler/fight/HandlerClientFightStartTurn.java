package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightStartTurn;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientFightStartTurn implements IMessageHandler<PacketFightStartTurn, IMessage> {
	@Override
	public IMessage onMessage(PacketFightStartTurn message, MessageContext ctx) {
		World world = Minecraft.getMinecraft().theWorld;

		EntityLivingBase entity = (EntityLivingBase) world.getEntityByID(message.fighterId);
		if (entity == null) {
			FMLLog.warning("No loaded entity found with the id : " + message.fighterId);
			return null;
		}

		FightManager.INSTANCE.startTurn(world, message.getFightId(), entity);

		return null;
	}
}
