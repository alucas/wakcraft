package heero.mc.mod.wakcraft.network.packet.fight;

import heero.mc.mod.wakcraft.fight.FightBlockCoordinates;
import heero.mc.mod.wakcraft.fight.FightBlockCoordinates.TYPE;
import heero.mc.mod.wakcraft.helper.FightHelper;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;

public class PacketFight implements IPacketFight {
	protected int fightId = 0;

	public PacketFight() {
	}

	public PacketFight(int fightId) {
		this.fightId = fightId;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		buffer.writeInt(fightId);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		this.fightId = buffer.readInt();
	}


	@Override
	public void handleClientSide(EntityPlayer player) throws Exception {
	}

	@Override
	public void handleServerSide(EntityPlayer player) throws Exception {
		throw new RuntimeException("This is a client side packet");
	}

	@Override
	public int getFightId() {
		return fightId;
	}

	protected static List<List<EntityLivingBase>> getEntities(World world, List<List<Integer>> fightersId) {
		List<List<EntityLivingBase>> fighters = new ArrayList<List<EntityLivingBase>>();

		Iterator<List<Integer>> iterator = fightersId.iterator();
		while (iterator.hasNext()) {
			List<EntityLivingBase> team = new ArrayList<EntityLivingBase>();

			Iterator<Integer> teamIterator = iterator.next().iterator();
			while (teamIterator.hasNext()) {
				int entityId = teamIterator.next();
				Entity entity = world.getEntityByID(entityId);
				if (entity == null) {
					FMLLog.warning("No entity found for id " + entityId);
					continue;
				}

				if (!FightHelper.isFighter(entity)) {
					FMLLog.warning("The entity " + entity + " is not a valid fighter");
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

	protected static void encodeFighters(PacketBuffer buffer, List<List<Integer>> fightersId) {
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

	protected static List<List<Integer>> decodeFighters(PacketBuffer buffer) {
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

	protected static void encoreStartPositions(PacketBuffer buffer, List<FightBlockCoordinates> startPositions) {
		buffer.writeInt(startPositions.size());

		for (FightBlockCoordinates block : startPositions) {
			buffer.writeInt(block.posX);
			buffer.writeInt(block.posY);
			buffer.writeInt(block.posZ);
		}
	}

	protected static List<FightBlockCoordinates> decodeStartPositions(PacketBuffer buffer) {
		int nbBlock = buffer.readInt();

		List<FightBlockCoordinates> startPositions = new ArrayList<FightBlockCoordinates>();
		for (int i = 0; i < nbBlock; i++) {
			startPositions.add(new FightBlockCoordinates(buffer.readInt(), buffer.readInt(), buffer.readInt(), TYPE.NORMAL));
		}

		return startPositions;
	}
}
