package heero.mc.mod.wakcraft.network.packet.fight;

import heero.mc.mod.wakcraft.fight.FightInfo.FightStage;
import io.netty.buffer.ByteBuf;

public class PacketFightChangeStage implements IPacketFight {
    protected PacketFight packetFight;

    public FightStage stage;

    public PacketFightChangeStage() {
        packetFight = new PacketFight();
    }

    public PacketFightChangeStage(int fightId, FightStage stage) {
        packetFight = new PacketFight(fightId);

        this.stage = stage;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        packetFight.toBytes(buffer);

        buffer.writeByte(stage.ordinal());
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        packetFight.fromBytes(buffer);

        int stageId = buffer.readByte();
        if (stageId < 0 || stageId >= FightStage.values().length) {
            throw new RuntimeException("Invialide value of 'stage'");
        }

        stage = FightStage.values()[stageId];
    }

    @Override
    public int getFightId() {
        return packetFight.getFightId();
    }
}
