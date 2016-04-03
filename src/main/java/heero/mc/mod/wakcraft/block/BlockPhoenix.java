package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.tileentity.TileEntityPhoenix;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class BlockPhoenix extends BlockContainer {

    public BlockPhoenix() {
        super(Material.wood);

        setCreativeTab(WCreativeTabs.tabSpecialBlock);
        setBlockUnbreakable();
        setDefaultState(blockState.getBaseState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH));
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityPhoenix();
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        EnumFacing yRotation = RotationUtil.getYRotationFromYaw(placer.rotationYaw);

        world.setBlockState(pos, state.withProperty(RotationUtil.PROP_Y_ROTATION, yRotation));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing yRotation = RotationUtil.getYRotationFromMetadata(meta);

        return getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, yRotation);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing yRotation = (EnumFacing) state.getValue(RotationUtil.PROP_Y_ROTATION);

        return RotationUtil.getMetadataFromYRotation(yRotation);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, RotationUtil.PROP_Y_ROTATION);
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

    @Override
    public int getRenderType() {
        return -1;
    }

    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add
     * boxes to the list if they intersect the mask.) Parameters: World, X, Y,
     * Z, mask, list, colliding entity
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity entity) {
        setBlockBounds(0F, 0F, 0F, 1F, 1.5F, 1F);
        super.addCollisionBoxesToList(world, pos, state, mask, list, entity);
        setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector
     * returning a ray trace hit. Args: world, x, y, z, startVec, endVec
     */
    @Override
    public MovingObjectPosition collisionRayTrace(World world, BlockPos pos, Vec3 start, Vec3 end) {
        setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
        MovingObjectPosition mop1 = super.collisionRayTrace(world, pos, start, end);
        setBlockBounds(1F/16 * 3, 0.5F, 1F/16 * 3, 1F/16 * 13, 1F, 1F/16 * 13);
        MovingObjectPosition mop2 = super.collisionRayTrace(world, pos, start, end);

        double d1 = 0, d2 = 0;
        if (mop1 != null) {
            d1 = mop1.hitVec.squareDistanceTo(end);
        }

        if (mop2 != null) {
            d2 = mop2.hitVec.squareDistanceTo(end);
        }

        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        return (d1 > d2) ? mop1 : mop2;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            world.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "random.click", 0.3F, 1F);
            player.setSpawnPoint(player.getPosition(), true);
        } else {
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.phoenixRegister")));
        }

        return true;
    }
}
