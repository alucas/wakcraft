package heero.wakcraft.block;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.reference.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSufokiaSun extends Block {

	public BlockSufokiaSun() {
		super(Material.sand);

		setBlockTextureName(References.MODID.toLowerCase() + ":sufokiaSun");
		setBlockName("SufokiaSun");

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
	}
}
