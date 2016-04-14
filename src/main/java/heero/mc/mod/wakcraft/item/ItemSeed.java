package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.block.BlockCrop;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.entity.misc.EntityTextPopup;
import heero.mc.mod.wakcraft.network.packet.PacketProfession;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import heero.mc.mod.wakcraft.util.ItemInUseUtil;
import heero.mc.mod.wakcraft.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemSeed extends ItemWithLevel {
    protected static final int USE_DURATION = 100;

    protected final PROFESSION profession;
    protected final BlockCrop blockCrop;

    public ItemSeed(final PROFESSION profession, final int level, final Block blockCrop, final Item itemResource) {
        super(level);

        assert (blockCrop != null) : "The blockCrop must not be null";
        assert (blockCrop instanceof BlockCrop) : blockCrop + " must be an instance of " + BlockCrop.class.getName();
        assert (itemResource != null) : "The itemResource must not be null";

        this.profession = profession;
        this.blockCrop = (BlockCrop) blockCrop;

        this.blockCrop.setItemResource(itemResource);
        this.blockCrop.setItemSeed(this);

        setCreativeTab(WCreativeTabs.tabResource);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
                             BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!WorldUtil.isMainWorld(world)) {
            return false;
        }

        if (side != EnumFacing.UP) {
            return false;
        }

        if (!canSowHere(world, pos)) {
            if (world.isRemote) {
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("message.cropSeed.cantUseHere")));
            }

            return false;
        }

        int professionLevel = ProfessionManager.getLevel(player, profession);
        if (professionLevel < getItemLevel(0)) {
            if (world.isRemote) {
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("message.cropSeed.insufficientLevel", getItemStackDisplayName(stack), getItemLevel(0))));
            }

            return false;
        }

        ItemInUseUtil.saveCoords(player, pos);
        player.stopUsingItem();
        player.setItemInUse(stack, USE_DURATION);

        return true;
    }

    protected boolean canSowHere(final World world, final BlockPos pos) {
        final IBlockState state = world.getBlockState(pos);
        if (state.getBlock() != WBlocks.dirtSlab && state.getBlock() != WBlocks.dirt) {
            return false;
        }

        final IBlockState stateUp = world.getBlockState(pos.up());
        if (!stateUp.getBlock().isAir(world, pos.up())) {
            return false;
        }

        return true;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (player.worldObj.isRemote) {
            MovingObjectPosition mop = player.rayTrace(10, 1.0F);
            BlockPos previousPos = ItemInUseUtil.getCoords(player);
            if (mop == null || mop.sideHit != EnumFacing.UP || !mop.getBlockPos().equals(previousPos)) {
                player.stopUsingItem();
                return;
            }
        }

        if (player.motionX != 0 || Math.abs(player.motionY) > 0.1 || player.motionZ != 0) {
            player.stopUsingItem();
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            stack.stackSize--;
        }

        final BlockPos posUp = ItemInUseUtil.getCoords(player).up();
        world.setBlockState(posUp, blockCrop.getDefaultState());

        int currentLevel = ProfessionManager.getLevel(player, profession);
        int xpAdded = ProfessionManager.addXpFromBlock(player, world, posUp, profession);
        if (xpAdded <= 0) {
            return stack;
        }

        if (world.isRemote) {
            int levelDiff = ProfessionManager.getLevel(player, profession) - currentLevel;
            if (levelDiff > 0) {
                world.spawnEntityInWorld(new EntityTextPopup(world, "+" + levelDiff + " niveau", posUp.getX(), posUp.getY(), posUp.getZ(), 1.0F, 0.21F, 0.21F));
                world.playRecord(posUp, "random.levelup");
            } else {
                world.spawnEntityInWorld(new EntityTextPopup(world, "+" + xpAdded + "xp", posUp.getX(), posUp.getY(), posUp.getZ(), 0.21F, 0.21F, 1.0F));
                world.playRecord(posUp, "random.orb");
            }
        } else if (player instanceof EntityPlayerMP) {
            Wakcraft.packetPipeline.sendTo(new PacketProfession(player, profession), (EntityPlayerMP) player);
        }

        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return USE_DURATION;
    }
}