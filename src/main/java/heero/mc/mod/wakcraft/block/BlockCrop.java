package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.profession.ProfessionManager;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockCrop extends BlockHarvesting {
    protected static final int UPDATE_INTERVAL = 200;
    protected static final int AGE_MAX = 2;

    protected final int blockLevel;
    protected final int blockXP;

    public static final PropertyInteger PROP_AGE = PropertyInteger.create("age", 0, AGE_MAX);

    public BlockCrop(final int blockLevel, final int blockXP) {
        super(Material.plants, ProfessionManager.PROFESSION.FARMER);

        this.blockLevel = blockLevel;
        this.blockXP = blockXP;

        setDefaultState(getDefaultState().withProperty(PROP_AGE, 0));
        setHardness(6.66f); // 6.66 = 10s
    }

    @Override
    protected BlockState createBlockState() {
        final BlockState state = super.createBlockState();
        final IProperty[] properties = state.getProperties().toArray(new IProperty[state.getProperties().size() + 1]);
        properties[properties.length - 1] = PROP_AGE;

        return new BlockState(this, properties);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        final int age = state.getValue(PROP_AGE);

        return age & 0b0011;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return super.getStateFromMeta(meta).withProperty(PROP_AGE, meta);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);

        final int age = state.getValue(PROP_AGE);
        if (age >= AGE_MAX) {
            return;
        }

        world.setBlockState(pos, state.withProperty(PROP_AGE, age + 1));
        world.scheduleBlockUpdate(pos, this, UPDATE_INTERVAL, 0);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);

        worldIn.scheduleBlockUpdate(pos, this, UPDATE_INTERVAL, 0);
    }

    @Override
    public boolean canHarvest(final EntityPlayer player, final World world, final BlockPos pos) {
        return world.getBlockState(pos).getValue(PROP_AGE) == AGE_MAX;
    }

    @Override
    public void onBlockHarvested(final World world, final BlockPos pos) {
        world.setBlockToAir(pos);
//        dropBlockAsItemWithChance(world, pos, state, 0.5f, 0);
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public float getBlockHeight() {
        return 1;
    }

    @Override
    public int getLevel(IBlockState state) {
        return blockLevel;
    }

    @Override
    public int getProfessionExp(IBlockState state) {
        return blockXP;
    }
}
