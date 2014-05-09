package heero.wakcraft.block;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInvisibleWall extends Block {

	public BlockInvisibleWall() {
		super(Material.air);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockName("InvisibleWall");
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}
}
