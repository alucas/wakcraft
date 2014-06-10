package heero.mc.mod.wakcraft.network.packet;

import heero.mc.mod.wakcraft.event.FightEvent.Type;
import heero.mc.mod.wakcraft.helper.FightHelper;
import heero.mc.mod.wakcraft.manager.FightBlockCoordinates;
import heero.mc.mod.wakcraft.manager.FightBlockCoordinates.TYPE;
import heero.mc.mod.wakcraft.manager.FightManager;
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

public class PacketFight implements IPacket {
	protected Type type;
	protected int fightId;
	protected List<List<Integer>> fightersId;
	protected List<FightBlockCoordinates> startBlocks;

	public PacketFight() {
		this.fightersId = new ArrayList<List<Integer>>();
	}

	public PacketFight(Type type, int fightId, List<List<EntityLivingBase>> fighters) {
		this.type = type;
		this.fightId = fightId;
		this.fightersId = new ArrayList<List<Integer>>();

		Iterator<List<EntityLivingBase>> iterator = fighters.iterator();
		while (iterator.hasNext()) {
			List<Integer> team = new ArrayList<Integer>();
			Iterator<EntityLivingBase> teamIterator = iterator.next().iterator();
			while (teamIterator.hasNext()) {
				team.add(teamIterator.next().getEntityId());
			}

			fightersId.add(team);
		}
	}

	public PacketFight(Type type, int fightId, List<List<EntityLivingBase>> fighters, List<FightBlockCoordinates> startBlocks) {
		this(type, fightId, fighters);

		this.startBlocks = startBlocks;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		buffer.writeInt(type.ordinal());
		buffer.writeInt(fightId);

		Iterator<List<Integer>> iterator = fightersId.iterator();
		while (iterator.hasNext()) {
			List<Integer> teamFighters = iterator.next();

			buffer.writeInt(teamFighters.size());

			Iterator<Integer> teamIterator = teamFighters.iterator();
			while (teamIterator.hasNext()) {
				buffer.writeInt(teamIterator.next());
			}
		}

		if (type == Type.START) {
			buffer.writeInt(startBlocks.size());

			for (FightBlockCoordinates block : startBlocks) {
				buffer.writeInt(block.posX);
				buffer.writeInt(block.posY);
				buffer.writeInt(block.posZ);
			}
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		int typeId = buffer.readInt();
		if (typeId < 0 && typeId >= Type.values().length) {
			throw new RuntimeException("Wrong value of type");
		}

		this.type = Type.values()[typeId];
		this.fightId = buffer.readInt();

		for (int teamId = 0; teamId < 2; teamId++) {
			List<Integer> team = new ArrayList<Integer>();

			int teamSize = buffer.readInt();
			for (int index = 0; index < teamSize; index++) {
				team.add(buffer.readInt());
			}

			this.fightersId.add(team);
		}

		if (type == Type.START) {
			int nbBlock = buffer.readInt();

			this.startBlocks = new ArrayList<FightBlockCoordinates>();
			for (int i = 0; i < nbBlock; i++) {
				startBlocks.add(new FightBlockCoordinates(buffer.readInt(), buffer.readInt(), buffer.readInt(), TYPE.NORMAL));
			}
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) throws Exception {
		List<List<EntityLivingBase>> fighters;

		switch (type) {
		case START:
			fighters = getEntities(player.worldObj, fightersId);
			FightManager.startClientFight(player.worldObj, fightId, fighters, startBlocks);
			break;

		case STOP:
			FightManager.stopFight(player.worldObj, fightId);
			break;

		default:
			FMLLog.warning("Unknow type");
			break;
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) throws Exception {
		throw new RuntimeException("This is a client side packet");
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
}
