package heero.wakcraft.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public abstract class RenderBlockGeneric implements
		ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		renderInventoryStandardBlock(block, metadata, renderer, 1, 1, 1);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	protected void renderInventoryStandardBlock(Block block, int metadata,
			RenderBlocks renderer, float colorR, float colorG, float colorB) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		tessellator.setColorOpaque_F(colorR, colorG, colorB);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D,
				renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.setColorOpaque_F(colorR, colorG, colorB);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D,
				renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		tessellator.setColorOpaque_F(colorR, colorG, colorB);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D,
				renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		tessellator.setColorOpaque_F(colorR, colorG, colorB);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D,
				renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		tessellator.setColorOpaque_F(colorR, colorG, colorB);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D,
				renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		tessellator.setColorOpaque_F(colorR, colorG, colorB);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D,
				renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
