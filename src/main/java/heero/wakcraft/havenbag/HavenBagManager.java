package heero.wakcraft.havenbag;

import heero.wakcraft.WakcraftBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class HavenBagManager {
	public static int getNextAvailableUID(World world) {
		int uid = 1;

		while (true) {
			int[] coords = getCoordFromUID(uid);
			if (world.getBlock(coords[0], coords[1], coords[2]).equals(Blocks.air)) {
				break;
			}

			uid++;
		}

		return uid + 1;
	}

	public static int[] getCoordFromUID(int uid) {
		return new int[] { 100000 + (uid % 10) * 30, 20, (uid / 10) * 30 };
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
			world.setBlock(x + i, y - 1, z, Blocks.stone_slab, 12, 2);
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				world.setBlock(x + 2 + i, y - 1, z - 1 + j, Blocks.planks);
			}
		}

		world.setBlock(x + 2, y, z - 3, Blocks.chest, 0, 2);
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
}
