package heero.mc.mod.wakcraft.havenbag;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.helper.HavenBagHelper;
import heero.mc.mod.wakcraft.world.WorldProviderHavenBag;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HavenBagGenerationHelper {
	protected static final int HB_HEIGHT = 4;
	protected static final int HB_WIDTH = 21;
	protected static final int HB_LENGTH = 24;

	public static boolean generateHavenBag(World havenBagWorld, int uid) {
		if (havenBagWorld.provider.dimensionId != WConfig.HAVENBAG_DIMENSION_ID) {
			WLog.warning("The received world is not the %s world : %s", WorldProviderHavenBag.NAME, havenBagWorld.provider.getDimensionName());

			return false;
		}

		int[] coords = HavenBagHelper.getCoordFromUID(uid);

		int x = coords[0];
		int y = coords[1];
		int z = coords[2];

		// Console blocks ground
		ItemStack stack = new ItemStack(WItems.craftHG);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Block block = getHBBlockFromStack(stack);
				havenBagWorld.setBlock(x + 2 + i, y - 1, z + j, block);
			}
		}

		// Console blocks bridge
		for (int i = 0; i < 2; i++) {
			havenBagWorld.setBlock(x + 3 + i, y - 1, z + 4, WBlocks.hbBridge, 8, 2);
		}

		// Start bridge
		havenBagWorld.setBlock(x + 0, y - 1, z + 7, WBlocks.hbCraft2);
		havenBagWorld.setBlock(x + 1, y - 1, z + 7, WBlocks.hbCraft);
		havenBagWorld.setBlock(x + 1, y + 0, z + 7, WBlocks.hbBarrier);
		havenBagWorld.setBlock(x + 1, y + 1, z + 7, WBlocks.hbBarrier);
		havenBagWorld.setBlock(x + 1, y + 2, z + 7, WBlocks.hbBarrier);

		// First gem ground
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				havenBagWorld.setBlock(x + 2 + i, y - 1, z + 5 + j, WBlocks.hbMerchant);
			}
		}

		generateWalls(havenBagWorld, x - 1, y - 1, z - 1, WBlocks.invisiblewall, 0);

		// Console blocks
		havenBagWorld.setBlock(x + 2, y, z, WBlocks.hbLock);
		havenBagWorld.setBlock(x + 3, y, z, WBlocks.hbVisitors);
		havenBagWorld.setBlock(x + 4, y, z, WBlocks.havenGemWorkbench);
		havenBagWorld.setBlock(x + 2, y, z + 3, WBlocks.hbChest);

		return true;
	}

	private static void generateWalls(World world, int x, int y, int z, Block block, int metadata) {
		for (int i = 0; i < HB_WIDTH; i++) {
			for (int j = 0; j < HB_LENGTH; j++) {
				if (world.getBlock(x + i, y, z + j).equals(Blocks.air)) {
					for (int k = 0; k < HB_HEIGHT; k++) {
						world.setBlock(x + i, y + k, z + j, block, metadata, 2);
					}
				}
			}
		}
	}

	public static void updateGem(World havenBagWorld, int uid, ItemStack stack, int gemPosition) {
		if (havenBagWorld.provider.dimensionId != WConfig.HAVENBAG_DIMENSION_ID) {
			WLog.warning("The received world is not the %s world : %s", WorldProviderHavenBag.NAME, havenBagWorld.provider.getDimensionName());

			return;
		}

		int[] coords = HavenBagHelper.getCoordFromUID(uid);

		int x = coords[0];
		int y = coords[1];
		int z = coords[2];

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if ((gemPosition % 2 == 1) && (i == 4 || j == 4) || (gemPosition % 2 == 0 && i != 4 && j != 4)) {
					Block block = getHBBlockFromStack(stack);
					setHBBBlock(havenBagWorld, x + 2 + i + ((gemPosition / 2) % 3) * 6, y - 1, z + 5 + j + (gemPosition / 6) * 6, block, 0);
				}
			}
		}
	}

	public static void updateBridge(World havenBagWorld, int uid, IInventory slots) {
		if (havenBagWorld.provider.dimensionId != WConfig.HAVENBAG_DIMENSION_ID) {
			WLog.warning("The received world is not the %s world : %s", WorldProviderHavenBag.NAME, havenBagWorld.provider.getDimensionName());

			return;
		}

		int[] coords = HavenBagHelper.getCoordFromUID(uid);

		int x = coords[0];
		int y = coords[1];
		int z = coords[2];

		// Vertical bridges
		for (int column = 2; column >= 0; --column) {
			for (int row = 2; row >= 1; --row) {
				ItemStack stack1Lower = slots.getStackInSlot((row * 3 + column) * 2);
				ItemStack stack1Upper = slots.getStackInSlot((row * 3 + column) * 2 + 1);
				ItemStack stack2Lower = slots.getStackInSlot(((row - 1) * 3 + column) * 2);
				ItemStack stack2Upper = slots.getStackInSlot(((row - 1) * 3 + column) * 2 + 1);

				if (stack1Upper != null && stack2Upper != null && stack1Upper.getItem().equals(stack2Upper.getItem())) {
					for (int i = 0; i < 5; i++) {
						Block block = getHBBlockFromStack(stack1Lower);
						setHBBBlock(havenBagWorld, x + 2 + i + column * 6, y - 1, z + 4 + row * 6, block, 0);
					}
				} else if (stack1Lower == null || stack2Lower == null) {
					Block block = getHBBlockFromStack(null);
					for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
						for (int i = 0; i < 5; i++) {
							setHBBBlock(havenBagWorld, x + 2 + i + column * 6, y - 1, z + 4 - j + row * 6, block, 0);
						}
					}
				} else {
					for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
						for (int i = 0; i < 5; i++) {
							if (i == 1 || i == 2) {
								setHBBBlock(havenBagWorld, x + 2 + i + column * 6, y - 1, z + 4 - j + row * 6, WBlocks.hbBridge, 0);
							} else {
								setHBBBlock(havenBagWorld, x + 2 + i + column * 6, y - 1, z + 4 - j + row * 6, WBlocks.invisiblewall, 0);
							}
						}
					}
				}
			}
		}

		// Horizontal bridges
		for (int column = 2; column >= 1; --column) {
			for (int row = 2; row >= 0; --row) {
				ItemStack stack1Lower = slots.getStackInSlot((row * 3 + column) * 2);
				ItemStack stack1Upper = slots.getStackInSlot((row * 3 + column) * 2 + 1);
				ItemStack stack2Lower = slots.getStackInSlot((row * 3 + (column - 1)) * 2);
				ItemStack stack2Upper = slots.getStackInSlot((row * 3 + (column - 1)) * 2 + 1);

				if (stack1Upper != null && stack2Upper != null && stack1Upper.getItem().equals(stack2Upper.getItem())) {
					for (int i = 0; i < 5; i++) {
						Block block = getHBBlockFromStack(stack1Lower);
						setHBBBlock(havenBagWorld, x + 1 + column * 6, y - 1, z + 5 + i + row * 6, block, 0);
					}
				} else if (stack1Lower == null || stack2Lower == null) {
					Block block = getHBBlockFromStack(null);
					for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
						for (int i = 0; i < 5; i++) {
							setHBBBlock(havenBagWorld, x + 1 - j + column * 6, y - 1, z + 5 + i + row * 6, block, 0);
						}
					}
				} else {
					for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
						for (int i = 0; i < 5; i++) {
							if (i == 1 || i == 2) {
								setHBBBlock(havenBagWorld, x + 1 - j + column * 6, y - 1, z + 5 + i + row * 6, WBlocks.hbBridge, 0);
							} else {
								setHBBBlock(havenBagWorld, x + 1 - j + column * 6, y - 1, z + 5 + i + row * 6, WBlocks.invisiblewall, 0);
							}
						}
					}
				}
			}
		}

		// 4 remaining block to update when 8 Gems are contiguous
		for (int i = 0; i < 5; i++) {
			if (i == 2) continue;

			ItemStack stack1 = slots.getStackInSlot(i * 2);
			ItemStack stack2 = slots.getStackInSlot((i + 1) * 2 + 1);
			ItemStack stack3 = slots.getStackInSlot((i + 3) * 2 + 1);
			ItemStack stack4 = slots.getStackInSlot((i + 4) * 2 + 1);
			if (stack1 != null && stack2 != null && stack3 != null && stack4 != null && stack1.getItem().equals(stack2.getItem()) && stack1.getItem().equals(stack3.getItem()) && stack1.getItem().equals(stack4.getItem())) {
				setHBBBlock(havenBagWorld, x + 7 + (i % 3) * 6, y - 1, z + 10 + (i / 3) * 6, getHBBlockFromStack(slots.getStackInSlot(i * 2)), 0);
			} else {
				setHBBBlock(havenBagWorld, x + 7 + (i % 3) * 6, y - 1, z + 10 + (i / 3) * 6, WBlocks.invisiblewall, 0);
			}
		}
	}

	private static Block getHBBlockFromStack(ItemStack stack) {
		return (stack == null) ? WBlocks.invisiblewall
				: (stack.getItem() == WItems.craftHG) ? ((int)(Math.random() * 2)) == 0 ? WBlocks.hbCraft : WBlocks.hbCraft2
						: (stack.getItem() == WItems.merchantHG) ? WBlocks.hbMerchant
								: (stack.getItem() == WItems.decoHG) ? ((int)(Math.random() * 2)) == 0 ? WBlocks.hbDeco : WBlocks.hbDeco2
										: (stack.getItem() == WItems.gardenHG) ? WBlocks.hbGarden
												: Blocks.lapis_block;
	}

	private static void setHBBBlock(World world, int x, int y, int z, Block hbBlock, int metadata) {
		if (hbBlock.equals(WBlocks.invisiblewall)) {
			for (int i = 0; i < 4; i++) {
				world.setBlock(x, y + i, z, hbBlock, 0, 2);
			}
		} else if (hbBlock.equals(WBlocks.hbBridge)) {
			world.setBlock(x, y, z, WBlocks.hbBridge, 0, 2);

			for (int i = 1; i < 4; i++) {
				world.setBlock(x, y + i, z, WBlocks.hbBarrier, 0, 2);
			}
		} else {
			world.setBlock(x, y, z, hbBlock, metadata, 2);

			for (int i = 1; i < 4; i++) {
				world.setBlock(x, y + i, z, Blocks.air, 0, 2);
			}
		}
	}
}
