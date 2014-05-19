package heero.wakcraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class BlockSlabDirt extends BlockSlab {
	public BlockSlabDirt() {
		super(Material.ground, Blocks.dirt);

		setBlockName("SlabDirt");
		setBlockTextureName("dirtSlab");
	}
}
