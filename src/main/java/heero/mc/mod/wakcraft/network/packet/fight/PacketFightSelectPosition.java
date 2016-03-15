package heero.mc.mod.wakcraft.network.packet.fight;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;

import javax.annotation.Nullable;

public class PacketFightSelectPosition implements IPacketFight {
    protected IPacketFight packetFight;

    public Integer fighterId;
    public
    @Nullable
    BlockPos selectedPosition;

    public PacketFightSelectPosition() {
        this.packetFight = new PacketFight();
    }

    public PacketFightSelectPosition(int fightId, EntityLivingBase fighter, @Nullable BlockPos selectedPosition) {
        this.packetFight = new PacketFight(fightId);
        this.fighterId = fighter.getEntityId();
        this.selectedPosition = selectedPosition;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        packetFight.toBytes(buffer);

        buffer.writeInt(fighterId);

        if (selectedPosition == null) {
            buffer.writeInt(Integer.MAX_VALUE);
            buffer.writeInt(Integer.MAX_VALUE);
            buffer.writeInt(Integer.MAX_VALUE);
        } else {
            buffer.writeInt(selectedPosition.getX());
            buffer.writeInt(selectedPosition.getY());
            buffer.writeInt(selectedPosition.getZ());
        }
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        packetFight.fromBytes(buffer);

        fighterId = buffer.readInt();

        int posX = buffer.readInt();
        int posY = buffer.readInt();
        int posZ = buffer.readInt();

        if (posX != Integer.MAX_VALUE || posY != Integer.MAX_VALUE || posZ != Integer.MAX_VALUE) {
            selectedPosition = new BlockPos(posX, posY, posZ);
        } else {
            selectedPosition = null;
        }
    }

    @Override
    public int getFightId() {
        return packetFight.getFightId();
    }
}
