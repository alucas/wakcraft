package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.util.ZaapUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.Set;

public class BlockZaap extends BlockGeneric {
    public BlockZaap() {
        super(Material.ground, WCreativeTabs.tabSpecialBlock);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);

        Set<BlockPos> zaaps = ZaapUtil.getZaaps(playerIn, worldIn);
        if (!zaaps.contains(pos)) {
            ZaapUtil.registerNewZaap(playerIn, worldIn, pos);
            WLog.info("Register zaap (" + pos + ") for " + playerIn.getName());

            if (!worldIn.isRemote) {
                playerIn.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("message.zaap.registered", pos)));
            }

            return true;
        }

        if (worldIn.isRemote) {
            playerIn.openGui(Wakcraft.instance, GuiId.ZAAPS.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
}
