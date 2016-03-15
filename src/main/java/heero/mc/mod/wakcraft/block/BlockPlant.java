package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPlant extends BlockGeneric {
    public static final IProperty PRO_BOTTOM_POSITION = PropertyInteger.create("propertyBottomPosition", 0, 3);

    public BlockPlant(Material material) {
        super(material, WCreativeTabs.tabBlock);

        setOpaque(false);
        setFull(false);
        setLayer(EnumWorldBlockLayer.CUTOUT);
        setDefaultState(blockState.getBaseState().withProperty(PRO_BOTTOM_POSITION, 0));
    }

    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, PRO_BOTTOM_POSITION);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState stateDown = worldIn.getBlockState(pos.down());
        if (!(stateDown.getBlock() instanceof BlockSlab)) {
            return state.withProperty(PRO_BOTTOM_POSITION, 0);
        }

        return state.withProperty(PRO_BOTTOM_POSITION, 3 - BlockSlab.getTopPosition(stateDown));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
}
