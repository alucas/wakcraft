package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.havenbag.HavenBagsManager;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientHavenBagProperties implements IMessageHandler<PacketHavenBagProperties, IMessage> {
    @Override
    public IMessage onMessage(final PacketHavenBagProperties message, final MessageContext ctx) {
        IThreadListener mainThread = Minecraft.getMinecraft();
        mainThread.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                onMessageHandler(message, ctx);
            }
        });

        return null;
    }

    protected IMessage onMessageHandler(final PacketHavenBagProperties message, final MessageContext ctx) {
        HavenBagsManager.setHavenBagNBT(message.tagHavenBag);

        return null;
    }
}