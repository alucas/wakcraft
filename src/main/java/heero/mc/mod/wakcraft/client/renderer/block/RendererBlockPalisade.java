package heero.mc.mod.wakcraft.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RendererBlockPalisade extends RendererBlockGeneric {
	public static int renderId;

	public RendererBlockPalisade(int renderId) {
		RendererBlockPalisade.renderId = renderId;
	}

	@Override
	public int getRenderId() {
		return renderId;
	}

	@Override
	protected void renderInventoryStandardBlock(Block block, int metadata,
			RenderBlocks renderer, float colorR, float colorG, float colorB) {
		renderer.setRenderBounds(0.8125f, 0, 0, 1, 1, 1);

		super.renderInventoryStandardBlock(block, metadata, renderer, colorR,
				colorG, colorB);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y,
			int z, Block block, int modelId, RenderBlocks renderer) {
		int m = blockAccess.getBlockMetadata(x, y, z) & 0x3;
		int m1 = blockAccess.getBlockMetadata(x + 1, y, z) & 0x3;
		int m2 = blockAccess.getBlockMetadata(x, y, z + 1) & 0x3;
		int m3 = blockAccess.getBlockMetadata(x - 1, y, z) & 0x3;
		int m4 = blockAccess.getBlockMetadata(x, y, z - 1) & 0x3;

		int blockId = Block.getIdFromBlock(blockAccess.getBlock(x, y, z));
		boolean t1 = Block.getIdFromBlock(blockAccess.getBlock(x + 1, y, z)) == blockId;
		boolean t2 = Block.getIdFromBlock(blockAccess.getBlock(x, y, z + 1)) == blockId;
		boolean t3 = Block.getIdFromBlock(blockAccess.getBlock(x - 1, y, z)) == blockId;
		boolean t4 = Block.getIdFromBlock(blockAccess.getBlock(x, y, z - 1)) == blockId;

		switch (m) {
		case 0:
			renderer.setRenderBounds(0, 0, 0, 1, 1, 0.1875f);
			break;
		case 1:
			renderer.setRenderBounds(0.8125f, 0, 0, 1, 1, 1);
			break;
		case 2:
			renderer.setRenderBounds(0, 0, 0, 0.1875f, 1, 1);
			break;
		default:
			renderer.setRenderBounds(0, 0, 0.8125f, 1, 1, 1);
		}

		renderer.uvRotateTop = m;

		boolean flag = renderer.renderStandardBlock(block, x, y, z);

		if (t3 && m == 1 && m3 == 0) {
			renderer.setRenderBounds(0, 0, 0, 0.8125, 1, 0.1875);
			renderer.renderStandardBlock(block, x, y, z);
		} else if (t1 && m == 2 && m1 == 0) {
			renderer.setRenderBounds(0.1875, 0, 0, 1, 1, 0.1875);
			renderer.renderStandardBlock(block, x, y, z);
		} else if (t4 && m == 3 && m4 == 1) {
			renderer.uvRotateTop = 1;
			renderer.setRenderBounds(0.8125, 0, 0, 1, 1, 0.8125);
			renderer.renderStandardBlock(block, x, y, z);
		} else if (t2 && m == 0 && m2 == 1) {
			renderer.uvRotateTop = 1;
			renderer.setRenderBounds(0.8125, 0, 0.1875, 1, 1, 1);
			renderer.renderStandardBlock(block, x, y, z);
		} else if (t3 && m == 1 && m3 == 3) {
			renderer.uvRotateTop = 3;
			renderer.setRenderBounds(0, 0, 0.8125, 0.8125, 1, 1);
			renderer.renderStandardBlock(block, x, y, z);
		} else if (t1 && m == 2 && m1 == 3) {
			renderer.uvRotateTop = 3;
			renderer.setRenderBounds(0.1875, 0, 0.8125, 1, 1, 1);
			renderer.renderStandardBlock(block, x, y, z);
		} else if (t4 && m == 3 && m4 == 2) {
			renderer.uvRotateTop = 2;
			renderer.setRenderBounds(0, 0, 0, 0.1875, 1, 0.8125);
			renderer.renderStandardBlock(block, x, y, z);
		} else if (t2 && m == 0 && m2 == 2) {
			renderer.uvRotateTop = 2;
			renderer.setRenderBounds(0, 0, 0.1875, 0.1875, 1, 1);
			renderer.renderStandardBlock(block, x, y, z);
		}

		renderer.uvRotateTop = 0;

		return flag;
	}
}
