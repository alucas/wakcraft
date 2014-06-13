package heero.mc.mod.wakcraft.network.packet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketCloseWindow implements IMessage {
	public static final int WINDOW_HB_VISITORS = 1;

	public int windowId;

	public PacketCloseWindow() {
	}

	public PacketCloseWindow(int windowId) {
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