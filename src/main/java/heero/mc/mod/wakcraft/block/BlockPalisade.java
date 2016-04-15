package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockPalisade extends BlockGenericTransparent {
    public static final IProperty PROP_CORNER = PropertyBool.create("propertyCorner");

    public BlockPalisade(Material material) {
        super(material, WCreativeTabs.tabBlock);

        setDefaultState(blockState.getBaseState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing yRotation = state.getValue(RotationUtil.PROP_Y_ROTATION);

        return RotationUtil.getMetadataFromYRotation(yRotation);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing yRotation = RotationUtil.getYRotationFromMetadata(meta);

        return getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, yRotation);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, RotationUtil.PROP_Y_ROTATION, PROP_CORNER);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
        super.setBlockBoundsBasedOnState(world, pos);

        IBlockState state = world.getBlockState(pos);
        IBlockState stateE = world.getBlockState(pos.east());
        IBlockState stateW = world.getBlockState(pos.west());
        IBlockState stateN = world.getBlockState(pos.north());
        IBlockState stateS = world.getBlockState(pos.south());

        Block block = state.getBlock();
        boolean tE = stateE.getBlock() == block;
        boolean tW = stateW.getBlock() == block;
        boolean tN = stateN.getBlock() == block;
        boolean tS = stateS.getBlock() == block;

        EnumFacing f = RotationUtil.getYRotationFromState(state);
        EnumFacing fE = RotationUtil.getYRotationFromState(stateE);
        EnumFacing fW = RotationUtil.getYRotationFromState(stateW);
        EnumFacing fN = RotationUtil.getYRotationFromState(stateN);
        EnumFacing fS = RotationUtil.getYRotationFromState(stateS);

        if ((tN && f == EnumFacing.SOUTH && fN == EnumFacing.NORTH)
                || (tE && f == EnumFacing.WEST && fE == EnumFacing.NORTH)
                || (tS && f == EnumFacing.EAST && fS == EnumFacing.SOUTH)
                || (tW && f == EnumFacing.NORTH && fW == EnumFacing.SOUTH)
                || (tN && f == EnumFacing.SOUTH && fN == EnumFacing.EAST)
                || (tE && f == EnumFacing.WEST && fE == EnumFacing.EAST)
                || (tS && f == EnumFacing.EAST && fS == EnumFacing.WEST)
                || (tW && f == EnumFacing.NORTH && fW == EnumFacing.WEST)) {
            block.setBlockBounds(0, 0, 0, 1, 1, 1);
        } else {
            switch (f) {
                case NORTH:
                    block.setBlockBounds(0, 0, 0, 1, 1, 0.1875f);
                    break;
                case SOUTH:
                    block.setBlockBounds(0, 0, 0.8125f, 1, 1, 1);
                    break;
                case WEST:
                    block.setBlockBounds(0, 0, 0, 0.1875f, 1, 1);
                    break;
                default:
                    block.setBlockBounds(0.8125f, 0, 0, 1, 1, 1);
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);

        RotationUtil.setYRotationFromYaw(world, pos, state, placer.rotationYaw);
    }

    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add
     * boxes to the list if they intersect the mask.) Parameters: World, X, Y,
     * Z, mask, list, colliding entity
     */
    @Override
    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity entity) {
        setBlockBoundsBasedOnState(world, pos);

        super.addCollisionBoxesToList(world, pos, state, mask, list, entity);
    }
}
