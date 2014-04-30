package heero.wakcraft.havenbag;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class HavenBagManager {
	public static int getNextAvailableUID(World world) {
		int uid = 0;

		while (true) {
			int[] coords = getCoordFromUID(uid);
			if (world.getBlock(coords[0], coords[1], coords[2]).equals(
					Blocks.air)) {
				break;
			}

			uid++;
		}

		return uid + 1;
	}

	public static int[] getCoordFromUID(int uid) {
		return new int[] { 100000 + (uid % 10) * 30, 20, (uid / 10) * 30 };
	}

	public static void generateHavenBag(World worldObj, int uid) {

	}
}
