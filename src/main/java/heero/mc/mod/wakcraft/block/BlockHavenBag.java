package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.havenbag.HavenBagProperties;
import heero.mc.mod.wakcraft.havenbag.HavenBagsManager;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBag;
import heero.mc.mod.wakcraft.util.HavenBagUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class BlockHavenBag extends BlockContainerOverSlab {
    public BlockHavenBag() {
        super(Material.wood, WCreativeTabs.tabSpecialBlock);

        setCanBePlacedManually(false);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        TileEntity tile = world.getTileEntity(pos);
        if (tile == null || !(tile instanceof TileEntityHavenBag)) {
            WLog.warning("Error while loading the tile entity (%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        World havenBagWorld = MinecraftServer.getServer().worldServerForDimension(WConfig.getHavenBagDimensionId());
        if (havenBagWorld == null) {
            WLog.warning("Error while loading the havenbag world : %d", WConfig.getHavenBagDimensionId());

            return false;
        }

        HavenBagProperties havenBagProperties = HavenBagsManager.getProperties(((TileEntityHavenBag) tile).uid);
        if (havenBagProperties == null) {
            return true;
        }

        if (havenBagProperties.isLocked()) {
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.lockHavenBag")));
            return true;
        }

        if (player instanceof EntityPlayerMP) {
            HavenBagUtil.teleportPlayerToHavenBag((EntityPlayerMP) player, ((TileEntityHavenBag) tile).uid, pos);
        }

        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing
     * the block.
     */
    @Override
    public TileEntity createNewTileEntity(World world, int var2) {
        return new TileEntityHavenBag();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
    }
}
