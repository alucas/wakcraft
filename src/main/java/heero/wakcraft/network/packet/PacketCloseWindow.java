package heero.wakcraft.network.packet;

import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.havenbag.HavenBagManager;
import heero.wakcraft.tileentity.TileEntityHavenBagProperties;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import cpw.mods.fml.common.FMLLog;

public class PacketCloseWindow implements IPacket {
	public static final int WINDOW_HB_VISITORS = 1;

	private int windowId;

	public PacketCloseWindow() {
	}

	public PacketCloseWindow(int windowId) {
		this.windowId = windowId;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) throws IOException {
		buffer.writeInt(windowId);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) throws IOException {
		this.windowId = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if (windowId == WINDOW_HB_VISITORS) {
			HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
			if (properties == null) {
				FMLLog.warning("Error while loading player (%s) havenbag properties", player.getDisplayName());
				return;
			}

			int havenBagUid = HavenBagManager.getUIDFromCoord((int)player.posX, (int)player.posY, (int)player.posZ);
			if (properties.uid != havenBagUid) {
				FMLLog.warning("Player (%s) requested to close a window that not belong to him", player.getDisplayName());
				return;
			}

			TileEntityHavenBagProperties tile = HavenBagManager.getHavenBagProperties(player.worldObj, havenBagUid);
			if (tile == null) {
				return;
			}

			if (player instanceof EntityPlayerMP) {
				System.out.println("update tile");
				((EntityPlayerMP) player).playerNetServerHandler.sendPacket(tile.getDescriptionPacket());
			}
		} else {
			FMLLog.warning("Unknow window ID : %d", windowId);
		}
	}
}