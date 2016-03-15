package heero.mc.mod.wakcraft.block.ore;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.block.BlockGeneric;
import heero.mc.mod.wakcraft.block.ILevelBlock;
import heero.mc.mod.wakcraft.block.material.AventureMaterial;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.entity.misc.EntityTextPopup;
import heero.mc.mod.wakcraft.item.EnumOre;
import heero.mc.mod.wakcraft.network.packet.PacketProfession;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public abstract class BlockOre extends BlockGeneric implements ILevelBlock {

    /**
     * Wakfu Ore block.
     * Metadata :
     * - Bit 1 : isExtractable
     * - Bit 2, 3, 4 : Mineral type
     */
    public BlockOre() {
        super(new AventureMaterial(MapColor.brownColor), WCreativeTabs.tabOreBlock);

        setDefaultState(blockState.getBaseState().withProperty(getPropExtractable(), EnumExtractable.NOT_MINED));
        setUnlocalizedName(Reference.MODID.toLowerCase() + ".ore_");
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        setHardness(6.66f); // 6.66 = 10s
        setOpaque(false);
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
        return new BlockState(this, getPropExtractable(), getPropOre());
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

    /**
     * Gets the hardness of block at the given coordinates in the given world,
     * relative to the ability of the given EntityPlayer.
     */
    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, BlockPos pos) {
        if (pos == BlockPos.ORIGIN) {
            return 0;
        } else if (world.getBlockState(pos).getValue(getPropExtractable()) == EnumExtractable.MINED) {
            return 0;
        } else if (ProfessionManager.getLevel(player, PROFESSION.MINER) < getLevel(world.getBlockState(pos))) {
            return 0;
        }

        return super.getPlayerRelativeBlockHardness(player, world, pos);
    }

    /**
     * Called when a player removes a block.  This is responsible for
     * actually destroying the block, and the block is intact at time of call.
     * This is called regardless of whether the player can harvest the block or
     * not.
     * <p/>
     * Return true if the block is actually destroyed.
     * <p/>
     * Note: When used in multiplayer, this is called on both client and
     * server sides!
     *
     * @param world       The current world
     * @param player      The player damaging the block, may be null
     * @param pos         Block position in world
     * @param willHarvest True if Block.harvestBlock will be called after this, if the return in true.
     *                    Can be useful to delay the destruction of tile entities till after harvestBlock
     * @return True if the block is actually destroyed.
     */
    @Override
    public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (player.capabilities.isCreativeMode) {
            return world.setBlockToAir(pos);
        }

        IBlockState state = world.getBlockState(pos);
        if (state.getValue(getPropExtractable()) == EnumExtractable.MINED) {
            return false;
        }

        world.setBlockState(pos, state.withProperty(getPropExtractable(), EnumExtractable.MINED), 2);
        world.scheduleBlockUpdate(pos, this, 1200, 0); // 20 = 1s, 1200 = 1min

        dropBlockAsItemWithChance(world, pos, state, 0.5f, 0);

        int currentLevel = ProfessionManager.getLevel(player, PROFESSION.MINER);
        int xp = ProfessionManager.addXpFromBlock(player, world, pos, PROFESSION.MINER);
        if (xp <= 0) {
            return false;
        }

        if (world.isRemote) {
            int levelDiff = ProfessionManager.getLevel(player, PROFESSION.MINER) - currentLevel;
            if (levelDiff > 0) {
                world.spawnEntityInWorld(new EntityTextPopup(world, "+" + levelDiff + " niveau", pos.getX(), pos.getY() + 1, pos.getZ(), 1.0F, 0.21F, 0.21F));
                world.playRecord(pos, "random.levelup");
            } else {
                world.spawnEntityInWorld(new EntityTextPopup(world, "+" + xp + "xp", pos.getX(), pos.getY() + 1, pos.getZ(), 0.21F, 0.21F, 1.0F));
                world.playRecord(pos, "random.orb");
            }
        } else if (player instanceof EntityPlayerMP) {
            Wakcraft.packetPipeline.sendTo(new PacketProfession(player, PROFESSION.MINER), (EntityPlayerMP) player);
        }

        return false;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (state.getValue(getPropExtractable()) == EnumExtractable.NOT_MINED) {
            return;
        }

        world.setBlockState(pos, state.withProperty(getPropExtractable(), EnumExtractable.NOT_MINED), 2);
    }

    /**
     * Called when a player hits the block. Args: world, x, y, z, player
     */
    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
        if (!world.isRemote) {
            return;
        }

        IBlockState state = world.getBlockState(pos);
        int blockLevel = getLevel(state);
        if (ProfessionManager.getLevel(player, PROFESSION.MINER) >= blockLevel) {
            return;
        }

        Item item = getItemDropped(state, null, 0);
        ItemStack itemStack = new ItemStack(item, 1, damageDropped(state));
        String itemName = item.getItemStackDisplayName(itemStack);
        player.addChatMessage(new ChatComponentText(I18n.format("message.blockOre.insufficientLevel", itemName, blockLevel)));
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
