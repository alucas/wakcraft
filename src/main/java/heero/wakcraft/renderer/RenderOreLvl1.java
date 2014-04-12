package heero.wakcraft.renderer;

import heero.wakcraft.block.BlockOreLvl1;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderOreLvl1 implements ISimpleBlockRenderingHandler {
	private static final float[][] colors = new float[][]{{0.63F, 0.66F, 0.70F}, {0.63F, 0.66F, 0.70F}, {0.92F, 0.95F, 0.94F}, {0.2F, 0.2F, 0.2F}, {0.93F, 0.78F, 0.27F}, {0.55F, 0.65F, 0.65F}, {0.88F, 0.8F, 0.56F}, {0.88F, 0.8F, 0.56F}};
	public static int renderId;
	
	public RenderOreLvl1(int renderId) {
		this.renderId = renderId;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D,
				renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D,
				renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D,
				renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D,
				renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D,
				renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D,
				renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		int metadata = blockAccess.getBlockMetadata(x, y, z);
		
		if ((metadata & 1) == 0) {
			renderer.setOverrideBlockTexture(BlockOreLvl1.iconTop);
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

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderId;
	}
}
