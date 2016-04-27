package heero.mc.mod.wakcraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockGrass extends BlockGeneric {
    public static final PropertyBool PROP_COVERED = PropertyBool.create("covered");

    public BlockGrass() {
        super(Material.grass);
    }

    @Override
    protected BlockState createBlockState() {
        final IProperty[] properties = new IProperty[]{PROP_COVERED};

        return new BlockState(this, properties);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        final BlockPos posUp = pos.up();
        final Block blockUp = world.getBlockState(posUp).getBlock();

        return state.withProperty(PROP_COVERED, blockUp instanceof BlockSlab || blockUp.isOpaqueCube());
    }
}
