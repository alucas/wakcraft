package heero.mc.mod.wakcraft.network.packet.fight;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public interface IPacketFight extends IMessage {
    public int getFightId();
}
