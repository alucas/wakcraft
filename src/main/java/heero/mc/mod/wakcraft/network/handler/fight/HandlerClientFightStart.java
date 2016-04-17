package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFight;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightStart;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientFightStart implements IMessageHandler<PacketFightStart, IMessage> {
    @Override
    public IMessage onMessage(final PacketFightStart message, final MessageContext ctx) {
        IThreadListener mainThread = Minecraft.getMinecraft();
        mainThread.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                onMessageHandler(message, ctx);
            }
        });

        return null;
    }

    public void onMessageHandler(final PacketFightStart message, final MessageContext ctx) {
        World world = Wakcraft.proxy.getClientWorld();

        FightManager.INSTANCE.startClientFight(world, message.getFightId(), PacketFight.getEntities(world, message.fightersId), message.startPositions);
    }
}
