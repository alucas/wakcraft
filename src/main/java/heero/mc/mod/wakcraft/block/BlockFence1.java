package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockFence;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;

public class BlockFence1 extends BlockFence {

	public BlockFence1(String iconPath, Material material) {
		super(iconPath, material);
		
		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockUnbreakable();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RendererBlockFence.renderId;
	}
}
