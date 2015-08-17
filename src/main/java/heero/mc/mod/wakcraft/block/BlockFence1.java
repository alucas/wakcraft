package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;

public class BlockFence1 extends BlockFence {

	public BlockFence1(Material material) {
		super(material);
		
		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockUnbreakable();
	}
}
