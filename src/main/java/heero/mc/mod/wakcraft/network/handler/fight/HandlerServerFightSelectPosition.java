package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.helper.FightHelper;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightSelectPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerServerFightSelectPosition implements IMessageHandler<PacketFightSelectPosition, IMessage> {
	@Override
	public IMessage onMessage(PacketFightSelectPosition message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;

		if (!FightHelper.isFighter(player)) {
			throw new RuntimeException("The entity " + player + " is not a valid fighter");
		}

		FightHelper.selectPosition((EntityLivingBase) player, message.selectedPosition);

		return null;
	}
}
