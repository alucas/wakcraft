package heero.mc.mod.wakcraft.network.packet.fight;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;

public class PacketFightStartTurn implements IPacketFight {
	protected IPacketFight packetFight;

	public Integer fighterId;

	public PacketFightStartTurn() {
		this.packetFight = new PacketFight();
	}

	public PacketFightStartTurn(int fightId, EntityLivingBase fighter) {
		this.packetFight = new PacketFight(fightId);
		this.fighterId = fighter.getEntityId();
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		packetFight.toBytes(buffer);

		buffer.writeInt(fighterId);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		packetFight.fromBytes(buffer);

		fighterId = buffer.readInt();
	}

	@Override
	public int getFightId() {
		return packetFight.getFightId();
	}
}
