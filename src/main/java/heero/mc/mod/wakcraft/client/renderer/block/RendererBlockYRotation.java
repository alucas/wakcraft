package heero.mc.mod.wakcraft.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RendererBlockYRotation extends RendererBlockGeneric {
	public static int renderId;
	
	public RendererBlockYRotation(int renderId) {
		RendererBlockYRotation.renderId = renderId;
	}
	
	@Override
	public int getRenderId() {
		return renderId;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y,
			int z, Block block, int modelId, RenderBlocks renderer) {
		int metadata = blockAccess.getBlockMetadata(x, y, z) & 0x3;
		renderer.uvRotateTop = metadata;

		boolean flag = renderer.renderStandardBlock(block, x, y, z);

		renderer.uvRotateTop = 0;

		return flag;
	}
}
