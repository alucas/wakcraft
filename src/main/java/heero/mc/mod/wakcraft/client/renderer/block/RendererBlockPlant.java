package heero.mc.mod.wakcraft.client.renderer.block;

public class RendererBlockPlant extends RendererBlockGeneric {
//	public static int renderId;
//
//	public RendererBlockPlant(int renderId) {
//		RendererBlockPlant.renderId = renderId;
//	}
//
//	@Override
//	public int getRenderId() {
//		return renderId;
//	}
//
//	@Override
//	public boolean shouldRender3DInInventory(int modelId) {
//		return false;
//	}
//
//	@Override
//	protected void renderInventoryStandardBlock(Block block, int metadata,
//			RenderBlocks renderer, float colorR, float colorG, float colorB) {
//		renderer.setRenderBounds(0.8125f, 0, 0, 1, 1, 1);
//
//		super.renderInventoryStandardBlock(block, metadata, renderer, colorR,
//				colorG, colorB);
//	}
//
//	@Override
//	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y,
//			int z, Block block, int modelId, RenderBlocks renderer) {
//		float yOffset = getYOffset(blockAccess, block, x, y, z);
//
//		Tessellator tessellator = Tessellator.instance;
//		tessellator.addTranslation(0, yOffset, 0);
//		tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));
//		int colorRGB = block.colorMultiplier(blockAccess, x, y, z);
//		float colorR = (float) (colorRGB >> 16 & 255) / 255.0F;
//		float colorG = (float) (colorRGB >> 8 & 255) / 255.0F;
//		float colorB = (float) (colorRGB & 255) / 255.0F;
//
//		if (EntityRenderer.anaglyphEnable) {
//			float f3 = (colorR * 30.0F + colorG * 59.0F + colorB * 11.0F) / 100.0F;
//			float f4 = (colorR * 30.0F + colorG * 70.0F) / 100.0F;
//			float f5 = (colorR * 30.0F + colorB * 70.0F) / 100.0F;
//			colorR = f3;
//			colorG = f4;
//			colorB = f5;
//		}
//
//		tessellator.setColorOpaque_F(colorR, colorG, colorB);
//		block.setBlockBoundsBasedOnState(blockAccess, x, y, z);
//
//		renderer.renderBlockStemSmall(block, blockAccess.getBlockMetadata(x, y, z),
//				renderer.renderMaxY, (double) x,
//				(double) ((float) y - 0.0625F),
//				(double) z);
//
//		tessellator.addTranslation(0, -yOffset, 0);
//
//		return true;
//	}
//
//	private float getYOffset(IBlockAccess blockAccess, Block block,
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
