package heero.wakcraft.network.packet;

import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.havenbag.HavenBagHelper;
import heero.wakcraft.havenbag.HavenBagProperties;
import heero.wakcraft.havenbag.HavenBagsManager;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import cpw.mods.fml.common.FMLLog;

public class PacketHavenBagVisitors implements IPacket {
	// Client -> Server
	public static final int ACTION_ADD = 1;
	public static final int ACTION_REMOVE = 2;

	private int action;
	private String playerName;
	private int right;

	public PacketHavenBagVisitors() {
	}

	public PacketHavenBagVisitors(int action, String playerName, int right) {
		this.action = action;
		this.playerName = playerName;
		this.right = right;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) throws IOException {
		buffer.writeInt(action);
		buffer.writeInt(right);
		buffer.writeStringToBuffer(playerName);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) throws IOException {
		this.action = buffer.readInt();
		this.right = buffer.readInt();
		this.playerName = buffer.readStringFromBuffer(16);
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading player (%s) havenbag properties", player.getDisplayName());
			return;
		}

		int havenBagUid = HavenBagHelper.getUIDFromCoord((int)player.posX, (int)player.posY, (int)player.posZ);
		if (properties.uid != havenBagUid) {
			FMLLog.warning("Player (%s) tried to update the permission of havenbag %d", player.getDisplayName(), havenBagUid);
			return;
		}

		HavenBagProperties hbProperties = HavenBagsManager.getProperties(havenBagUid);
		if (hbProperties == null) {
			return;
		}

		if (action == ACTION_ADD) {
			hbProperties.acl.put(playerName, hbProperties.acl.get(playerName) | right);
			HavenBagsManager.setProperties(havenBagUid, hbProperties);
		} else if (action == ACTION_REMOVE) {
			hbProperties.acl.put(playerName, hbProperties.acl.get(playerName) & ((~right) & 0xF));
			HavenBagsManager.setProperties(havenBagUid, hbProperties);
		} else {
			FMLLog.warning("Unknow action : %d", action);
			return;
		}
	}
}