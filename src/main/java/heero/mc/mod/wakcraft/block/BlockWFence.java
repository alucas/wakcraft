package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;

public class BlockWFence extends BlockFence {

    public BlockWFence(Material material) {
        super(material);

        setCreativeTab(WCreativeTabs.tabBlock);
        setBlockUnbreakable();
    }
}
