package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.entity.property.HavenBagProperty;
import heero.mc.mod.wakcraft.havenbag.HavenBagProperties;
import heero.mc.mod.wakcraft.havenbag.HavenBagsManager;
import heero.mc.mod.wakcraft.util.HavenBagUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.List;

public class BlockHavenBagBarrier extends BlockGeneric {

    public BlockHavenBagBarrier() {
        super(Material.air);

        setUnlocalizedName(Reference.MODID + "_HavenBagBarrier");
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean stopOnLiquid) {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        if (world.isRemote) {
            Wakcraft.proxy.getClientPlayer().addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.canPlaceBlockManualy")));
        }

        return false;
    }

    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add
     * boxes to the list if they intersect the mask.) Parameters: World, X, Y,
     * Z, mask, list, colliding entity
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity entity) {
        do {
            if (!(entity instanceof EntityPlayer)) {
                return;
            }

            EntityPlayer player = (EntityPlayer) entity;

            int havenBagUID = HavenBagUtil.getUIDFromCoord(entity.getPosition());
            IExtendedEntityProperties properties = entity.getExtendedProperties(HavenBagProperty.IDENTIFIER);
            if (properties == null || !(properties instanceof HavenBagProperty)) {
                WLog.warning("Error while loading the player (%s) properties", player.getDisplayName());
                break;
            }

            HavenBagProperty havenBagProperties = (HavenBagProperty) properties;
            if (havenBagUID == havenBagProperties.getUID()) {
                return;
            }

            HavenBagProperties hbProperties = HavenBagsManager.getProperties(havenBagUID);
            if (hbProperties == null) {
                break;
            }

            Integer rightAll = hbProperties.getRight(HavenBagProperties.ACL_KEY_ALL);
            Integer rightGuild = hbProperties.getRight(HavenBagProperties.ACL_KEY_GUILD);
            Integer right = hbProperties.getRight(player.getDisplayName().getUnformattedText());

            if (rightAll == 0 && rightGuild == 0 && (right == null || right == 0)) {
                break;
            }

            Block[] blocks = new Block[4];
            blocks[0] = world.getBlockState(new BlockPos(pos.getX(), 19, pos.getZ() + 1)).getBlock();
            blocks[1] = world.getBlockState(new BlockPos(pos.getX(), 19, pos.getZ() - 1)).getBlock();
            blocks[2] = world.getBlockState(new BlockPos(pos.getX() + 1, 19, pos.getZ())).getBlock();
            blocks[3] = world.getBlockState(new BlockPos(pos.getX() - 1, 19, pos.getZ())).getBlock();

            for (Block block : blocks) {
                if ((block.equals(WBlocks.hbGarden) && (rightAll & HavenBagUtil.R_GARDEN) == 0)
                        || (block.equals(WBlocks.hbMerchant) && (rightAll & HavenBagUtil.R_MERCHANT) == 0)
                        || (block.equals(WBlocks.hbDeco) && (rightAll & HavenBagUtil.R_DECO) == 0)
                        || (block.equals(WBlocks.hbCraft) && (rightAll & HavenBagUtil.R_CRAFT) == 0)
                        || (block.equals(WBlocks.hbDeco2) && (rightAll & HavenBagUtil.R_DECO) == 0)
                        || (block.equals(WBlocks.hbCraft2) && (rightAll & HavenBagUtil.R_CRAFT) == 0)
                        ) {
                    break;
                }
            }

            return;
        } while (false);

        AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBox(world, pos, state);

        if (axisalignedbb1 != null && mask.intersectsWith(axisalignedbb1)) {
            list.add(axisalignedbb1);
        }
    }
}
