package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWorkbench extends BlockOverSlab {
    protected final ProfessionManager.PROFESSION profession;

    public BlockWorkbench(final ProfessionManager.PROFESSION profession) {
        super(Material.wood, WCreativeTabs.tabSpecialBlock);

        this.profession = profession;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        player.openGui(Wakcraft.instance, GuiId.WORKBENCH.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Override
    public float getBlockHeight(IBlockAccess worldIn, BlockPos pos) {
        return 1;
    }

    public ProfessionManager.PROFESSION getProfession() {
        return profession;
    }
}
