package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSufokiaSun extends Block {

	public BlockSufokiaSun() {
		super(Material.sand);

		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":sufokiaSun");
		setBlockName("SufokiaSun");

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
	}
}
