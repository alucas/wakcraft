package heero.wakcraft.network.packet;

import heero.wakcraft.Wakcraft;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import cpw.mods.fml.common.FMLLog;

public class PacketOpenWindow implements IPacket {
	public static final int WINDOW_HB_VISITORS = 1;

	private int windowId;

	public PacketOpenWindow() {
	}

	public PacketOpenWindow(int windowId) {
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
		if (windowId == WINDOW_HB_VISITORS) {
			Wakcraft.proxy.openHBVisitorsGui(player);
		} else {
			FMLLog.warning("Unknow window ID : %d", windowId);
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
	}
}