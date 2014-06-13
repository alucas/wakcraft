package heero.mc.mod.wakcraft.network.packet.fight;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChunkCoordinates;

public class PacketFightSelectPosition implements IPacketFight {
	protected IPacketFight packetFight;

	public Integer fighterId;
	public ChunkCoordinates selectedPosition;

	public PacketFightSelectPosition() {
		this.packetFight = new PacketFight();
	}

	public PacketFightSelectPosition(int fightId, EntityLivingBase fighter, ChunkCoordinates selectedPosition) {
		this.packetFight = new PacketFight(fightId);
		this.fighterId = fighter.getEntityId();
		this.selectedPosition = selectedPosition;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		packetFight.toBytes(buffer);

		buffer.writeInt(fighterId);
		buffer.writeInt(selectedPosition.posX);
		buffer.writeInt(selectedPosition.posY);
		buffer.writeInt(selectedPosition.posZ);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		packetFight.fromBytes(buffer);

		fighterId = buffer.readInt();
		selectedPosition = new ChunkCoordinates(buffer.readInt(), buffer.readInt(), buffer.readInt());
	}

	@Override
	public int getFightId() {
		return packetFight.getFightId();
	}
}
