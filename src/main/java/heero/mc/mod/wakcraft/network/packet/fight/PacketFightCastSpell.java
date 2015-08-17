package heero.mc.mod.wakcraft.network.packet.fight;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.BlockPos;

public class PacketFightCastSpell implements IPacketFight {
	protected PacketFight packetFight;

	public BlockPos targetPosition;

	public PacketFightCastSpell() {
		this.packetFight = new PacketFight();
	}

	public PacketFightCastSpell(final int fightId, BlockPos targetPosition) {
		this.packetFight = new PacketFight(fightId);

		this.targetPosition = targetPosition;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		this.packetFight.toBytes(buffer);

		buffer.writeInt(targetPosition.getX());
		buffer.writeInt(targetPosition.getY());
		buffer.writeInt(targetPosition.getZ());
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.packetFight.fromBytes(buffer);

		this.targetPosition = new BlockPos(buffer.readInt(), buffer.readInt(), buffer.readInt());
	}

	@Override
	public int getFightId() {
		return packetFight.getFightId();
	}
}
