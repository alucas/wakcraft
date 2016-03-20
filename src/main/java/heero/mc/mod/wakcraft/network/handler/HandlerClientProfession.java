package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.network.packet.PacketProfession;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientProfession implements IMessageHandler<PacketProfession, IMessage> {
    @Override
    public IMessage onMessage(final PacketProfession message, final MessageContext ctx) {
        IThreadListener mainThread = Minecraft.getMinecraft();
        mainThread.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                onMessageHandler(message, ctx);
            }
        });

        return null;
    }

    protected void onMessageHandler(final PacketProfession message, final MessageContext ctx) {
        EntityPlayer player = Wakcraft.proxy.getClientPlayer();

        for (PROFESSION profession : message.xps.keySet()) {
            ProfessionManager.setXp(player, profession, message.xps.get(profession));
        }
    }
}