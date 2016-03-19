package heero.mc.mod.wakcraft.client.renderer.world;

import heero.mc.mod.wakcraft.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class EndSkyRenderer extends IRenderHandler {
    private static final ResourceLocation locationEndSkyPng = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/environment/havenbag_sky.png");

    @Override
    @SideOnly(Side.CLIENT)
    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        GlStateManager.disableFog();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.depthMask(false);
        //this.renderEngine.bindTexture(locationEndSkyPng);
        mc.getTextureManager().bindTexture(locationEndSkyPng);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        for (int i = 0; i < 6; ++i) {
            GlStateManager.pushMatrix();

            if (i == 1) {
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            } else if (i == 2) {
                GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            } else if (i == 3) {
                GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            } else if (i == 4) {
                GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            } else if (i == 5) {
                GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
            }

            worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.color(0x28, 0x28, 0x28, 0x28);
            worldrenderer.pos(-100.0D, -100.0D, -100.0D).tex(0.0D, 0.0D).color(40, 40, 40, 255).endVertex();
            worldrenderer.pos(-100.0D, -100.0D, 100.0D).tex(0.0D, 16.0D).color(40, 40, 40, 255).endVertex();
            worldrenderer.pos(100.0D, -100.0D, 100.0D).tex(16.0D, 16.0D).color(40, 40, 40, 255).endVertex();
            worldrenderer.pos(100.0D, -100.0D, -100.0D).tex(16.0D, 0.0D).color(40, 40, 40, 255).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
        }

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
    }
}
