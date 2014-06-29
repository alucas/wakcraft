package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.network.packet.PacketCloseWindow;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerServerCloseWindow implements IMessageHandler<PacketCloseWindow, IMessage> {
	@Override
	public IMessage onMessage(PacketCloseWindow message, MessageContext ctx) {
		if (message.windowId == PacketCloseWindow.WINDOW_HB_VISITORS) {
			return null;
		}

		WLog.warning("Unknow window ID : %d", message.windowId);

		return null;
	}
}