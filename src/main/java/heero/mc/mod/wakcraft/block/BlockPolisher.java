package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.block.BlockGeneric;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.network.GuiId;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockPolisher extends BlockGeneric {

    public BlockPolisher() {
        super(Material.wood, WCreativeTabs.tabSpecialBlock);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        player.openGui(Wakcraft.instance, GuiId.POLISHER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }
}
