package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightStop;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientFightStop implements IMessageHandler<PacketFightStop, IMessage> {
    @Override
    public IMessage onMessage(final PacketFightStop message, final MessageContext ctx) {
        IThreadListener mainThread = Minecraft.getMinecraft();
        mainThread.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                onMessageHandler(message, ctx);
            }
        });

        return null;
    }

    public void onMessageHandler(final PacketFightStop message, final MessageContext ctx) {
        FightManager.INSTANCE.stopFight(Wakcraft.proxy.getClientWorld(), message.getFightId());
    }
}
