package heero.mc.mod.wakcraft.client.renderer.entity;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.entity.misc.EntityTextPopup;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RendererTextPopup extends Render<EntityTextPopup> {
    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/entity/ascii.png");

    public RendererTextPopup(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTextPopup entity) {
        return texture;
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
    public void doRender(EntityTextPopup entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.bindEntityTexture(entity);

        float scale = 0.3f;
        double point = 1D / 16;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5F, y, z + 0.5F);
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.color(entity.colorRed, entity.colorGreen, entity.colorBlue, 1.0F);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        for (int i = 0; i < entity.text.length(); i++) {
            int letterCode = entity.text.charAt(i);
            double minU = point * (letterCode % 16);
            double maxU = minU + point;
            double minV = point * (letterCode / 16);
            double maxV = minV + point;

            double offsetX = entity.text.length() / 2F - i;
            double offsetY = 0.25F;

            worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(0.0F - offsetX, 0.0D - offsetY, 0).tex(minU, maxV).endVertex();
            worldRenderer.pos(1.0F - offsetX, 0.0D - offsetY, 0).tex(maxU, maxV).endVertex();
            worldRenderer.pos(1.0F - offsetX, 1.0D - offsetY, 0).tex(maxU, minV).endVertex();
            worldRenderer.pos(0.0F - offsetX, 1.0D - offsetY, 0).tex(minU, minV).endVertex();
            tessellator.draw();

        }

        GlStateManager.popMatrix();
    }
}
