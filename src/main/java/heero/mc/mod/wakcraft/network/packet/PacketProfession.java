package heero.mc.mod.wakcraft.network.packet;

import heero.mc.mod.wakcraft.manager.ProfessionManager;
import heero.mc.mod.wakcraft.manager.ProfessionManager.PROFESSION;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketProfession implements IMessage {
	public Map<PROFESSION, Integer> xps;

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
	public void toBytes(ByteBuf buffer) {
		buffer.writeByte(xps.size());
		for (PROFESSION profession : xps.keySet()) {
			buffer.writeByte(profession.getValue());
			buffer.writeInt(xps.get(profession));
		}
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		int nbProfession = buffer.readByte();
		for (int i = 0; i < nbProfession; i++) {
			PROFESSION profession = PROFESSION.getProfession(buffer.readByte());
			int xp = buffer.readInt();

			xps.put(profession, xp);
		}
	}
}