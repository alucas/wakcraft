package heero.wakcraft.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderPillarBlock extends RenderStandardBlock {
	public static int renderId;
	
	public RenderPillarBlock(int renderId) {
		this.renderId = renderId;
	}
	
	@Override
	public int getRenderId() {
		return renderId;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		int metadata = blockAccess.getBlockMetadata(x, y, z) & 0x3;
		renderer.uvRotateTop = metadata;

        boolean flag = renderer.renderStandardBlock(block, x, y, z);

        renderer.uvRotateTop = 0;
        
        return flag;
	}
}
