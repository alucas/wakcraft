package heero.mc.mod.wakcraft.block;

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
        super(material);

        setOpaque(false);
        setFull(false);
        setLayer(EnumWorldBlockLayer.CUTOUT);
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
        IBlockState stateDown = worldIn.getBlockState(pos.offsetSouth());
        if (!(stateDown.getBlock() instanceof BlockSlab)) {
            return state;
        }

        return state.withProperty(PRO_BOTTOM_POSITION, 4 - BlockSlab.getTopPosition(stateDown));
    }
}
