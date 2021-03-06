package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.material.Material;
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
    public static final PropertyBool PROP_CORNER_LEFT = PropertyBool.create("corner_left");
    public static final PropertyBool PROP_CORNER_RIGHT = PropertyBool.create("corner_right");

    public BlockPalisade(Material material) {
        super(material, WCreativeTabs.tabBlock);

        setFullCube(false);

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
        return new BlockState(this, RotationUtil.PROP_Y_ROTATION, PROP_CORNER_LEFT, PROP_CORNER_RIGHT);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        final EnumFacing face = state.getValue(RotationUtil.PROP_Y_ROTATION);
        final BlockPos posFront = pos.offset(face.getOpposite());
        final BlockPos posFrontLeft = posFront.offset(face.rotateY());
        final BlockPos posFrontRight = posFront.offset(face.rotateYCCW());

        final IBlockState stateFront = worldIn.getBlockState(posFront);
        final IBlockState stateFrontLeft = worldIn.getBlockState(posFrontLeft);
        final IBlockState stateFrontRight = worldIn.getBlockState(posFrontRight);

        final boolean isPalisadeFront = stateFront.getBlock() instanceof BlockPalisade;
        final boolean isPalisadeFrontLeft = stateFrontLeft.getBlock() instanceof BlockPalisade;
        final boolean isPalisadeFrontRight = stateFrontRight.getBlock() instanceof BlockPalisade;

        final boolean cornerLeft = (isPalisadeFrontLeft && stateFrontLeft.getValue(RotationUtil.PROP_Y_ROTATION) == face) || (isPalisadeFront && stateFront.getValue(RotationUtil.PROP_Y_ROTATION) == face.rotateY());
        final boolean cornerRight = (isPalisadeFrontRight && stateFrontRight.getValue(RotationUtil.PROP_Y_ROTATION) == face) || (isPalisadeFront && stateFront.getValue(RotationUtil.PROP_Y_ROTATION) == face.rotateYCCW());

        return state.withProperty(PROP_CORNER_LEFT, cornerLeft).withProperty(PROP_CORNER_RIGHT, cornerRight);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
        final IBlockState state = getActualState(world.getBlockState(pos), world, pos);
        final EnumFacing face = RotationUtil.getYRotationFromState(state);

        setBlockBoundsFromFace(face);
    }

    public void setBlockBoundsFromFace(final EnumFacing face) {
        switch (face) {
            case NORTH:
                setBlockBounds(0, 0, 0, 1, 1, 0.1875f);
                break;
            case SOUTH:
                setBlockBounds(0, 0, 0.8125f, 1, 1, 1);
                break;
            case WEST:
                setBlockBounds(0, 0, 0, 0.1875f, 1, 1);
                break;
            default:
                setBlockBounds(0.8125f, 0, 0, 1, 1, 1);
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
        final IBlockState actualState = getActualState(state, world, pos);
        final EnumFacing face = RotationUtil.getYRotationFromState(state);

        setBlockBoundsFromFace(face);
        super.addCollisionBoxesToList(world, pos, state, mask, list, entity);

        if ((Boolean) actualState.getValue(PROP_CORNER_LEFT)) {
            setBlockBoundsFromFace(face.rotateY());
            super.addCollisionBoxesToList(world, pos, state, mask, list, entity);
        }

        if ((Boolean) actualState.getValue(PROP_CORNER_RIGHT)) {
            setBlockBoundsFromFace(face.rotateYCCW());
            super.addCollisionBoxesToList(world, pos, state, mask, list, entity);
        }
    }
}
