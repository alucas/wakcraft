package heero.mc.mod.wakcraft.network.packet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketOpenWindow implements IMessage {
	public static final int WINDOW_HB_VISITORS = 1;
	public static final int WINDOW_INVENTORY = 2;

	public int windowId;

	public PacketOpenWindow() {
	}

	public PacketOpenWindow(int windowId) {
		this.windowId = windowId;
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.windowId = buffer.readInt();
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(windowId);
	}
}