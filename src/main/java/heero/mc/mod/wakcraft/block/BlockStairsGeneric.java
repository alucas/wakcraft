package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockStairsGeneric extends BlockStairs {

    public BlockStairsGeneric(IBlockState state) {
        super(state);

        this.setCreativeTab(WCreativeTabs.tabBlock);
        this.setBlockUnbreakable();
        this.setResistance(6000000);
        this.useNeighborBrightness = true;
    }
}
