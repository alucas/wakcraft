package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.network.packet.PacketOpenWindow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientOpenWindow implements IMessageHandler<PacketOpenWindow, IMessage> {
    @Override
    public IMessage onMessage(PacketOpenWindow message, MessageContext ctx) {
        EntityPlayer player = Wakcraft.proxy.getClientPlayer();

        if (message.windowId == GuiId.HAVEN_BAG_VISITORS) {
            Wakcraft.proxy.openHBVisitorsGui(player);
        } else {
            WLog.warning("Unknow window ID : %d", message.windowId);
        }

        return null;
    }
}