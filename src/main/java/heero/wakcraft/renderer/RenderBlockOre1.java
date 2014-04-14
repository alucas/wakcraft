package heero.wakcraft.renderer;

import heero.wakcraft.block.BlockOre1;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderBlockOre1 extends RenderBlockGeneric {
	private static final float[][] colors = new float[][]{{0.63F, 0.66F, 0.70F}, {0.63F, 0.66F, 0.70F}, {0.92F, 0.95F, 0.94F}, {0.2F, 0.2F, 0.2F}, {0.93F, 0.78F, 0.27F}, {0.55F, 0.65F, 0.65F}, {0.88F, 0.8F, 0.56F}, {0.88F, 0.8F, 0.56F}};
	public static int renderId;
	
	public RenderBlockOre1(int renderId) {
		this.renderId = renderId;
	}
	
	@Override
	public int getRenderId() {
		return renderId;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		int metadata = blockAccess.getBlockMetadata(x, y, z);
		
		if ((metadata & 1) == 0) {
			renderer.setOverrideBlockTexture(BlockOre1.iconTop);
			renderOre(x, y, z, block, renderer, colors[metadata / 2]);
			renderer.clearOverrideBlockTexture();
		}

		renderer.setRenderBounds(0, 0, 0, 1, 0.75, 1);
        return renderer.renderStandardBlock(block, x, y, z);
	}
	
	public void renderOre(int x, int y, int z,
			Block block, RenderBlocks renderer, float[] color) {
		renderer.setRenderBounds(0.125, 0.75, 0.125, 0.5, 0.875, 0.5);
		renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, color[0], color[1], color[2]);
		
		renderer.setRenderBounds(0.25, 0.75, 0.5625, 0.4375, 0.8125, 0.75);
		renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, color[0], color[1], color[2]);
		
		renderer.setRenderBounds(0.5625, 0.75, 0.375, 0.8125, 0.8125, 0.5625);
		renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, color[0], color[1], color[2]);
	}
}
