package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.item.ItemSeed;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockCrop extends BlockHarvesting {
    protected static final int BREAKING_DURATION = 5; // seconds
    protected static final int UPDATE_INTERVAL = 5; // seconds
    protected static final int AGE_MAX = 2;

    protected final int blockLevel;
    protected final int blockXP;
    protected ItemSeed itemSeed;
    protected Item itemResource;

    public static final PropertyInteger PROP_AGE = PropertyInteger.create("age", 0, AGE_MAX);

    public BlockCrop(final int blockLevel, final int blockXP) {
        super(Material.plants, ProfessionManager.PROFESSION.FARMER);

        this.blockLevel = blockLevel;
        this.blockXP = blockXP;

        setDefaultState(getDefaultState().withProperty(PROP_AGE, 0));
        setHardness(BREAKING_DURATION * 0.66F);
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
        world.scheduleBlockUpdate(pos, this, UPDATE_INTERVAL * 20, 0);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);

        worldIn.scheduleBlockUpdate(pos, this, UPDATE_INTERVAL * 20, 0);
    }

    @Override
    public boolean canHarvest(final EntityPlayer player, final World world, final BlockPos pos) {
        return world.getBlockState(pos).getValue(PROP_AGE) == AGE_MAX;
    }

    @Override
    public void onHarvestingAction(final World world, final BlockPos pos, final EntityPlayer player) {
        final int nbItemDrop = 1 + RANDOM.nextInt(2);
        final Item itemDrop = (player.getHeldItem() != null && (player.getHeldItem().getItem() instanceof ItemHoe)) ? itemResource : itemSeed;
        if (itemDrop == null) {
            WLog.error("ItemSeed or ItemResource not initialized for " + getLocalizedName());
            return;
        }

        // dropBlockAsItemWithChance;
        if (!world.isRemote && !world.restoringBlockSnapshots) {
            for (int i = 0; i < nbItemDrop; i++) {
                spawnAsEntity(world, pos, new ItemStack(itemDrop));
            }
        }

        world.setBlockToAir(pos);
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public float getBlockHeight(final IBlockAccess worldIn, final BlockPos pos) {
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

    public void setItemResource(final Item itemResource) {
        this.itemResource = itemResource;
    }

    public void setItemSeed(final ItemSeed itemSeed) {
        this.itemSeed = itemSeed;
    }
}
