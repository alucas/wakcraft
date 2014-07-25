package heero.mc.mod.wakcraft.network.packet.fight;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.ChunkCoordinates;

public class PacketFightCastSpell implements IPacketFight {
	protected PacketFight packetFight;

	public ChunkCoordinates targetPosition;

	public PacketFightCastSpell() {
		this.packetFight = new PacketFight();
	}

	public PacketFightCastSpell(final int fightId, ChunkCoordinates targetPosition) {
		this.packetFight = new PacketFight(fightId);

		this.targetPosition = targetPosition;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		this.packetFight.toBytes(buffer);

		buffer.writeInt(targetPosition.posX);
		buffer.writeInt(targetPosition.posY);
		buffer.writeInt(targetPosition.posZ);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.packetFight.fromBytes(buffer);

		this.targetPosition = new ChunkCoordinates(buffer.readInt(), buffer.readInt(), buffer.readInt());
	}

	@Override
	public int getFightId() {
		return packetFight.getFightId();
	}
}
