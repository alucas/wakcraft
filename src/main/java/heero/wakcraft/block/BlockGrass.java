package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import net.minecraft.block.material.Material;

public class BlockGrass extends BlockSlab {
	public BlockGrass() {
		super(Material.grass);

		names = new String[] { "grass", "dirt" };

		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":grassTop");
		setBlockName("SlabGrass");
	}
}
