package heero.wakcraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSlabGrass extends BlockSlab {
	public BlockSlabGrass() {
		super(Material.grass, Blocks.grass);

		setBlockName("SlabGrass");
		setBlockTextureName("grassSlabTop");
		setBlockTextureName(ForgeDirection.UP, "grassSlabTop");
		setBlockTextureName(ForgeDirection.DOWN, "grassSlabBottom");

		int[][] metadata = new int[][]{{0}, {1, 4}, {2, 5, 8}, {3, 6, 9}};
		for (int i = 0; i < metadata.length; i++) {
			for (int j : metadata[i]) {
				setBlockTextureName(ForgeDirection.EAST, j, "grassSlabSide" + i);
				setBlockTextureName(ForgeDirection.WEST, j, "grassSlabSide" + i);
				setBlockTextureName(ForgeDirection.NORTH, j, "grassSlabSide" + i);
				setBlockTextureName(ForgeDirection.SOUTH, j, "grassSlabSide" + i);
			}
		}
	}
}
