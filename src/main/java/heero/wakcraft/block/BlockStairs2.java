package heero.wakcraft.block;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockStairs2 extends BlockStairs {

	public BlockStairs2(Block block, int metadata) {
		super(block, metadata);
		
		this.setCreativeTab(WakcraftCreativeTabs.tabBlock);
		this.useNeighborBrightness = true;
	}
}
