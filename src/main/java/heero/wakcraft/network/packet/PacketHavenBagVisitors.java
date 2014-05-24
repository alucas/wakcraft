package heero.wakcraft.network.packet;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.manager.HavenBagHelper;
import heero.wakcraft.manager.HavenBagProperties;
import heero.wakcraft.manager.HavenBagsManager;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
		if (properties.getUID() != havenBagUid) {
			FMLLog.warning("Player (%s) tried to update the permission of havenbag %d", player.getDisplayName(), havenBagUid);
			return;
		}

		HavenBagProperties hbProperties = HavenBagsManager.getProperties(havenBagUid);
		if (hbProperties == null) {
			return;
		}

		if (action == ACTION_ADD) {
			hbProperties.setRight(playerName, hbProperties.getRight(playerName) | right);
			HavenBagsManager.setProperties(havenBagUid, hbProperties);
		} else if (action == ACTION_REMOVE) {
			hbProperties.setRight(playerName, hbProperties.getRight(playerName) & ((~right) & 0xF));
			HavenBagsManager.setProperties(havenBagUid, hbProperties);
		} else {
			FMLLog.warning("Unknow action : %d", action);
			return;
		}

		for (Object entity : player.worldObj.getLoadedEntityList()) {
			if (entity instanceof EntityPlayerMP) {
				EntityPlayerMP playerMP = (EntityPlayerMP) entity;
				properties = (HavenBagProperty) playerMP.getExtendedProperties(HavenBagProperty.IDENTIFIER);
				if (properties == null) {
					FMLLog.warning("Error while loading player (%s) havenbag properties", playerMP.getDisplayName());
					continue;
				}

				if (properties.getHavenBag() != havenBagUid) {
					continue;
				}

				Wakcraft.packetPipeline.sendTo(new PacketHavenBagProperties(havenBagUid), playerMP);
			}
		}
	}
}