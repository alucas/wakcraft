package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.entity.property.HavenBagProperty;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import heero.mc.mod.wakcraft.util.HavenBagUtil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class BlockHavenGemWorkbench extends BlockContainer {
    public BlockHavenGemWorkbench() {
        super(Material.wood);

        setCreativeTab(WCreativeTabs.tabSpecialBlock);
        setBlockUnbreakable();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        IExtendedEntityProperties properties = player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
        if (properties == null || !(properties instanceof HavenBagProperty)) {
            WLog.warning("Error while loading the player (%s) extended properties", player.getDisplayName());
            return true;
        }

        if (((HavenBagProperty) properties).getUID() != HavenBagUtil.getUIDFromCoord(pos)) {
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.notYourBag")));
            return true;
        }

        TileEntity tile = world.getTileEntity(pos);
        if (tile == null || !(tile instanceof TileEntityHavenGemWorkbench)) {
            WLog.warning("Error while loading the tile entity (%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        player.openGui(Wakcraft.instance, GuiId.HAVEN_GEM_WORKBENCH.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing
     * the block.
     */
    @Override
    public TileEntity createNewTileEntity(World world, int var2) {
        return new TileEntityHavenGemWorkbench();
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        if (world.isRemote) {
            Wakcraft.proxy.getClientPlayer().addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.canPlaceBlockManualy")));
        }

        return false;
    }

    @Override
    public int getRenderType() {
        return 3;
    }
}
