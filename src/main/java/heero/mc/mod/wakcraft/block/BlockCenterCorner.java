package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BlockCenterCorner extends BlockYRotation {
    public static final IProperty PROP_CORNER = PropertyBool.create("propertyCorner");
    public static final IProperty PROP_CENTER = PropertyBool.create("propertyCenter");

    public BlockCenterCorner(Material material) {
        super(material);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, RotationUtil.PROP_Y_ROTATION, PROP_CENTER, PROP_CORNER);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        BlockPos posSouth = pos.offsetSouth();
        BlockPos posWest = pos.offsetWest();
        BlockPos posNorth = pos.offsetNorth();
        BlockPos posEast = pos.offsetEast();

        Block blockSouth = worldIn.getBlockState(posSouth).getBlock();
        Block blockWest = worldIn.getBlockState(posWest).getBlock();
        Block blockNorth = worldIn.getBlockState(posNorth).getBlock();
        Block blockEast = worldIn.getBlockState(posEast).getBlock();

        Boolean propertyCenterNew = blockSouth == this && blockWest == this && blockNorth == this && blockEast == this;

        state = state.withProperty(PROP_CENTER, propertyCenterNew);

        if (propertyCenterNew) {
            return state;
        }

        EnumFacing yRotation = (EnumFacing) state.getValue(RotationUtil.PROP_Y_ROTATION);
        Boolean propertyCornerSouth = yRotation == EnumFacing.SOUTH && blockSouth != this && blockWest != this && blockNorth == this && blockEast == this;
        Boolean propertyCornerWest = yRotation == EnumFacing.WEST && blockSouth == this && blockWest != this && blockNorth != this && blockEast == this;
        Boolean propertyCornerNorth = yRotation == EnumFacing.NORTH && blockSouth == this && blockWest == this && blockNorth != this && blockEast != this;
        Boolean propertyCornerEast = yRotation == EnumFacing.EAST && blockSouth != this && blockWest == this && blockNorth == this && blockEast != this;

        return state.withProperty(PROP_CORNER, propertyCornerSouth || propertyCornerWest || propertyCornerNorth || propertyCornerEast);
    }
}
