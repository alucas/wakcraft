package heero.mc.mod.wakcraft.block.vein;

import heero.mc.mod.wakcraft.block.BlockHarvesting;
import heero.mc.mod.wakcraft.block.ILevelBlock;
import heero.mc.mod.wakcraft.block.material.AdventureMaterial;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.item.EnumOre;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public abstract class BlockVein extends BlockHarvesting implements ILevelBlock {
    /**
     * Wakfu Ore block.
     * Metadata :
     * - Bit 1 : isExtractable
     * - Bit 2, 3, 4 : Mineral type
     */
    public BlockVein() {
        super(new AdventureMaterial(MapColor.brownColor), PROFESSION.MINER, WCreativeTabs.tabOreBlock);

        setDefaultState(blockState.getBaseState().withProperty(getPropExtractable(), EnumExtractable.NOT_MINED));
        setHardness(6.66f); // 6.66 = 10s
    }

    public abstract PropertyEnum<EnumExtractable> getPropExtractable();

    public abstract PropertyEnum<EnumOre> getPropOre();

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood
     * returns 4 blocks)
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
        for (int i = 0; i < getPropOre().getAllowedValues().toArray().length; i++) {
            subItems.add(new ItemStack(item, 1, i << 1));
        }
    }

    @Override
    protected BlockState createBlockState() {
        final BlockState state = super.createBlockState();
        final IProperty[] properties = state.getProperties().toArray(new IProperty[state.getProperties().size() + 2]);
        properties[properties.length - 2] = getPropExtractable();
        properties[properties.length - 1] = getPropOre();

        return new BlockState(this, properties);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if (state == null || state.getProperties().isEmpty()) {
            return 0;
        }

        int metaExtractable = state.getValue(getPropExtractable()).ordinal();
        int metaOre = state.getValue(getPropOre()).ordinal();

        return ((metaOre << 1) & 0b1110) | (metaExtractable & 0b0001);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int metaExtractable = meta & 0b0001;
        int metaOre = (meta & 0b1110) >> 1;

        return getDefaultState()
                .withProperty(getPropExtractable(), EnumExtractable.values()[metaExtractable])
                .withProperty(getPropOre(), (EnumOre) getPropOre().getAllowedValues().toArray()[metaOre]);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);

        if (state.getValue(getPropExtractable()) == EnumExtractable.NOT_MINED) {
            return;
        }

        world.setBlockState(pos, state.withProperty(getPropExtractable(), EnumExtractable.NOT_MINED), 2);
    }

    @Override
    public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
        int meta = getMetaFromState(worldIn.getBlockState(pos));

        return getStateFromMeta(meta).getValue(getPropOre()).getColor();
    }

    @Override
    public int getLevel(final IBlockState state) {
        return state.getValue(getPropOre()).getLevel();
    }

    /**
     * Gathers how much experience this block drops when broken.
     *
     * @param state The state of the block.
     * @return Amount of XP from breaking this block.
     */
    @Override
    public int getProfessionExp(final IBlockState state) {
        return 100;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(getPropOre()).ordinal() % 16;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random random) {
        return 3;
    }

    @Override
    public boolean canHarvest(final EntityPlayer player, final World world, final BlockPos pos) {
        return world.getBlockState(pos).getValue(getPropExtractable()) == EnumExtractable.NOT_MINED;
    }

    @Override
    public void onBlockHarvested(final World world, final BlockPos pos) {
        final IBlockState state = world.getBlockState(pos);

        world.setBlockState(pos, state.withProperty(getPropExtractable(), EnumExtractable.MINED), 2);
        world.scheduleBlockUpdate(pos, this, 1200, 0); // 20 = 1s, 1200 = 1min

        dropBlockAsItemWithChance(world, pos, state, 0.5f, 0);
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean stopOnLiquid) {
        return state.getValue(getPropExtractable()) != EnumExtractable.MINED;
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public float getBlockHeight() {
        return (1F / 16) * 3;
    }

    public enum EnumExtractable implements IStringSerializable {
        NOT_MINED,
        MINED;

        @Override
        public String getName() {
            return this.toString();
        }
    }
}
