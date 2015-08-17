package heero.mc.mod.wakcraft.network.packet.fight;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.fight.FightBlockCoordinates;
import heero.mc.mod.wakcraft.fight.FightBlockCoordinates.TYPE;
import heero.mc.mod.wakcraft.util.FightUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PacketFight implements IPacketFight {
	public int fightId = 0;

	public PacketFight() {
	}

	public PacketFight(int fightId) {
		this.fightId = fightId;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(fightId);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.fightId = buffer.readInt();
	}

	@Override
	public int getFightId() {
		return fightId;
	}

	public static List<List<EntityLivingBase>> getEntities(World world, List<List<Integer>> fightersId) {
		List<List<EntityLivingBase>> fighters = new ArrayList<List<EntityLivingBase>>();

		Iterator<List<Integer>> iterator = fightersId.iterator();
		while (iterator.hasNext()) {
			List<EntityLivingBase> team = new ArrayList<EntityLivingBase>();

			Iterator<Integer> teamIterator = iterator.next().iterator();
			while (teamIterator.hasNext()) {
				int entityId = teamIterator.next();
				Entity entity = world.getEntityByID(entityId);
				if (entity == null) {
					WLog.warning("No entity found for id " + entityId);
					continue;
				}

				if (!FightUtil.isFighter(entity)) {
					WLog.warning("The entity " + entity + " is not a valid fighter");
					continue;
				}

				team.add((EntityLivingBase) entity);
			}

			fighters.add(team);
		}
		return fighters;
	}

	protected static List<List<Integer>> getIds(List<List<EntityLivingBase>> fighters) {
		List<List<Integer>> fightersId = new ArrayList<List<Integer>>();

		Iterator<List<EntityLivingBase>> iterator = fighters.iterator();
		while (iterator.hasNext()) {
			List<Integer> team = new ArrayList<Integer>();
			Iterator<EntityLivingBase> teamIterator = iterator.next().iterator();
			while (teamIterator.hasNext()) {
				team.add(teamIterator.next().getEntityId());
			}

			fightersId.add(team);
		}

		return fightersId;
	}

	protected static void encodeFighters(ByteBuf buffer, List<List<Integer>> fightersId) {
		Iterator<List<Integer>> iterator = fightersId.iterator();
		while (iterator.hasNext()) {
			List<Integer> teamFighters = iterator.next();

			buffer.writeInt(teamFighters.size());

			Iterator<Integer> teamIterator = teamFighters.iterator();
			while (teamIterator.hasNext()) {
				buffer.writeInt(teamIterator.next());
			}
		}
	}

	protected static List<List<Integer>> decodeFighters(ByteBuf buffer) {
		List<List<Integer>> fightersId = new ArrayList<List<Integer>>();

		for (int teamId = 0; teamId < 2; teamId++) {
			List<Integer> team = new ArrayList<Integer>();

			int teamSize = buffer.readInt();
			for (int index = 0; index < teamSize; index++) {
				team.add(buffer.readInt());
			}

			fightersId.add(team);
		}

		return fightersId;
	}

	protected static void encoreStartPositions(ByteBuf buffer, List<List<FightBlockCoordinates>> startPositions) {
		buffer.writeInt(startPositions.size());

		for (List<FightBlockCoordinates> startPositionOfTeam : startPositions) {
			buffer.writeInt(startPositionOfTeam.size());

			for (FightBlockCoordinates block : startPositionOfTeam) {
				buffer.writeInt(block.getX());
				buffer.writeInt(block.getY());
				buffer.writeInt(block.getZ());
			}
		}
	}

	protected static List<List<FightBlockCoordinates>> decodeStartPositions(ByteBuf buffer) {
		int nbTeam = buffer.readInt();

		List<List<FightBlockCoordinates>> startPositions = new ArrayList<List<FightBlockCoordinates>>();
		for (int i = 0; i < nbTeam; i++) {
			List<FightBlockCoordinates> startPositionsOfTeam = new ArrayList<FightBlockCoordinates>();

			int nbPosition = buffer.readInt();
			for (int j = 0; j < nbPosition; j++) {
				startPositionsOfTeam.add(new FightBlockCoordinates(buffer.readInt(), buffer.readInt(), buffer.readInt(), TYPE.NORMAL));
			}

			startPositions.add(startPositionsOfTeam);
		}

		return startPositions;
	}
}
