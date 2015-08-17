package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.block.BlockSlab;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This blocks represent a demi slab block (1/4 block)
 * The metadata is :
 * 2 lower bits : position (0, 1, 2, 3)
 * 2 higher bits : size (0 : 1/4, 1 : 1/2, 2 : 1/3)
 */
public class ItemBlockSlab extends ItemBlock {
    // Opaque version of the slab block
    protected final IBlockState blockStateOpaque;
    protected final BlockSlab blockSlab;

    public ItemBlockSlab(final Block block) {
        super(block);

        if (!(block instanceof BlockSlab)) {
            throw new IllegalArgumentException("The block " + block.getUnlocalizedName() + " must extends " + BlockSlab.class.getName());
        }

        this.blockSlab = (BlockSlab) block;
        this.blockStateOpaque = ((BlockSlab) block).getSubBlockState();
    }

    /**
     * Callback for item usage. If the item does something special on right
     * clicking, he will have one of those. Return True if something happen and
     * false if it don't. Args : stack, player, world, x, y, z, side, hitX,
     * hitY, hitZ
     */
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos blockPos, EnumFacing side, float hitX, float hitY, float hitZ) {
        BlockPos blockPosDown = blockPos.offsetDown();
        BlockPos blockPosUp = blockPos.offsetUp();

        IBlockState blockState = world.getBlockState(blockPos);

        switch (side) {
            case DOWN:
                if (blockState.getBlock() == this.blockSlab) {
                    int size = BlockSlab.getSize(blockState);
                    int bottom = BlockSlab.getBottomPosition(blockState);

                    if (bottom == 1 && size == 2) {
                        playSoundPlaced(world, blockPos);
                        world.setBlockState(blockPos, this.blockStateOpaque);
                        return true;
                    } else if (bottom > 0) {
                        playSoundPlaced(world, blockPos);
                        world.setBlockState(blockPos, BlockSlab.setSize(BlockSlab.setBottomPosition(blockState, bottom - 1), size + 1));
                        return true;
                    }
                }

                IBlockState blockStateDown = world.getBlockState(blockPosDown);
                if (blockStateDown.getBlock() == this.blockSlab) {
                    int size = BlockSlab.getSize(blockStateDown);
                    int bottom = BlockSlab.getBottomPosition(blockStateDown);
                    int top = bottom + size;

                    if (bottom == 0 && size == 2) {
                        playSoundPlaced(world, blockPosDown);
                        world.setBlockState(blockPosDown, this.blockStateOpaque);
                        return true;
                    } else if (top == 2) {
                        playSoundPlaced(world, blockPosDown);
                        world.setBlockState(blockPosDown, BlockSlab.setSize(blockStateDown, size + 1));
                        return true;
                    }
                } else if (blockStateDown.getBlock().isReplaceable(world, blockPosDown)) {
                    playSoundPlaced(world, blockPosDown);

                    IBlockState blockStateTmp = this.blockSlab.getDefaultState();
                    world.setBlockState(blockPosDown, BlockSlab.setBottomPosition(blockStateTmp, 3));
                    return true;
                }

                break;
            case UP:
                if (blockState.getBlock() == this.blockSlab) {
                    int size = BlockSlab.getSize(blockState);
                    int bottom = BlockSlab.getBottomPosition(blockState);
                    int top = bottom + size;

                    if (bottom == 0 && size == 2) {
                        playSoundPlaced(world, blockPos);
                        world.setBlockState(blockPos, this.blockStateOpaque);
                        return true;
                    } else if (size + bottom < 3) {
                        playSoundPlaced(world, blockPos);
                        world.setBlockState(blockPos, BlockSlab.setSize(blockState, size + 1));
                        return true;
                    }
                }

                IBlockState blockStateUp = world.getBlockState(blockPosUp);
                if (blockStateUp.getBlock() == this.blockSlab) {
                    int size = BlockSlab.getSize(blockStateUp);
                    int pos = BlockSlab.getBottomPosition(blockStateUp);

                    if (size == 2 && pos == 1) {
                        playSoundPlaced(world, blockPosUp);
                        world.setBlockState(blockPosUp, this.blockStateOpaque);
                        return true;
                    } else if (pos == 1) {
                        playSoundPlaced(world, blockPosUp);
                        world.setBlockState(blockPosUp, BlockSlab.setSize(blockStateUp, size + 1));
                        return true;
                    }
                }

                break;
            default: // NORTH, SOUTH, EAST, WEST
                int posX = blockPos.getX() + ((side == EnumFacing.EAST) ? 1 : (side == EnumFacing.WEST) ? -1 : 0);
                int posZ = blockPos.getZ() + ((side == EnumFacing.SOUTH) ? 1 : (side == EnumFacing.NORTH) ? -1 : 0);
                int sectionY = (int) (hitY * 4);

                BlockPos blockPosSide = new BlockPos(posX, blockPos.getY(), posZ);
                IBlockState blockStateSide = world.getBlockState(blockPosSide);
                if (blockStateSide.getBlock() == this.blockSlab) {
                    int size = BlockSlab.getSize(blockStateSide);
                    int bottom = BlockSlab.getBottomPosition(blockStateSide);
                    int top = size + bottom;

                    if (size == 2 && ((sectionY == 0 && bottom == 1) || (sectionY == 3 && bottom == 0))) {
                        playSoundPlaced(world, blockPosSide);
                        world.setBlockState(blockPosSide, this.blockStateOpaque);
                        return true;
                    }

                    if ((sectionY == 0 && bottom == 1) || (sectionY == 1 && bottom == 2) || (sectionY == 2 && bottom == 3)) {
                        playSoundPlaced(world, blockPosSide);
                        world.setBlockState(blockPosSide, BlockSlab.setSize(BlockSlab.setBottomPosition(blockStateSide, bottom - 1), size + 1));
                        return true;
                    }

                    if ((sectionY == 3 && top == 2) || (sectionY == 2 && top == 1) || (sectionY == 1 && top == 0)) {
                        playSoundPlaced(world, blockPosSide);
                        world.setBlockState(blockPosSide, BlockSlab.setSize(blockStateSide, size + 1));
                        return true;
                    }
                } else if (blockStateSide.getBlock().isReplaceable(world, blockPosSide)) {
                    playSoundPlaced(world, blockPosSide);

                    IBlockState blockStateTmp = this.blockSlab.getDefaultState();
                    world.setBlockState(blockPosSide, BlockSlab.setBottomPosition(blockStateTmp, sectionY));
                    return true;
                }
        }

        super.onItemUse(stack, player, world, blockPos, side, hitX, hitY, hitZ);

        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, EnumFacing side, EntityPlayer player, ItemStack itemStack) {
        return true;
//        final IBlockState blockState = world.getBlockState(blockPos);
//        if (!(blockState.getBlock() instanceof BlockSlab)) {
//            return true;
//        }
//
//        final int size = BlockSlab.getSize(blockState);
//        final int bottom = BlockSlab.getBottomPosition(blockState);
//        final int top = size + bottom;
//
//        final int posX = blockPos.getX() + ((side == EnumFacing.EAST) ? 1 : (side == EnumFacing.WEST) ? -1 : 0);
//        final int posZ = blockPos.getZ() + ((side == EnumFacing.SOUTH) ? 1 : (side == EnumFacing.NORTH) ? -1 : 0);
//
//        final IBlockState blockStateSide = world.getBlockState(new BlockPos(posX, blockPos.getY(), posZ));
//
//        final int posY = blockPos.getY()
//                + ((side == EnumFacing.UP && (top == 3 || blockStateSide.getBlock() != this.blockSlab)) ? 1
//                : (side == EnumFacing.DOWN && (bottom == 0 || blockStateSide.getBlock() != this.blockSlab)) ? -1
//                : 0);
//
//        final IBlockState blockStateSide2 = world.getBlockState(new BlockPos(posX, posY, posZ));
//        if (blockStateSide2.getBlock() == this.blockSlab) {
//            final int size2 = BlockSlab.getSize(blockStateSide2);
//            final int bottom2 = BlockSlab.getBottomPosition(blockStateSide2);
//            final int top2 = bottom + size;
//
//            if ((side == EnumFacing.DOWN && bottom2 > 0) || (side == EnumFacing.UP && top2 < 3) || size2 > 1) {
//                return true;
//            }
//        }
//
//        return super.canPlaceBlockOnSide(world, blockPos, side, player, itemStack);
    }

    public void playSoundPlaced(World world, BlockPos pos) {
        world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, this.blockSlab.stepSound.getPlaceSound(), (this.blockSlab.stepSound.getVolume() + 1.0F) / 2.0F, this.blockSlab.stepSound.getFrequency() * 0.8F);
    }
}
