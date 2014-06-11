package heero.mc.mod.wakcraft.network.packet.fight;

import heero.mc.mod.wakcraft.fight.FightManager;
import net.minecraft.entity.player.EntityPlayer;

public class PacketFightStop extends PacketFight {
	public PacketFightStop() {
	}

	public PacketFightStop(int fightId) {
		super(fightId);
	}

	@Override
	public void handleClientSide(EntityPlayer player) throws Exception {
		FightManager.INSTANCE.stopFight(player.worldObj, fightId);
	}
}
