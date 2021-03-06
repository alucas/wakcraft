package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightSelectPosition;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientFightSelectPosition implements IMessageHandler<PacketFightSelectPosition, IMessage> {
    @Override
    public IMessage onMessage(final PacketFightSelectPosition message, final MessageContext ctx) {
        IThreadListener mainThread = Minecraft.getMinecraft();
        mainThread.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                onMessageHandler(message, ctx);
            }
        });

        return null;
    }

    public void onMessageHandler(final PacketFightSelectPosition message, final MessageContext ctx) {
        Entity entity = Wakcraft.proxy.getClientWorld().getEntityByID(message.fighterId);
        if (entity == null) {
            throw new RuntimeException("No entity found for id " + message.fighterId);
        }

        if (!FightUtil.isFighter(entity)) {
            throw new RuntimeException("The entity " + entity + " is not a valid fighter");
        }

        FightUtil.setStartPosition(entity, message.selectedPosition);
    }
}
