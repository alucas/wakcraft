package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockScree;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.material.Material;

public class BlockScree extends BlockGeneric {
	public BlockScree() {
		super(Material.ground);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockTextureName("ground15");
		setBlockName("Scree1");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RendererBlockScree.renderId;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
