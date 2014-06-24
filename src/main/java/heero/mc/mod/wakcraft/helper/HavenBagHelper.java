package heero.mc.mod.wakcraft.helper;

import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.entity.property.HavenBagProperty;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagProperties;
import heero.mc.mod.wakcraft.world.TeleporterHavenBag;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLLog;

public class HavenBagHelper {
	public static final int R_GARDEN = 1;
	public static final int R_CRAFT = 2;
	public static final int R_DECO = 4;
	public static final int R_MERCHANT = 8;

	public static int[] getCoordFromUID(int uid) {
		return new int[] { (uid % 16) * 32, 20, (uid / 16) * 32 };
	}

	public static int getUIDFromCoord(int x, int y, int z) {
		return (z / 32) * 16 + (x / 32);
	}

	public static void teleportPlayerToHavenBag(EntityPlayerMP player, int havenBagUID) {
		HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading havenbag player (%s) properties", player.getDisplayName());
			return;
		}

		properties.setEnterHavenBag(player.posX, player.posY, player.posZ, havenBagUID);

		player.mcServer.getConfigurationManager().transferPlayerToDimension(player, WConfig.HAVENBAG_DIMENSION_ID, new TeleporterHavenBag(MinecraftServer.getServer().worldServerForDimension(WConfig.HAVENBAG_DIMENSION_ID), havenBagUID));

		Wakcraft.packetPipeline.sendTo(new PacketHavenBagProperties(properties.getUID()), player);
	}

	public static void leaveHavenBag(EntityPlayerMP player) {
		HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading havenbag player (%s) properties", player.getDisplayName());
			return;
		}

		player.mcServer.getConfigurationManager().transferPlayerToDimension(player, 0, new TeleporterHavenBag(MinecraftServer.getServer().worldServerForDimension(0), properties));

		properties.setLeaveHavenBag();
	}
}
