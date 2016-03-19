package heero.mc.mod.wakcraft.havenbag;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.util.HavenBagUtil;
import heero.mc.mod.wakcraft.util.WorldUtil;
import heero.mc.mod.wakcraft.world.WorldProviderHavenBag;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class HavenBagGenerationHelper {
    protected static final int HB_HEIGHT = 4;
    protected static final int HB_WIDTH = 21;
    protected static final int HB_LENGTH = 24;

    public static boolean generateHavenBag(World world, int uid) {
        if (!isHavenBagWorld(world)) {
            return false;
        }

        int[] coords = HavenBagUtil.getCoordFromUID(uid);

        int x = coords[0];
        int y = coords[1];
        int z = coords[2];

        // Start bridge
        world.setBlockState(new BlockPos(x + 0, y - 1, z + 7), WBlocks.hbCraft2.getDefaultState());
        world.setBlockState(new BlockPos(x + 1, y - 1, z + 7), WBlocks.hbCraft.getDefaultState());
        world.setBlockState(new BlockPos(x + 1, y + 0, z + 7), WBlocks.hbBarrier.getDefaultState());
        world.setBlockState(new BlockPos(x + 1, y + 1, z + 7), WBlocks.hbBarrier.getDefaultState());
        world.setBlockState(new BlockPos(x + 1, y + 2, z + 7), WBlocks.hbBarrier.getDefaultState());

        // Console blocks ground
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Block block = getRandomHBBlock(WItems.craftHG);
                world.setBlockState(new BlockPos(x + 2 + i, y - 1, z + j), block.getDefaultState());
            }
        }

        // Console blocks
        world.setBlockState(new BlockPos(x + 2, y, z), WBlocks.hbLock.getDefaultState());
        world.setBlockState(new BlockPos(x + 3, y, z), WBlocks.hbVisitors.getDefaultState());
        world.setBlockState(new BlockPos(x + 4, y, z), WBlocks.hbGemWorkbench.getDefaultState());
        world.setBlockState(new BlockPos(x + 2, y, z + 3), WBlocks.hbChest.getDefaultState());

        // Console blocks bridge
        for (int i = 0; i < 2; i++) {
            world.setBlockState(new BlockPos(x + 3 + i, y - 1, z + 4), WBlocks.hbBridge.getStateFromMeta(8));
        }

        // First gem ground
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Block block = getRandomHBBlock(WItems.merchantHG);
                world.setBlockState(new BlockPos(x + 2 + i, y - 1, z + 5 + j), block.getDefaultState());
            }
        }

        generateWalls(world, x - 1, y - 1, z - 1, WBlocks.invisibleWall, 0);

        return true;
    }

    protected static void generateWalls(World world, int x, int y, int z, Block block, int metadata) {
        for (int i = 0; i < HB_WIDTH; i++) {
            for (int j = 0; j < HB_LENGTH; j++) {
                BlockPos pos = new BlockPos(x + i, y, z + j);
                if (world.getBlockState(pos).getBlock().isAir(world, pos)) {
                    for (int k = 1; k < HB_HEIGHT; k++) {
                        world.setBlockState(pos.up(k), block.getStateFromMeta(metadata));
                    }
                }
            }
        }
    }

    public static void updateGem(World world, int uid, ItemStack stack, int gemPosition) {
        if (!isHavenBagWorld(world)) {
            return;
        }

        int[] coords = HavenBagUtil.getCoordFromUID(uid);

        int x = coords[0];
        int y = coords[1];
        int z = coords[2];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if ((gemPosition % 2 == 1) && (i == 4 || j == 4) || (gemPosition % 2 == 0 && i != 4 && j != 4)) {
                    Block block = getRandomHBBlock(stack);
                    setHBBBlock(world,
                            x + 2 + i + ((gemPosition / 2) % 3) * 6,
                            y - 1,
                            z + 5 + j + (gemPosition / 6) * 6,
                            block,
                            0);
                }
            }
        }
    }

    public static void updateBridge(World world, int uid, IInventory slots) {
        if (!isHavenBagWorld(world)) {
            return;
        }

        int[] coords = HavenBagUtil.getCoordFromUID(uid);

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
                        Block block = getRandomHBBlock(stack1Lower);
                        setHBBBlock(world,
                                x + 2 + i + column * 6,
                                y - 1,
                                z + 4 + row * 6,
                                block,
                                0);
                    }
                } else if (stack1Lower == null || stack2Lower == null) {
                    Block block = getRandomHBBlock();
                    for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
                        for (int i = 0; i < 5; i++) {
                            setHBBBlock(world, x + 2 + i + column * 6, y - 1, z + 4 - j + row * 6, block, 0);
                        }
                    }
                } else {
                    for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
                        for (int i = 0; i < 5; i++) {
                            if (i == 1 || i == 2) {
                                setHBBBlock(world, x + 2 + i + column * 6, y - 1, z + 4 - j + row * 6, WBlocks.hbBridge, 0);
                            } else {
                                setHBBBlock(world, x + 2 + i + column * 6, y - 1, z + 4 - j + row * 6, WBlocks.invisibleWall, 0);
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
                        Block block = getRandomHBBlock(stack1Lower);
                        setHBBBlock(world, x + 1 + column * 6, y - 1, z + 5 + i + row * 6, block, 0);
                    }
                } else if (stack1Lower == null || stack2Lower == null) {
                    Block block = getRandomHBBlock();
                    for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
                        for (int i = 0; i < 5; i++) {
                            setHBBBlock(world, x + 1 - j + column * 6, y - 1, z + 5 + i + row * 6, block, 0);
                        }
                    }
                } else {
                    for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
                        for (int i = 0; i < 5; i++) {
                            if (i == 1 || i == 2) {
                                setHBBBlock(world, x + 1 - j + column * 6, y - 1, z + 5 + i + row * 6, WBlocks.hbBridge, 0);
                            } else {
                                setHBBBlock(world, x + 1 - j + column * 6, y - 1, z + 5 + i + row * 6, WBlocks.invisibleWall, 0);
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
                setHBBBlock(world, x + 7 + (i % 3) * 6, y - 1, z + 10 + (i / 3) * 6, getRandomHBBlock(slots.getStackInSlot(i * 2)), 0);
            } else {
                setHBBBlock(world, x + 7 + (i % 3) * 6, y - 1, z + 10 + (i / 3) * 6, WBlocks.invisibleWall, 0);
            }
        }
    }

    protected static Block getRandomHBBlock() {
        return WBlocks.invisibleWall;
    }

    protected static Block getRandomHBBlock(ItemStack stack) {
        return (stack == null) ? WBlocks.invisibleWall : getRandomHBBlock(stack.getItem());
    }

    protected static Block getRandomHBBlock(Item item) {
        return (item == null) ? WBlocks.invisibleWall
                : (item == WItems.craftHG) ? ((int) (Math.random() * 2)) == 0 ? WBlocks.hbCraft : WBlocks.hbCraft2
                : (item == WItems.merchantHG) ? WBlocks.hbMerchant
                : (item == WItems.decoHG) ? ((int) (Math.random() * 2)) == 0 ? WBlocks.hbDeco : WBlocks.hbDeco2
                : (item == WItems.gardenHG) ? WBlocks.hbGarden
                : Blocks.lapis_block;
    }

    private static void setHBBBlock(World world, int x, int y, int z, Block hbBlock, int metadata) {
        if (hbBlock.equals(WBlocks.invisibleWall)) {
            for (int i = 0; i < 4; i++) {
                world.setBlockState(new BlockPos(x, y + i, z), hbBlock.getDefaultState(), 2);
            }
        } else if (hbBlock.equals(WBlocks.hbBridge)) {
            world.setBlockState(new BlockPos(x, y, z), WBlocks.hbBridge.getDefaultState(), 2);

            for (int i = 1; i < 4; i++) {
                world.setBlockState(new BlockPos(x, y + i, z), WBlocks.hbBarrier.getDefaultState(), 2);
            }
        } else {
            world.setBlockState(new BlockPos(x, y, z), hbBlock.getStateFromMeta(metadata), 2);

            for (int i = 1; i < 4; i++) {
                world.setBlockState(new BlockPos(x, y + i, z), Blocks.air.getDefaultState(), 2);
            }
        }
    }

    private static boolean isHavenBagWorld(World world) {
        if (!WorldUtil.isHavenBagWorld(world)) {
            WLog.warning("The received world is not the {} world : {}", WorldProviderHavenBag.NAME, world.provider.getDimensionName());

            return false;
        }

        return true;
    }
}
