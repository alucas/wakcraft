package heero.mc.mod.wakcraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockContainerOverSlab extends BlockContainerGeneric implements IOverSlab {
    public static final PropertyInteger PROP_BOTTOM_POSITION = PropertyInteger.create("bottom_position", 0, 3);


    public BlockContainerOverSlab(final Material material, final CreativeTabs tab) {
        super(material, tab);

        setRenderType(3);
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

    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
        IBlockState blockStateDown = worldIn.getBlockState(pos.down());
        if (!(blockStateDown.getBlock() instanceof BlockSlab)) {
            super.setBlockBounds(0, 0, 0, 1, 1, 1);
            return;
        }

        switch (BlockSlab.getTopPosition(blockStateDown)) {
            case 1:
                this.setBlockBounds(0, -0.75F, 0, 1, 0.25F, 1);
                break;
            case 2:
                this.setBlockBounds(0, -0.5F, 0, 1, 0.5F, 1);
                break;
            case 3:
                this.setBlockBounds(0, -0.25F, 0, 1, 0.75F, 1);
                break;
            default:
                this.setBlockBounds(0, 0, 0, 1, 1, 1);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
    }
}
