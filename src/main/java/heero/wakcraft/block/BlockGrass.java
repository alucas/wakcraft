package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import net.minecraft.block.material.Material;

public class BlockGrass extends BlockSlab {
	public BlockGrass() {
		super(Material.grass);

		iconNames = new String[] { "grass", "dirt" };

		setBlockName("SlabGrass");
	}
}
