package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockYRotation extends BlockGeneric implements IRotation {
    public BlockYRotation(Material material, CreativeTabs tab) {
        super(material, tab);
    }

    public BlockYRotation(Material material) {
        super(material);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, RotationUtil.PROP_Y_ROTATION);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return RotationUtil.getMetadataFromYRotation((EnumFacing) state.getValue(RotationUtil.PROP_Y_ROTATION));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return super.getStateFromMeta(meta).withProperty(RotationUtil.PROP_Y_ROTATION, RotationUtil.getYRotationFromMetadata(meta));
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);

        RotationUtil.setYRotationFromYaw(world, pos, state, placer.rotationYaw);
    }

    @Override
    public EnumFacing getTopRotation(int metadata) {
        return RotationUtil.getYRotationFromMetadata(metadata);
    }

    @Override
    public EnumFacing getBottomRotation(int metadata) {
        return RotationUtil.getYRotationFromMetadata(metadata);
    }

    @Override
    public EnumFacing getEastRotation(int metadata) {
        return EnumFacing.UP;
    }

    @Override
    public EnumFacing getWestRotation(int metadata) {
        return EnumFacing.UP;
    }

    @Override
    public EnumFacing getNorthRotation(int metadata) {
        return EnumFacing.UP;
    }

    @Override
    public EnumFacing getSouthRotation(int metadata) {
        return EnumFacing.UP;
    }
}
