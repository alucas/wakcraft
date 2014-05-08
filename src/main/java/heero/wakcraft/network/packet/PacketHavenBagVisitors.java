package heero.wakcraft.network.packet;

import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.havenbag.HavenBagHelper;
import heero.wakcraft.tileentity.TileEntityHavenBagProperties;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import cpw.mods.fml.common.FMLLog;

public class PacketHavenBagVisitors implements IPacket {
	// Client -> Server
	public static final int ACTION_ADD = 1;
	public static final int ACTION_REMOVE = 2;

	public static final int R_GARDEN = 1;
	public static final int R_CRAFT = 2;
	public static final int R_DECO = 4;
	public static final int R_MERCHANT = 8;

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

		TileEntityHavenBagProperties tile = HavenBagHelper.getHavenBagProperties(player.worldObj, havenBagUid);
		if (tile == null) {
			return;
		}

		System.out.println(tile.acl.get(playerName));
		if (action == ACTION_ADD) {
			tile.acl.put(playerName, tile.acl.get(playerName) | right);
			tile.markDirty();
		} else if (action == ACTION_REMOVE) {
			tile.acl.put(playerName, tile.acl.get(playerName) & ((~right) & 0xF));
			tile.markDirty();
		} else {
			FMLLog.warning("Unknow action : %d", action);
			return;
		}
	}
}