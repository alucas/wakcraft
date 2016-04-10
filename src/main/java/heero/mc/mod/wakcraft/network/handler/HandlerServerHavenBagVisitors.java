package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.entity.property.HavenBagProperty;
import heero.mc.mod.wakcraft.havenbag.HavenBagProperties;
import heero.mc.mod.wakcraft.havenbag.HavenBagsManager;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagProperties;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagVisitors;
import heero.mc.mod.wakcraft.util.HavenBagUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerServerHavenBagVisitors implements IMessageHandler<PacketHavenBagVisitors, IMessage> {
    @Override
    public IMessage onMessage(final PacketHavenBagVisitors message, final MessageContext ctx) {
        IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
        mainThread.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                onMessageHandler(message, ctx);
            }
        });

        return null;
    }

    protected IMessage onMessageHandler(final PacketHavenBagVisitors message, final MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;

        HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
        if (properties == null) {
            WLog.warning("Error while loading player (%s) havenbag properties", player.getDisplayName());
            return null;
        }

        int havenBagUid = HavenBagUtil.getUIDFromCoord(player.getPosition());
        if (properties.getPlayerHavenBagId() != havenBagUid) {
            WLog.warning("Player (%s) tried to update the permission of havenbag %d", player.getDisplayName(), havenBagUid);
            return null;
        }

        HavenBagProperties hbProperties = HavenBagsManager.getProperties(havenBagUid);
        if (hbProperties == null) {
            return null;
        }

        if (message.action == PacketHavenBagVisitors.ACTION_ADD) {
            hbProperties.setRight(message.playerName, hbProperties.getRight(message.playerName) | message.right);
            HavenBagsManager.setProperties(havenBagUid, hbProperties);
        } else if (message.action == PacketHavenBagVisitors.ACTION_REMOVE) {
            hbProperties.setRight(message.playerName, hbProperties.getRight(message.playerName) & ((~message.right) & 0xF));
            HavenBagsManager.setProperties(havenBagUid, hbProperties);
        } else {
            WLog.warning("Unknow action : %d", message.action);
            return null;
        }

        for (Object entity : player.worldObj.loadedEntityList) {
            if (entity instanceof EntityPlayerMP) {
                EntityPlayerMP playerMP = (EntityPlayerMP) entity;
                properties = (HavenBagProperty) playerMP.getExtendedProperties(HavenBagProperty.IDENTIFIER);
                if (properties == null) {
                    WLog.warning("Error while loading player (%s) havenbag properties", playerMP.getDisplayName());
                    continue;
                }

                if (properties.getCurrentHavenBagId() != havenBagUid) {
                    continue;
                }

                Wakcraft.packetPipeline.sendTo(new PacketHavenBagProperties(havenBagUid), playerMP);
            }
        }

        return null;
    }
}