package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.entity.misc.EntityTextPopup;
import heero.mc.mod.wakcraft.network.packet.PacketProfession;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public abstract class BlockHarvesting extends BlockOverSlab implements ILevelBlock {
    protected final ProfessionManager.PROFESSION profession;

    public BlockHarvesting(final Material material, final ProfessionManager.PROFESSION profession) {
        this(material, profession, WCreativeTabs.tabOther);
    }

    public BlockHarvesting(final Material material, final ProfessionManager.PROFESSION profession, final CreativeTabs tab) {
        super(material, tab);

        this.profession = profession;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
    }

    /**
     * Gets the hardness of block at the given coordinates in the given world,
     * relative to the ability of the given EntityPlayer.
     */
    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, BlockPos pos) {
        if (pos == BlockPos.ORIGIN) {
            return 0;
        } else if (!canHarvest(player, world, pos)) {
            return 0;
        } else if (ProfessionManager.getLevel(player, profession) < getLevel(world.getBlockState(pos))) {
            return 0;
        }

        return super.getPlayerRelativeBlockHardness(player, world, pos);
    }

    public abstract boolean canHarvest(final EntityPlayer player, final World world, final BlockPos pos);

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
        if (!world.isRemote) {
            return;
        }

        final IBlockState state = world.getBlockState(pos);
        final int blockLevel = getLevel(state);
        if (ProfessionManager.getLevel(player, profession) >= blockLevel) {
            return;
        }

        Item item = getItemDropped(state, null, 0);
        ItemStack itemStack = new ItemStack(item, 1, damageDropped(state));
        String itemName = item.getItemStackDisplayName(itemStack);
        player.addChatMessage(new ChatComponentText(I18n.format("message." + profession.toString().toLowerCase() + ".insufficientLevel", itemName, blockLevel)));
    }

    @Override
    public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (player.capabilities.isCreativeMode) {
            return world.setBlockToAir(pos);
        }

        if (!canHarvest(player, world, pos)) {
            return false;
        }

        int currentLevel = ProfessionManager.getLevel(player, profession);
        int xpAdded = ProfessionManager.addXpFromBlock(player, world, pos, profession);

        onBlockHarvested(world, pos);

        if (xpAdded <= 0) {
            return false;
        }

        if (world.isRemote) {
            int levelDiff = ProfessionManager.getLevel(player, profession) - currentLevel;
            if (levelDiff > 0) {
                world.spawnEntityInWorld(new EntityTextPopup(world, "+" + levelDiff + " niveau", pos.getX(), pos.getY() + 1, pos.getZ(), 1.0F, 0.21F, 0.21F));
                world.playRecord(pos, "random.levelup");
            } else {
                world.spawnEntityInWorld(new EntityTextPopup(world, "+" + xpAdded + "xp", pos.getX(), pos.getY() + 1, pos.getZ(), 0.21F, 0.21F, 1.0F));
                world.playRecord(pos, "random.orb");
            }
        } else if (player instanceof EntityPlayerMP) {
            Wakcraft.packetPipeline.sendTo(new PacketProfession(player, profession), (EntityPlayerMP) player);
        }

        return false;
    }

    public abstract void onBlockHarvested(final World world, final BlockPos pos);
}
