package heero.wakcraft.havenbag;

import heero.wakcraft.WakcraftBlocks;
import heero.wakcraft.WakcraftItems;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.item.ItemIkiakit;
import heero.wakcraft.tileentity.TileEntityHavenBag;
import heero.wakcraft.tileentity.TileEntityHavenBagProperties;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;

public class HavenBagGenerationHelper {
	public static void generateHavenBag(World world, int uid) {
		int[] coords = HavenBagHelper.getCoordFromUID(uid);

		int x = coords[0];
		int y = coords[1];
		int z = coords[2];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				world.setBlock(x + 2 + i, y - 1, z - 6 + j, Blocks.stone);
			}
		}

		for (int i = 0; i < 2; i++) {
			world.setBlock(x + 3 + i, y - 1, z - 2, WakcraftBlocks.hbBridge, 8, 2);
		}

		for (int i = 0; i < 2; i++) {
			world.setBlock(x + i, y - 1, z + 1, Blocks.stone, 12, 2);
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				world.setBlock(x + 2 + i, y - 1, z - 1 + j, Blocks.planks);
			}
		}

		world.setBlock(x, y, z - 1, WakcraftBlocks.hbProperties, 0, 2);
		world.setBlock(x + 2, y, z - 3, WakcraftBlocks.hbChest, 0, 2);
		world.setBlock(x + 2, y, z - 6, WakcraftBlocks.hbLock, 0, 2);
		world.setBlock(x + 3, y, z - 6, WakcraftBlocks.hbVisitors, 0, 2);
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
