package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.block.material.Material;

public class BlockScree extends BlockGeneric {
    public BlockScree() {
        super(Material.ground);

        setCreativeTab(WCreativeTabs.tabBlock);
        setUnlocalizedName(Reference.MODID + "_Scree1");
    }

//	@Override
//	public int getRenderType() {
//		return RendererBlockScree.renderId;
//	}

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
