package heero.mc.mod.wakcraft.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketZaapTeleportation implements IMessage {
    public BlockPos zaapPos;

    public PacketZaapTeleportation() {
    }

    public PacketZaapTeleportation(final BlockPos zaapPos) {
        this.zaapPos = zaapPos;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        final int posX = buffer.readInt();
        final int posY = buffer.readInt();
        final int posZ = buffer.readInt();
        this.zaapPos = new BlockPos(posX, posY, posZ);
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.zaapPos.getX());
        buffer.writeInt(this.zaapPos.getY());
        buffer.writeInt(this.zaapPos.getZ());
    }
}