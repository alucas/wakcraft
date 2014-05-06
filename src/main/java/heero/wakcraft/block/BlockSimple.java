package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSimple extends Block {

	public BlockSimple(Material material) {
		super(material);
	}

	public Block setBlockTextureName(String textureName) {
		return super.setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":" + textureName);
	}
}
