package heero.wakcraft.block;

import heero.wakcraft.WInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSufokiaSun extends Block {

	public BlockSufokiaSun() {
		super(Material.sand);

		setBlockTextureName(WInfo.MODID.toLowerCase() + ":sufokiaSun");
		setBlockName("SufokiaSun");

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
	}
}
