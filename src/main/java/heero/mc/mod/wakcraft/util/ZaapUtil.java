package heero.mc.mod.wakcraft.util;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.block.BlockZaap;
import heero.mc.mod.wakcraft.entity.property.ZaapProperty;
import heero.mc.mod.wakcraft.network.packet.PacketExtendedEntityProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class ZaapUtil {
    public static ZaapProperty getZaapProperties(final EntityPlayer player) {
        return (ZaapProperty) player.getExtendedProperties(ZaapProperty.IDENTIFIER);
    }

    public static void registerNewZaap(final EntityPlayer player, final World world, final BlockPos zaapPos) {
        final ZaapProperty properties = getZaapProperties(player);
        if (properties == null) {
            WLog.warning("Error while loading zaaps' player (%s) properties", player.getDisplayName());
            return;
        }

        properties.addZaap(world.provider.getDimensionId(), zaapPos);
    }

    public static Set<BlockPos> getZaaps(final EntityPlayer player, final World world) {
        final ZaapProperty properties = getZaapProperties(player);
        if (properties == null) {
            WLog.warning("Error while loading zaaps' player (%s) properties", player.getDisplayName());
            return null;
        }

        final int dimensionId = world.provider.getDimensionId();
        final Set<BlockPos> zaaps = properties.getZaaps(dimensionId);
        if (zaaps == null) {
            return new TreeSet<>();
        }

        if (world.isRemote) {
            return zaaps;
        }

        boolean zaapRemoved = false;

        final Iterator<BlockPos> zaapIterator = zaaps.iterator();
        while (zaapIterator.hasNext()) {
            final BlockPos zaap = zaapIterator.next();
            if (world.getBlockState(zaap).getBlock() instanceof BlockZaap) {
                continue;
            }

            WLog.info("Remove zaap " + zaap);
            zaapRemoved = true;

            zaapIterator.remove();
        }

        if (zaapRemoved) {
            properties.setZaaps(dimensionId, zaaps);

            Wakcraft.packetPipeline.sendTo(new PacketExtendedEntityProperty(player, ZaapProperty.IDENTIFIER), (EntityPlayerMP) player);
        }

        return zaaps;
    }

    public static void clear(final EntityPlayer player) {
        final ZaapProperty properties = getZaapProperties(player);
        if (properties == null) {
            WLog.warning("Error while loading zaaps' player (%s) properties", player.getDisplayName());
            return;
        }

        properties.clear();
    }

    public static void teleportPlayerToZaap(final EntityPlayerMP player, final BlockPos zaapPos) {
        player.setPositionAndUpdate(zaapPos.getX() + 0.5, zaapPos.getY() + 1, zaapPos.getZ() + 0.5);
    }
}
