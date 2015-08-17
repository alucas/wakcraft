package heero.mc.mod.wakcraft.client.renderer.block;

public class RendererBlockFence extends RendererBlockGeneric {
//	public static int renderId;
//
//	public RendererBlockFence(int renderId) {
//		RendererBlockFence.renderId = renderId;
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
//		renderInventoryBlock((BlockFence) block, metadata, modelID, renderer);
//	}
//
//	public void renderInventoryBlock(BlockFence block, int metadata,
//			int modelID, RenderBlocks renderer) {
//		double unit = 1 / 16.0D;
//
//		renderer.setRenderBounds(unit * 6, unit * 0, unit * 0, unit * 10, unit * 16, unit * 4);
//		renderInventoryStandardBlock(block, metadata, renderer, 1, 1, 1);
//
//		renderer.setRenderBounds(unit * 6, unit * 0, unit * 12, unit * 10, unit * 16, unit * 16);
//		renderInventoryStandardBlock(block, metadata, renderer, 1, 1, 1);
//
//		renderer.setRenderBounds(unit * 7, unit * 7, unit * 4, unit * 9, unit * 9, unit * 12);
//		renderInventoryStandardBlock(block, metadata, renderer, 1, 1, 1);
//
//		renderer.setRenderBounds(unit * 7, unit * 13, unit * 4, unit * 9, unit * 15, unit * 12);
//		renderInventoryStandardBlock(block, metadata, renderer, 1, 1, 1);
//	}
//
//	@Override
//	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z,
//			Block block, int modelId, RenderBlocks renderer) {
//		return renderWorldBlock(blockAccess, x, y, z, (BlockFence) block, modelId, renderer);
//	}
//
//	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z,
//			BlockFence block, int modelId, RenderBlocks renderer) {
//		float yOffset = getYOffset(blockAccess, block, x, y, z);
//
//		double unit = 1/16.0D;
//
//		renderer.setRenderBounds(unit * 6, yOffset, unit * 6, unit * 10, 1 + yOffset, unit * 10);
//		renderer.renderStandardBlock(block, x, y, z);
//
//		boolean flag = true;
//		boolean canConnectX = false;
//		boolean canConnectZ = false;
//
//		if (block.canConnectFenceTo(blockAccess, x - 1, y, z) || block.canConnectFenceTo(blockAccess, x + 1, y, z)) {
//			canConnectX = true;
//		}
//
//		if (block.canConnectFenceTo(blockAccess, x, y, z - 1) || block.canConnectFenceTo(blockAccess, x, y, z + 1)) {
//			canConnectZ = true;
//		}
//
//		boolean canConnectXNeg = block.canConnectFenceTo(blockAccess, x - 1, y, z);
//		boolean canConnectXPos = block.canConnectFenceTo(blockAccess, x + 1, y, z);
//		boolean canConnectZNeg = block.canConnectFenceTo(blockAccess, x, y, z - 1);
//		boolean canConnectZPos = block.canConnectFenceTo(blockAccess, x, y, z + 1);
//
//		if (!canConnectX && !canConnectZ) {
//			canConnectX = true;
//		}
//
//		double f = 0.4375F;
//		double f1 = 0.5625F;
//		double f2 = 0.75F + yOffset;
//		double f3 = 0.9375F + yOffset;
//		double f4 = canConnectXNeg ? 0.0F : f;
//		double f5 = canConnectXPos ? 1.0F : f1;
//		double f6 = canConnectZNeg ? 0.0F : f;
//		double f7 = canConnectZPos ? 1.0F : f1;
//
//		if (canConnectX) {
//			renderer.setRenderBounds(f4, f2, f, f5, f3, f1);
//			renderer.renderStandardBlock(block, x, y, z);
//			flag = true;
//		}
//
//		if (canConnectZ) {
//			renderer.setRenderBounds(f, f2, f6, f1, f3, f7);
//			renderer.renderStandardBlock(block, x, y, z);
//			flag = true;
//		}
//
//		f2 = 0.375F + yOffset;
//		f3 = 0.5625F + yOffset;
//
//		if (canConnectX) {
//			renderer.setRenderBounds(f4, f2, f, f5, f3, f1);
//			renderer.renderStandardBlock(block, x, y, z);
//			flag = true;
//		}
//
//		if (canConnectZ) {
//			renderer.setRenderBounds(f, f2, f6, f1, f3, f7);
//			renderer.renderStandardBlock(block, x, y, z);
//			flag = true;
//		}
//
//		block.setBlockBoundsBasedOnState(blockAccess, x, y, z);
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
