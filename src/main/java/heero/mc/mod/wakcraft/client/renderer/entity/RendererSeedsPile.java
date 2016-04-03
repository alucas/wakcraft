package heero.mc.mod.wakcraft.client.renderer.entity;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.entity.misc.EntitySeedsPile;
import heero.mc.mod.wakcraft.item.ItemWCreatureSeeds;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RendererSeedsPile extends Render<EntitySeedsPile> {
    protected static final ResourceLocation generic = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/entity/seeds_pile.png");
    protected static final ResourceLocation gobball = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/entity/seeds_pile_gobball.png");

    protected final RenderItem renderItem;

    public RendererSeedsPile(RenderManager renderManager, RenderItem renderItem) {
        super(renderManager);

        this.renderItem = renderItem;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySeedsPile seedsPileEntity) {
        ItemWCreatureSeeds seeds = seedsPileEntity.getItemSeeds();

        if (seeds.equals(WItems.gobballSeed)) {
            return gobball;
        }

        return generic;
    }

    @Override
    public void doRender(EntitySeedsPile entity, double relativeX, double relativeY,
                         double relativeZ, float rotationYaw, float partialTickTime) {
        bindEntityTexture(entity);

        GlStateManager.pushMatrix();
        GlStateManager.translate(relativeX + 0.5, relativeY + 0.5, relativeZ + 0.5);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-0.5, -0.5, 0).tex(0, 1).endVertex();
        worldRenderer.pos(+0.5, -0.5, 0).tex(1, 1).endVertex();
        worldRenderer.pos(+0.5, +0.5, 0).tex(1, 0).endVertex();
        worldRenderer.pos(-0.5, +0.5, 0).tex(0, 0).endVertex();
        tessellator.draw();

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();

        super.doRender(entity, relativeX, relativeY, relativeZ, rotationYaw, partialTickTime);
    }
}
