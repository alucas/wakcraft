package heero.mc.mod.wakcraft.network.packet;

import heero.mc.mod.wakcraft.event.FightEvent;
import heero.mc.mod.wakcraft.event.FightEvent.Type;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLLog;

public class PacketFight implements IPacket {
	protected Type type;
	protected int fightId;
	protected List<List<Integer>> fighters;

	public PacketFight() {
		this.fighters = new ArrayList<List<Integer>>();
	}

	public PacketFight(Type type, int fightId, List<List<Integer>> fighters) {
		this.type = type;
		this.fightId = fightId;
		this.fighters = fighters;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		buffer.writeInt(type.ordinal());
		buffer.writeInt(fightId);

		Iterator<List<Integer>> iterator = fighters.iterator();
		while (iterator.hasNext()) {
			List<Integer> teamFighters = iterator.next();

			buffer.writeInt(teamFighters.size());

			Iterator<Integer> teamIterator = teamFighters.iterator();
			while (teamIterator.hasNext()) {
				buffer.writeInt(teamIterator.next());
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

			this.fighters.add(team);
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) throws Exception {
		switch (type) {
		case START:
			MinecraftForge.EVENT_BUS.post(new FightEvent(player.worldObj, type, fightId, fighters));
			break;

		case STOP:
			MinecraftForge.EVENT_BUS.post(new FightEvent(player.worldObj, type, fightId, fighters));
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
}
