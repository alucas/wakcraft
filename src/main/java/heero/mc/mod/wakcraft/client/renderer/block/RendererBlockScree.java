package heero.mc.mod.wakcraft.client.renderer.block;

public class RendererBlockScree extends RendererBlockGeneric {
//	public static int renderId;
//
//	public RendererBlockScree(int renderId) {
//		RendererBlockScree.renderId = renderId;
//	}
//
//	@Override
//	public int getRenderId() {
//		return renderId;
//	}
//
//	@Override
//	public void renderInventoryBlock(Block block, int metadata, int modelID,
//			RenderBlocks renderer) {
//		renderer.setRenderBounds(0, 0, 0, 0.5, 1, 0.5);
//		renderInventoryStandardBlock(block, metadata, renderer, 1, 1, 1);
//
//		renderer.setRenderBounds(0.5, 0, 0, 1, 0.6, 0.5);
//		renderInventoryStandardBlock(block, metadata, renderer, 1, 1, 1);
//
//		renderer.setRenderBounds(0, 0, 0.5, 0.5, 0.4, 1);
//		renderInventoryStandardBlock(block, metadata, renderer, 1, 1, 1);
//
//		renderer.setRenderBounds(0.5, 0, 0.5, 1, 0.3, 1);
//		renderInventoryStandardBlock(block, metadata, renderer, 1, 1, 1);
//	}
//
//	@Override
//	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z,
//			Block block, int modelId, RenderBlocks renderer) {
//		float yOffset = getYOffset(blockAccess, block, x, y, z);
//
//		renderer.renderAllFaces = true;
//
//		renderer.setRenderBounds(0, yOffset, 0, 0.5, 0.3 + yOffset, 0.5);
//		renderer.renderStandardBlock(block, x, y, z);
//
//		renderer.setRenderBounds(0.5, yOffset, 0, 1, 0.6 + yOffset, 0.5);
//		renderer.renderStandardBlock(block, x, y, z);
//
//		renderer.setRenderBounds(0, yOffset, 0.5, 0.5, 0.4 + yOffset, 1);
//		renderer.renderStandardBlock(block, x, y, z);
//
//		renderer.setRenderBounds(0.5, yOffset, 0.5, 1, 1 + yOffset, 1);
//		boolean flag = renderer.renderStandardBlock(block, x, y, z);
//
//		renderer.renderAllFaces = false;
//
//		return flag;
//	}
//
//	protected float getYOffset(IBlockAccess blockAccess, Block block,
//			int x, int y, int z) {
//		if (y == 0) {
//			return 0;
//		}
//
//		Block blockUnder = blockAccess.getBlock(x, y - 1, z);
//		if (!(blockUnder instanceof BlockSlab)) {
//			return 0;
//		}
//
//		int blockUnderMetadata = blockAccess.getBlockMetadata(x, y - 1, z);
//		int topPosition = BlockSlab.getTopPosition(blockUnderMetadata);
//
//		return (topPosition + 1) * (1/4.0F) - 1;
//	}
}
