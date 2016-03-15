package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.havenbag.ChestType;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBagChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemIkiakit extends ItemWithLevel {
    public ItemIkiakit(final int level) {
        super(level);
    }

    /**
     * This is called when the item is used, before the block is activated.
     */
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world,
                                  BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (world.getBlockState(pos).getBlock().equals(WBlocks.hbChest)) {
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBagChest)) {
                    WLog.warning("Error while loading the TileEntityHavenBagChest (%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ());

                    return false;
                }

                TileEntityHavenBagChest tileEntityChest = (TileEntityHavenBagChest) tileEntity;
                ChestType chestId = ChestType.getChestIdFromIkiakit(this);
                if (chestId == null) {
                    WLog.warning("No chestId found for itiakit : " + this);

                    return false;
                }

                if (tileEntityChest.isChestUnlocked(chestId)) {
                    player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.chestAlreadyUnlocked")));

                    return true;
                }

                tileEntityChest.unlockChest(chestId);
                tileEntityChest.markDirty();

                if (player instanceof EntityPlayerMP) {
                    ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(tileEntityChest.getDescriptionPacket());
                }

                if (world instanceof WorldServer) {
                    ((WorldServer) world).spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0);
                }

                world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.levelup", 1f, 2f);

                --stack.stackSize;

                return true;
            }
        }

        return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ);
    }
}