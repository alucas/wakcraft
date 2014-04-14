package heero.wakcraft.renderer;

import heero.wakcraft.block.BlockOre1;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderBlockOre1 extends RenderBlockGeneric {
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
			renderOre(x, y, z, block, renderer, ((BlockOre1)block).getColor(metadata));
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
