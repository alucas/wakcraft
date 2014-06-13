package heero.mc.mod.wakcraft.network.packet.fight;

import heero.mc.mod.wakcraft.fight.FightBlockCoordinates;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;

public class PacketFightStart implements IPacketFight {
	protected IPacketFight packetFight;

	public List<List<Integer>> fightersId;
	public List<List<FightBlockCoordinates>> startPositions;

	public PacketFightStart() {
		this.packetFight = new PacketFight();
		this.fightersId = new ArrayList<List<Integer>>();
	}

	public PacketFightStart(int fightId, List<List<EntityLivingBase>> fighters, List<List<FightBlockCoordinates>> startPositions) {
		this.packetFight = new PacketFight(fightId);
		this.fightersId = PacketFight.getIds(fighters);
		this.startPositions = startPositions;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		packetFight.toBytes(buffer);

		PacketFight.encodeFighters(buffer, fightersId);
		PacketFight.encoreStartPositions(buffer, startPositions);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		packetFight.fromBytes(buffer);

		this.fightersId = PacketFight.decodeFighters(buffer);
		this.startPositions = PacketFight.decodeStartPositions(buffer);
	}

	@Override
	public int getFightId() {
		return packetFight.getFightId();
	}
}
