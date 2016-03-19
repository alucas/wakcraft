package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.tileentity.TileEntityDragoexpress;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockDragoexpress extends BlockContainer {

    public BlockDragoexpress() {
        super(Material.wood);

        setCreativeTab(WCreativeTabs.tabSpecialBlock);
        setLightOpacity(0);
        setBlockUnbreakable();
        setDefaultState(blockState.getBaseState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH));
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityDragoexpress();
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
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        RotationUtil.setYRotationFromYaw(world, pos, state, placer.rotationYaw);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, RotationUtil.PROP_Y_ROTATION);
    }
}
