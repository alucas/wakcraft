package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.network.packet.PacketOpenWindow;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerServerOpenWindow implements IMessageHandler<PacketOpenWindow, IMessage> {
	@Override
	public IMessage onMessage(PacketOpenWindow message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;

		if (message.windowId == GuiId.INVENTORY) {
			player.openGui(Wakcraft.instance, GuiId.INVENTORY.ordinal(), player.worldObj, (int) Math.floor(player.posX), (int) Math.floor(player.posY), (int) Math.floor(player.posZ));
		} else {
			FMLLog.warning("Unknow window ID : %d", message.windowId);
		}

		return null;
	}
}