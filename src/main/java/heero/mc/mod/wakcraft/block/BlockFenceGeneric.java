package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;

public class BlockFenceGeneric extends BlockFence {

    public BlockFenceGeneric(Material material) {
        super(material);

        setCreativeTab(WCreativeTabs.tabBlock);
        setResistance(600000);
        setBlockUnbreakable();
    }
}
