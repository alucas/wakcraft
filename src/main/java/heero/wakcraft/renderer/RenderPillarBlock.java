package heero.wakcraft.renderer;

import heero.wakcraft.block.BlockPillar;
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
		BlockPillar pillarBlock = (BlockPillar) block;
		
		int type = Block.getIdFromBlock(block);
		boolean t1 = Block.getIdFromBlock(blockAccess.getBlock(x + 1, y, z)) == type;
		boolean t2 = Block.getIdFromBlock(blockAccess.getBlock(x, y, z + 1)) == type;
		boolean t3 = Block.getIdFromBlock(blockAccess.getBlock(x - 1, y, z)) == type;
		boolean t4 = Block.getIdFromBlock(blockAccess.getBlock(x, y, z - 1)) == type;
		
		int neighbor = (t1 ? 0b1 : 0) + (t2 ? 0b10 : 0) + (t3 ? 0b100 : 0) + (t4 ? 0b1000 : 0);
		
		if (pillarBlock.useCenterIcon() && neighbor == 0b1111) {
			renderer.overrideBlockTexture = pillarBlock.getCenterIcon(0, 0);
		} else if (pillarBlock.useCornerIcon() && (neighbor == 0b1100 || neighbor == 0b1001 || neighbor == 0b0011 || neighbor == 0b0110)) {
			renderer.overrideBlockTexture = pillarBlock.getCornerIcon(0, 0);
			renderer.uvRotateTop = (neighbor == 0b1100) ? 1 : (neighbor == 0b0011) ? 2 : (neighbor == 0b1001) ? 3 : 0; // todo
		} else {
			int metadata = blockAccess.getBlockMetadata(x, y, z) & 0x3;
			renderer.uvRotateTop = metadata;
		}

        boolean flag = renderer.renderStandardBlock(block, x, y, z);

        renderer.clearOverrideBlockTexture();
        renderer.uvRotateTop = 0;
        
        return flag;
	}
}
