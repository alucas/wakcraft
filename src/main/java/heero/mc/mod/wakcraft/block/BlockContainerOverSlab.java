package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockContainerOverSlab extends BlockContainerGeneric implements IOverSlab {
    protected BlockOverSlab blockOverSlab;

    private static class BlockOverSlabWrapper extends BlockOverSlab {
        public final BlockContainerOverSlab container;

        public BlockOverSlabWrapper(final BlockContainerOverSlab container) {
            super(Material.ground, WCreativeTabs.tabOther);

            this.container = container;
        }

        @Override
        public float getBlockHeight(IBlockAccess worldIn, BlockPos pos) {
            return container.getBlockHeight(worldIn, pos);
        }
    }

    public BlockContainerOverSlab(final Material material, final CreativeTabs tab) {
        super(material, tab);

        setRenderType(3);
        setFullCube(false);
    }

    protected BlockOverSlab getBlockOverSlab() {
        if (blockOverSlab == null) {
            blockOverSlab = new BlockOverSlabWrapper(this);
        }

        return blockOverSlab;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    protected BlockState createBlockState() {
        return getBlockOverSlab().createBlockState(this);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return getBlockOverSlab().getMetaFromState(state);
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return getBlockOverSlab().getExtendedState(state, world, pos);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
        getBlockOverSlab().setBlockBoundsBasedOnState(worldIn, pos);

        final float minX = (float) getBlockOverSlab().getBlockBoundsMinX();
        final float minY = (float) getBlockOverSlab().getBlockBoundsMinY();
        final float minZ = (float) getBlockOverSlab().getBlockBoundsMinZ();
        final float maxX = (float) getBlockOverSlab().getBlockBoundsMaxX();
        final float maxY = (float) getBlockOverSlab().getBlockBoundsMaxY();
        final float maxZ = (float) getBlockOverSlab().getBlockBoundsMaxZ();

        this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public float getBlockHeight(final IBlockAccess worldIn, final BlockPos pos) {
        return 1;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
    }
}
