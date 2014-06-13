package heero.mc.mod.wakcraft.network.packet.fight;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public interface IPacketFight extends IMessage {
	public int getFightId();
}
