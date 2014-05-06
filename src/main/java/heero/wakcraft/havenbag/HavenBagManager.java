package heero.wakcraft.havenbag;

import heero.wakcraft.WakcraftBlocks;
import heero.wakcraft.WakcraftItems;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.item.ItemIkiakit;
import heero.wakcraft.tileentity.TileEntityHavenBagProperties;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;

public class HavenBagManager {
	public static final int CHEST_NORMAL = 0;
	public static final int CHEST_SMALL = 1;
	public static final int CHEST_ADVENTURER = 2;
	public static final int CHEST_KIT = 3;
	public static final int CHEST_COLLECTOR = 4;
	public static final int CHEST_GOLDEN = 5;
	public static final int CHEST_EMERALD = 6;

	public static final int[] CHESTS = new int[] { CHEST_NORMAL, CHEST_SMALL,
			CHEST_ADVENTURER, CHEST_KIT, CHEST_COLLECTOR, CHEST_GOLDEN,
			CHEST_EMERALD };
	private static final int[] CHESTS_SIZES = new int[] { 14, 14, 21, 21, 21,
			28, 28 };
	private static final Item[] CHESTS_IKIAKIT = new Item[] { null,
			WakcraftItems.smallikiakit, WakcraftItems.adventurerikiakit,
			WakcraftItems.kitikiakit, WakcraftItems.collectorikiakit,
			WakcraftItems.goldenikiakit, WakcraftItems.emeraldikiakit };

	public static int getNextAvailableUID(World world) {
		int uid = 0;

		while (true) {
			int[] coords = getCoordFromUID(++uid);
			if (world.getBlock(coords[0], coords[1] - 1, coords[2] + 1).equals(Blocks.air)) {
				break;
			}
		}

		return uid;
	}

	public static int[] getCoordFromUID(int uid) {
		return new int[] { 100000 + (uid % 10) * 30, 20, (uid / 10) * 30 };
	}

	public static int getUIDFromCoord(int x, int y, int z) {
		return ((z + 7) / 30) * 10 + ((x - 99999) / 30);
	}

	public static void generateHavenBag(World world, int uid) {
		int[] coords = getCoordFromUID(uid);

		int x = coords[0];
		int y = coords[1];
		int z = coords[2];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				world.setBlock(x + 2 + i, y - 1, z - 6 + j, Blocks.stone);
			}
		}

		for (int i = 0; i < 2; i++) {
			world.setBlock(x + 3 + i, y - 1, z - 2, Blocks.stone_slab, 8, 2);
		}

		for (int i = 0; i < 2; i++) {
			world.setBlock(x + i, y - 1, z + 1, Blocks.stone_slab, 12, 2);
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				world.setBlock(x + 2 + i, y - 1, z - 1 + j, Blocks.planks);
			}
		}

		world.setBlock(x, y, z - 1, WakcraftBlocks.havenbagproperties, 0, 2);
		world.setBlock(x + 2, y, z - 3, WakcraftBlocks.havenbagchest, 0, 2);
		world.setBlock(x + 2, y, z - 6, WakcraftBlocks.havenbaglock, 0, 2);
		world.setBlock(x + 4, y, z - 6, WakcraftBlocks.havenGemWorkbench, 0, 2);

		fillWalls(world, x - 1, y - 1, z - 7, 4, WakcraftBlocks.invisiblewall, 0);
	}

	private static void fillWalls(World world, int x, int y, int z, int nbLayer, Block block, int metadata) {
		for (int i = 0; i < 21; i++) {
			for (int j = 0; j < 24; j++) {
				if (world.getBlock(x + i, y, z + j) == Blocks.air) {
					for (int k = 0; k < 4; k++) {
						world.setBlock(x + i, y + k, z + j, block, metadata, 2);
					}
				}
			}
		}
	}

	public static TileEntityHavenBagProperties getHavenBagProperties(World world, int uid) {
		int[] coords = getCoordFromUID(uid);

		TileEntity tileEntity = world.getTileEntity(coords[0], coords[1], coords[2] - 1);
		if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBagProperties)) {
			FMLLog.warning("Error while loading tile entity haven bag properties (%d, %d, %d)", coords[0], coords[1], coords[2]);
			return null;
		}

		return (TileEntityHavenBagProperties) tileEntity;
	}

	public static void teleportPlayerToHavenBag(EntityPlayer player, int havenBagUID) {
		HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading player (%s) properties", player.getDisplayName());
			return;
		}

		properties.setEnterHavenBag(player.posX, player.posY, player.posZ, havenBagUID);

		int[] coords = HavenBagManager.getCoordFromUID(havenBagUID);
		player.rotationYaw = -90;
		player.rotationPitch = 0;
		player.setPositionAndUpdate(coords[0] + 0.5, coords[1], coords[2] + 1.5);
	}

	public static int getChestSize(int chestId) {
		return CHESTS_SIZES[chestId];
	}

	public static Item getChestIkiakit(int chestId) {
		return CHESTS_IKIAKIT[chestId];
	}

	public static int getChestIdFromIkiakit(Item ikiakit) {
		if (ikiakit instanceof ItemIkiakit) {
			return Arrays.asList(CHESTS_IKIAKIT).indexOf(ikiakit);
		}

		return 0;
	}
}
