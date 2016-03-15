package heero.mc.mod.wakcraft.network.packet.fight;

import io.netty.buffer.ByteBuf;

public class PacketFightStop implements IPacketFight {
    protected PacketFight packetFight;

    public PacketFightStop() {
        packetFight = new PacketFight();
    }

    public PacketFightStop(int fightId) {
        packetFight = new PacketFight(fightId);
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        packetFight.toBytes(buffer);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        packetFight.fromBytes(buffer);
    }

    @Override
    public int getFightId() {
        return packetFight.getFightId();
    }
}
