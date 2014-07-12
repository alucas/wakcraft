package heero.mc.mod.wakcraft.client.renderer.block;

import heero.mc.mod.wakcraft.block.IRotation;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RendererBlockRotation extends RendererBlockGeneric {
	public static int renderId;
	
	public RendererBlockRotation(int renderId) {
		RendererBlockRotation.renderId = renderId;
	}
	
	@Override
	public int getRenderId() {
		return renderId;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y,
			int z, Block block, int modelId, RenderBlocks renderer) {
		if (block instanceof IRotation) {
			int metadata = blockAccess.getBlockMetadata(x, y, z);
			renderer.uvRotateTop = RotationUtil.getTopRotation(((IRotation) block).getTopRotation(metadata));
			renderer.uvRotateBottom = RotationUtil.getYNegRotation(((IRotation) block).getBottomRotation(metadata));
			renderer.uvRotateEast = RotationUtil.getNorthRotation(((IRotation) block).getNorthRotation(metadata));
			renderer.uvRotateWest = RotationUtil.getSouthRotation(((IRotation) block).getSouthRotation(metadata));
			renderer.uvRotateNorth = RotationUtil.getWestRotation(((IRotation) block).getWestRotation(metadata));
			renderer.uvRotateSouth = RotationUtil.getEastRotation(((IRotation) block).getEastRotation(metadata));
		}

		boolean flag = renderer.renderStandardBlock(block, x, y, z);

		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;

		return flag;
	}
}
