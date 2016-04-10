package heero.mc.mod.wakcraft.util;

import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.entity.property.HavenBagProperty;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagProperties;
import heero.mc.mod.wakcraft.world.TeleporterHavenBag;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class HavenBagUtil {
    public static final int R_GARDEN = 1;
    public static final int R_CRAFT = 2;
    public static final int R_DECO = 4;
    public static final int R_MERCHANT = 8;

    public static int[] getCoordFromUID(int uid) {
        return new int[]{(uid % 16) * 32, 20, (uid / 16) * 32};
    }

    public static int getUIDFromCoord(BlockPos pos) {
        return (pos.getZ() / 32) * 16 + (pos.getX() / 32);
    }

    public static HavenBagProperty getHavenBagProperties(final EntityPlayerMP player) {
        return (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
    }

    public static void teleportPlayerToHavenBag(final EntityPlayerMP player, final int havenBagId, final BlockPos havenBagPos) {
        HavenBagProperty properties = getHavenBagProperties(player);
        if (properties == null) {
            WLog.warning("Error while loading havenbag player (%s) properties", player.getDisplayName());
            return;
        }

        properties.setEnterHavenBag(player.posX, player.posY, player.posZ, havenBagId, havenBagPos);

        player.mcServer.getConfigurationManager().transferPlayerToDimension(player, WConfig.getHavenBagDimensionId(), new TeleporterHavenBag(MinecraftServer.getServer().worldServerForDimension(WConfig.getHavenBagDimensionId()), havenBagId));

        Wakcraft.packetPipeline.sendTo(new PacketHavenBagProperties(properties.getPlayerHavenBagId()), player);
    }

    public static void leaveHavenBag(final EntityPlayerMP player) {
        HavenBagProperty properties = getHavenBagProperties(player);
        if (properties == null) {
            WLog.warning("Error while loading havenbag player (%s) properties", player.getDisplayName());
            return;
        }

        player.mcServer.getConfigurationManager().transferPlayerToDimension(player, 0, new TeleporterHavenBag(MinecraftServer.getServer().worldServerForDimension(0), properties));

        if (properties.getCurrentHavenBagId() == properties.getPlayerHavenBagId()) {
            MinecraftServer.getServer().worldServerForDimension(0).setBlockToAir(properties.getCurrentHavenBagPos());
        }

        properties.setLeaveHavenBag();
    }
}
