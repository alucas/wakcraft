package heero.mc.mod.wakcraft.network.packet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketHavenBagVisitors implements IMessage {
	// Client -> Server
	public static final int ACTION_ADD = 1;
	public static final int ACTION_REMOVE = 2;

	public int action;
	public String playerName;
	public int right;

	public PacketHavenBagVisitors() {
	}

	public PacketHavenBagVisitors(int action, String playerName, int right) {
		this.action = action;
		this.playerName = playerName;
		this.right = right;
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.action = buffer.readInt();
		this.right = buffer.readInt();
		this.playerName = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(action);
		buffer.writeInt(right);
		ByteBufUtils.writeUTF8String(buffer, playerName);
	}
}