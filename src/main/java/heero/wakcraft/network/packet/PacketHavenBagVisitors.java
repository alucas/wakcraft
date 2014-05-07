package heero.wakcraft.network.packet;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

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
		// update tile
	}
}