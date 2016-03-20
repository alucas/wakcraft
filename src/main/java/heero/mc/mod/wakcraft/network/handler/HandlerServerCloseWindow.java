package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.network.packet.PacketCloseWindow;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerServerCloseWindow implements IMessageHandler<PacketCloseWindow, IMessage> {
    @Override
    public IMessage onMessage(final PacketCloseWindow message, final MessageContext ctx) {
        IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
        mainThread.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                onMessageHandler(message, ctx);
            }
        });

        return null;
    }

    protected IMessage onMessageHandler(final PacketCloseWindow message, final MessageContext ctx) {
        if (message.windowId == PacketCloseWindow.WINDOW_HB_VISITORS) {
            return null;
        }

        WLog.warning("Unknow window ID : %d", message.windowId);

        return null;
    }
}