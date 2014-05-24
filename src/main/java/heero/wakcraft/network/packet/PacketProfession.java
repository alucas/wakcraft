package heero.wakcraft.network.packet;

import heero.wakcraft.manager.ProfessionManager;
import heero.wakcraft.manager.ProfessionManager.PROFESSION;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class PacketProfession implements IPacket {
	private Map<PROFESSION, Integer> xps;

	public PacketProfession() {
		xps = new HashMap<PROFESSION, Integer>();
	}

	public PacketProfession(EntityPlayer player) {
		xps = new HashMap<PROFESSION, Integer>();
		
		for (PROFESSION profession : PROFESSION.values()) {
			xps.put(profession, ProfessionManager.getXp(player, profession));
		}
	}

	public PacketProfession(EntityPlayer player, PROFESSION profession) {
		xps = new HashMap<PROFESSION, Integer>();
		xps.put(profession, ProfessionManager.getXp(player, profession));
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		buffer.writeByte(xps.size());
		for (PROFESSION profession : xps.keySet()) {
			buffer.writeByte(profession.getValue());
			buffer.writeInt(xps.get(profession));
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
		int nbProfession = buffer.readByte();
		for (int i = 0; i < nbProfession; i++) {
			PROFESSION profession = PROFESSION.getProfession(buffer.readByte());
			int xp = buffer.readInt();

			xps.put(profession, xp);
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		for (PROFESSION profession : xps.keySet()) {
			ProfessionManager.setXp(player, profession, xps.get(profession));
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
	}
}