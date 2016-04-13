package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.block.BlockZaap;
import heero.mc.mod.wakcraft.network.packet.PacketZaapTeleportation;
import heero.mc.mod.wakcraft.util.ZaapUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerServerZaapTeleportation implements IMessageHandler<PacketZaapTeleportation, IMessage> {
    @Override
    public IMessage onMessage(final PacketZaapTeleportation message, final MessageContext ctx) {
        final IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
        mainThread.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                onMessageHandler(message, ctx);
            }
        });

        return null;
    }

    protected IMessage onMessageHandler(final PacketZaapTeleportation message, final MessageContext ctx) {
        final EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        final World world = player.getEntityWorld();
        final BlockPos zaapPos = message.zaapPos;

        if (!ZaapUtil.getZaaps(player, world).contains(zaapPos)) {
            WLog.warning("The player " + player.getName() + " doesn't know the Zaap " + zaapPos);
            return null;
        }

        if (!(world.getBlockState(zaapPos).getBlock() instanceof BlockZaap)) {
            WLog.warning("There is no Zaap at " + zaapPos);
            return null;
        }

        ZaapUtil.teleportPlayerToZaap(player, zaapPos);

        return null;
    }
}