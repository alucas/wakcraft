package heero.wakcraft.renderer.entity;

import heero.wakcraft.WInfo;
import heero.wakcraft.entity.misc.EntityTextPopup;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderTextPopup extends Render {
	private static final ResourceLocation texture = new ResourceLocation(WInfo.MODID.toLowerCase(), "textures/entity/ascii.png");

	public RenderTextPopup() {
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity entity, double relativeX, double relativeY,
			double relativeZ, float rotationYaw, float partialTickTime) {

		if (!(entity instanceof EntityTextPopup)) {
			return;
		}

		EntityTextPopup entityText = (EntityTextPopup) entity;

		this.bindEntityTexture(entity);

		GL11.glPushMatrix();
		GL11.glTranslatef((float) relativeX + 0.5F, (float) relativeY, (float) relativeZ + 0.5F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		float scale = 0.3f;
		GL11.glScalef(scale, scale, scale);

		Tessellator tessellator = Tessellator.instance;

		double point = 1D / 16;

		for (int i = 0; i < entityText.text.length(); i++) {
			int letterCode = entityText.text.charAt(i);
			double minU = point * (letterCode % 16);
			double maxU = minU + point;
			double minV = point * (letterCode / 16);
			double maxV = minV + point;

			double offsetX = entityText.text.length() / 2F - i;
			double offsetY = 0.25F;

			GL11.glPushMatrix();
			GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
			GL11.glColor4f(entityText.colorRed, entityText.colorGreen, entityText.colorBlue, 1.0F);
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			tessellator.addVertexWithUV((double) (0.0F - offsetX), 0.0D - offsetY, 0.0D, (double) minU, (double) maxV);
			tessellator.addVertexWithUV((double) (1.0F - offsetX), 0.0D - offsetY, 0.0D, (double) maxU, (double) maxV);
			tessellator.addVertexWithUV((double) (1.0F - offsetX), 1.0D - offsetY, 0.0D, (double) maxU, (double) minV);
			tessellator.addVertexWithUV((double) (0.0F - offsetX), 1.0D - offsetY, 0.0D, (double) minU, (double) minV);
			tessellator.draw();
			GL11.glPopMatrix();
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		return texture;
	}
}
