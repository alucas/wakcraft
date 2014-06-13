package heero.mc.mod.wakcraft.network.packet.fight;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChunkCoordinates;

import com.sun.istack.internal.Nullable;

public class PacketFightSelectPosition implements IPacketFight {
	protected IPacketFight packetFight;

	public Integer fighterId;
	public @Nullable ChunkCoordinates selectedPosition;

	public PacketFightSelectPosition() {
		this.packetFight = new PacketFight();
	}

	public PacketFightSelectPosition(int fightId, EntityLivingBase fighter, @Nullable ChunkCoordinates selectedPosition) {
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
			buffer.writeInt(selectedPosition.posX);
			buffer.writeInt(selectedPosition.posY);
			buffer.writeInt(selectedPosition.posZ);
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
			selectedPosition = new ChunkCoordinates(posX, posY, posZ);
		} else {
			selectedPosition = null;
		}
	}

	@Override
	public int getFightId() {
		return packetFight.getFightId();
	}
}
