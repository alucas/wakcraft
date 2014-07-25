package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.fight.FightInfo;
import heero.mc.mod.wakcraft.fight.FightUtil;
import heero.mc.mod.wakcraft.helper.FightHelper;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightCastSpell;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerServerFightCastSpell implements IMessageHandler<PacketFightCastSpell, IMessage> {
	@Override
	public IMessage onMessage(PacketFightCastSpell message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;

		if (!FightHelper.isFighter(player)) {
			throw new RuntimeException("The entity " + player + " is not a valid fighter");
		}

		if (!FightHelper.isFighting(player)) {
			throw new RuntimeException("The entity " + player + " is not fighting");
		}

		int fightId = FightHelper.getFightId(player);
		if (FightHelper.getFightStage(player.worldObj, fightId) != FightInfo.FightStage.FIGHT) {
			throw new RuntimeException("The entity " + player + " can't cast a spell, the fight is not in the right stage ( " + FightHelper.getFightStage(player.worldObj, fightId) + " )");
		}

		if (FightHelper.getCurrentFighter(player.worldObj, fightId) != player) {
			throw new RuntimeException("The entity " + player + " can't cast a spell, it's not his turn");
		}

		FightUtil.tryCastSpell(player, message.targetPosition);

		return null;
	}
}
