package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockSlab extends BlockGeneric implements IBlockProvider {
    protected static final PropertyInteger PROP_BOTTOM_POSITION = PropertyInteger.create("bottom_position", 0, 3);
    protected static final PropertyInteger PROP_SIZE = PropertyInteger.create("size", 1, 3);

    // Opaque version of the slab block
    private final IBlockState blockStateOpaque;

    public BlockSlab(final Material material, final IBlockState blockStateOpaque) {
        super(material, WCreativeTabs.tabBlock);

        if (!blockStateOpaque.getBlock().isOpaqueCube()) {
            WLog.warning("The slab block " + this.getClass().getName() + " use a non opaque block : " + blockStateOpaque.getBlock().getLocalizedName());
        }

        this.blockStateOpaque = blockStateOpaque;
        this.useNeighborBrightness = true;
        this.setFullCube(false);
        this.setLightOpacity(255);
        this.setDefaultState(this.blockState.getBaseState().withProperty(PROP_BOTTOM_POSITION, 0).withProperty(PROP_SIZE, 1));
    }

    public static int getSize(final IBlockState state) {
        return state.getValue(PROP_SIZE);
    }

    public static int getBottomPosition(final IBlockState state) {
        return state.getValue(PROP_BOTTOM_POSITION);
    }

    public static int getTopPosition(final IBlockState state) {
        return getBottomPosition(state) + getSize(state);
    }

    public static IBlockState setSize(final IBlockState state, final int size) {
        return state.withProperty(PROP_SIZE, size);
    }

    public static IBlockState setBottomPosition(final IBlockState state, final int bottomPosition) {
        return state.withProperty(PROP_BOTTOM_POSITION, bottomPosition);
    }

    public static IBlockState setTopPosition(final IBlockState state, final int topPosition) {
        return state.withProperty(PROP_SIZE, topPosition - getBottomPosition(state));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int size = ((meta & 0b1100) >> 2) + 1;
        int bottomPosition = meta & 0b0011;

        return getDefaultState().withProperty(PROP_BOTTOM_POSITION, bottomPosition).withProperty(PROP_SIZE, size);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, PROP_BOTTOM_POSITION, PROP_SIZE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if (state == null || state.getProperties().isEmpty()) {
            return 0;
        }

        int bottomPosition = state.getValue(PROP_BOTTOM_POSITION);
        int size = state.getValue(PROP_SIZE);

        return (((size - 1) << 2) & 0b1100) | (bottomPosition & 0b0011);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
        IBlockState blockState = world.getBlockState(pos);
        int size = getSize(blockState);
        int bottomPosition = getBottomPosition(blockState);

        setBlockBounds(0, bottomPosition * 0.25F, 0, 1, bottomPosition * 0.25F + size * 0.25F, 1);
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    @Override
    public void setBlockBoundsForItemRender() {
        setBlockBounds(0, 0, 0, 1, 0.25f, 1);
    }

    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add
     * boxes to the list if they intersect the mask.) Parameters: World, X, Y,
     * Z, mask, list, colliding entity
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {
        setBlockBoundsBasedOnState(world, pos);

        super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether
     * or not to render the shared face of two adjacent blocks and also whether
     * the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * How bright to render this block based on the light its receiving. Args:
     * iBlockAccess, x, y, z
     */
    @SideOnly(Side.CLIENT)
    @Override
    public int getMixedBrightnessForBlock(IBlockAccess world, BlockPos pos) {
        int l = super.getMixedBrightnessForBlock(world, pos);

        if (l == 0) {
            BlockPos posDown = pos.down();
            Block block = world.getBlockState(posDown).getBlock();
            l = world.getCombinedLight(posDown, block.getLightValue(world, posDown));
        }

        return l;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
        final MovingObjectPosition mop = super.collisionRayTrace(worldIn, pos, start, end);
        if (mop != null && mop.sideHit != EnumFacing.UP) {
            return mop;
        }

        final BlockPos posUp = pos.up();
        final IBlockState stateUp = worldIn.getBlockState(posUp);
        if (!(stateUp.getBlock() instanceof IOverSlab)) {
            return mop;
        }

        final MovingObjectPosition mopUp = stateUp.getBlock().collisionRayTrace(worldIn, posUp, start, end);
        if (mopUp == null) {
            return mop;
        }

        return mopUp;
    }

    @Override
    public IBlockState getSubBlockState() {
        return blockStateOpaque;
    }
}
