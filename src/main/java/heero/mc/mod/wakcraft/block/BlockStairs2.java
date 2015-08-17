package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockStairs2 extends BlockStairs {

	public BlockStairs2(IBlockState state) {
		super(state);
		
		this.setCreativeTab(WakcraftCreativeTabs.tabBlock);
		this.setBlockUnbreakable();
		this.useNeighborBrightness = true;
	}
}
