package heero.wakcraft.block;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;

public class BlockFence1 extends BlockFence {

	public BlockFence1(String iconPath, Material material) {
		super(iconPath, material);
		
		setCreativeTab(WakcraftCreativeTabs.tabBlock);
	}
}
