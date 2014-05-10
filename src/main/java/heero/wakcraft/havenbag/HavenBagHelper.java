package heero.wakcraft.havenbag;

import heero.wakcraft.WakcraftConfig;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.tileentity.TileEntityHavenBagProperties;
import heero.wakcraft.world.TeleporterHavenBag;
import heero.wakcraft.world.WorldProviderHaveBag;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;

public class HavenBagHelper {
	public static final int R_GARDEN = 1;
	public static final int R_CRAFT = 2;
	public static final int R_DECO = 4;
	public static final int R_MERCHANT = 8;

	public static int getNextAvailableUID(World havenBagWorld) {
		if (havenBagWorld.provider.dimensionId != WakcraftConfig.havenBagDimensionId) {
			FMLLog.warning("The received world is not the %s world : %s", WorldProviderHaveBag.NAME, havenBagWorld.provider.getDimensionName());

			return 0;
		}

		int uid = 0;

		while (true) {
			int[] coords = getCoordFromUID(++uid);
			if (havenBagWorld.getBlock(coords[0], coords[1] - 1, coords[2] + 7).equals(Blocks.air)) {
				break;
			}
		}

		return uid;
	}

	public static int[] getCoordFromUID(int uid) {
		return new int[] { (uid % 16) * 32, 20, (uid / 16) * 32 };
	}

	public static int getUIDFromCoord(int x, int y, int z) {
		return (z / 32) * 16 + (x / 32);
	}

	public static TileEntityHavenBagProperties getHavenBagProperties(World havenBagWorld, int uid) {
		if (havenBagWorld.provider.dimensionId != WakcraftConfig.havenBagDimensionId) {
			FMLLog.warning("The received world is not the %s world : %s", WorldProviderHaveBag.NAME, havenBagWorld.provider.getDimensionName());

			return null;
		}

		int[] coords = getCoordFromUID(uid);

		TileEntity tileEntity = havenBagWorld.getTileEntity(coords[0], coords[1], coords[2]);
		if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBagProperties)) {
			FMLLog.warning("Error while loading tile entity haven bag properties (%d, %d, %d)", coords[0], coords[1], coords[2]);
			return null;
		}

		return (TileEntityHavenBagProperties) tileEntity;
	}

	public static void teleportPlayerToHavenBag(EntityPlayerMP player, int havenBagUID) {
		HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading havenbag player (%s) properties", player.getDisplayName());
			return;
		}

		properties.setEnterHavenBag(player.posX, player.posY, player.posZ, havenBagUID);
		
		player.mcServer.getConfigurationManager().transferPlayerToDimension(player, WakcraftConfig.havenBagDimensionId, new TeleporterHavenBag(MinecraftServer.getServer().worldServerForDimension(WakcraftConfig.havenBagDimensionId), havenBagUID));
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
