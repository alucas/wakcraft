package heero.mc.mod.wakcraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;

public abstract class BlockOverSlab extends BlockGeneric implements IOverSlab {
    public static final IUnlistedProperty<Integer> PROP_BOTTOM_POSITION = new Properties.PropertyAdapter<>(PropertyInteger.create("bottom_position", 0, 3));

    public BlockOverSlab(final Material material, final CreativeTabs tab) {
        super(material, tab);

        setFullCube(false);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    protected BlockState createBlockState() {
        return createBlockState(this);
    }

    protected BlockState createBlockState(final Block block) {
        final IProperty[] properties = new IProperty[]{};
        final IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[]{PROP_BOTTOM_POSITION};

        return new ExtendedBlockState(block, properties, unlistedProperties);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (!(state instanceof IExtendedBlockState)) {
            return state;
        }

        final IExtendedBlockState extendedState = (IExtendedBlockState) state;
        final IBlockState stateDown = world.getBlockState(pos.down());
        if (!(stateDown.getBlock() instanceof BlockSlab)) {
            return extendedState.withProperty(PROP_BOTTOM_POSITION, 0);
        }

        return extendedState.withProperty(PROP_BOTTOM_POSITION, 4 - BlockSlab.getTopPosition(stateDown));
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
        final IBlockState state = getExtendedState(worldIn.getBlockState(pos), worldIn, pos);
        if (!(state instanceof IExtendedBlockState)) {
            return;
        }

        final IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
        final int bottomPosition = extendedBlockState.getValue(BlockOverSlab.PROP_BOTTOM_POSITION);

        this.setBlockBounds(0, (bottomPosition * -0.25F), 0, 1, (bottomPosition * -0.25F) + getBlockHeight(worldIn, pos), 1);
    }

    public abstract float getBlockHeight(final IBlockAccess worldIn, final BlockPos pos);
}
