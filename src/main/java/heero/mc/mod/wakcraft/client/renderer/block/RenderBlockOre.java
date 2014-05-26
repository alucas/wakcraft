package heero.mc.mod.wakcraft.client.renderer.block;

import heero.mc.mod.wakcraft.block.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderBlockOre extends RenderBlockGeneric {
	public static int renderId;
	
	public RenderBlockOre(int renderId) {
		RenderBlockOre.renderId = renderId;
	}
	
	@Override
	public int getRenderId() {
		return renderId;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		if ((metadata & 1) == 0) {
			float[] color = ((BlockOre)block).getColor(metadata);
			
			renderer.setOverrideBlockTexture(BlockOre.iconTop);
			
			renderer.setRenderBounds(0.125, 0.75, 0.125, 0.5, 0.875, 0.5);
			renderInventoryStandardBlock(block, metadata, renderer, color[0], color[1], color[2]);
			
			renderer.setRenderBounds(0.25, 0.75, 0.5625, 0.4375, 0.8125, 0.75);
			renderInventoryStandardBlock(block, metadata, renderer, color[0], color[1], color[2]);
			
			renderer.setRenderBounds(0.5625, 0.75, 0.375, 0.8125, 0.8125, 0.5625);
			renderInventoryStandardBlock(block, metadata, renderer, color[0], color[1], color[2]);
			
			renderer.clearOverrideBlockTexture();
		}

		renderer.setRenderBounds(0, 0, 0, 1, 0.75, 1);
        renderInventoryStandardBlock(block, metadata, renderer, 1, 1, 1);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		int metadata = blockAccess.getBlockMetadata(x, y, z);
		
		if ((metadata & 1) == 0) {
			float[] color = ((BlockOre)block).getColor(metadata);
			
			renderer.setOverrideBlockTexture(BlockOre.iconTop);
			renderer.renderAllFaces = true;
			
			renderer.setRenderBounds(0.125, 0.75, 0.125, 0.5, 0.875, 0.5);
			renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, color[0], color[1], color[2]);
			
			renderer.setRenderBounds(0.25, 0.75, 0.5625, 0.4375, 0.8125, 0.75);
			renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, color[0], color[1], color[2]);
			
			renderer.setRenderBounds(0.5625, 0.75, 0.375, 0.8125, 0.8125, 0.5625);
			renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, color[0], color[1], color[2]);
			
			renderer.renderAllFaces = false;
			renderer.clearOverrideBlockTexture();
		}

		renderer.setRenderBounds(0, 0, 0, 1, 0.75, 1);
        return renderer.renderStandardBlock(block, x, y, z);
	}
}
