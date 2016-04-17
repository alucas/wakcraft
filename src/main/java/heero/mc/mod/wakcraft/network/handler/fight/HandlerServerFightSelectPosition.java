package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.network.packet.fight.PacketFightSelectPosition;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerServerFightSelectPosition implements IMessageHandler<PacketFightSelectPosition, IMessage> {
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
        EntityPlayer player = ctx.getServerHandler().playerEntity;

        if (!FightUtil.isFighter(player)) {
            throw new RuntimeException("The entity " + player + " is not a valid fighter");
        }

        FightUtil.selectPosition(player, message.selectedPosition);
    }
}
