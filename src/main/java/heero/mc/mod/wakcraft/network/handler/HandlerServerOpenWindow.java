package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.network.GuiHandler;
import heero.mc.mod.wakcraft.network.packet.PacketOpenWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerServerOpenWindow implements IMessageHandler<PacketOpenWindow, IMessage> {
	@Override
	public IMessage onMessage(PacketOpenWindow message, MessageContext ctx) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		if (message.windowId == PacketOpenWindow.WINDOW_INVENTORY) {
			player.openGui(Wakcraft.instance, GuiHandler.GUI_INVENTORY, player.worldObj, (int) Math.floor(player.posX), (int) Math.floor(player.posY), (int) Math.floor(player.posZ));
		} else {
			FMLLog.warning("Unknow window ID : %d", message.windowId);
		}

		return null;
	}
}