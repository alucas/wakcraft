package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.block.material.Material;

public class BlockCloth extends BlockYRotation {
    public BlockCloth() {
        super(Material.cloth, WCreativeTabs.tabBlock);

        setFullCube(false);
        setFullBlock(false);
        setBlockBounds(0, 0, 0, 1, 1 / 16F, 1);
    }
}
