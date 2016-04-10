package heero.mc.mod.wakcraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public abstract class BlockOverSlab extends BlockGeneric implements IOverSlab {
    public static final PropertyInteger PROP_BOTTOM_POSITION = PropertyInteger.create("bottom_position", 0, 3);

    public BlockOverSlab(final Material material, final CreativeTabs tab) {
        super(material, tab);

        setFullCube(false);
        setDefaultState(blockState.getBaseState().withProperty(PROP_BOTTOM_POSITION, 0));
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    protected BlockState createBlockState() {
        IProperty[] properties = new IProperty[]{PROP_BOTTOM_POSITION};

        return new BlockState(this, properties);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IBlockState stateDown = world.getBlockState(pos.down());
        if (!(stateDown.getBlock() instanceof BlockSlab)) {
            return state.withProperty(PROP_BOTTOM_POSITION, 0);
        }

        return state.withProperty(PROP_BOTTOM_POSITION, 4 - BlockSlab.getTopPosition(stateDown));
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
        final IBlockState state = getActualState(worldIn.getBlockState(pos), worldIn, pos);
        final int bottomPosition = state.getValue(BlockOverSlab.PROP_BOTTOM_POSITION);

        this.setBlockBounds(0, (bottomPosition * -0.25F), 0, 1, (bottomPosition * -0.25F) + getBlockHeight(), 1);
    }

    public abstract float getBlockHeight();
}
