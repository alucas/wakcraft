package heero.mc.mod.wakcraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockGenericTransparent extends BlockGeneric {
    public BlockGenericTransparent(Material material) {
        super(material);
    }

    public BlockGenericTransparent(Material material, CreativeTabs tab) {
        super(material, tab);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
