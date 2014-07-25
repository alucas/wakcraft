package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.fight.FightUtil;
import heero.mc.mod.wakcraft.helper.FightHelper;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightCastSpell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
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

		ChunkCoordinates fighterPosition = FightHelper.getCurrentPosition(player);
		ItemStack spellStack = FightHelper.getCurrentSpell(player);
		if (!FightUtil.isAimingPositionValid(fighterPosition, message.targetPosition, spellStack)) {
			throw new RuntimeException("Invalid position to cast the spell");
		}

		return null;
	}
}
