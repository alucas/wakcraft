package heero.mc.mod.wakcraft.client.renderer.entity;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.entity.misc.EntitySeedsPile;
import heero.mc.mod.wakcraft.item.ItemWCreatureSeeds;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RendererSeedsPile extends Render {
    private static final ResourceLocation generic = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/entity/seedpile.png");
    private static final ResourceLocation gobball = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/entity/gobballSeedPile.png");

    public RendererSeedsPile(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        if (entity instanceof EntitySeedsPile) {
            ItemWCreatureSeeds seeds = ((EntitySeedsPile) entity).getItemSeeds();

            if (seeds.equals(WItems.gobballSeed)) {
                return gobball;
            }
        }

        return generic;
    }

    @Override
    public void doRender(Entity entity, double relativeX, double relativeY,
                         double relativeZ, float rotationYaw, float partialTickTime) {
        doRender((EntitySeedsPile) entity, relativeX, relativeY, relativeZ, rotationYaw, partialTickTime);
    }

    public void doRender(EntitySeedsPile entity, double relativeX, double relativeY,
                         double relativeZ, float rotationYaw, float partialTickTime) {
        bindEntityTexture(entity);

        GL11.glPushMatrix();
        GL11.glTranslatef((float) relativeX + 0.5F, (float) relativeY, (float) relativeZ + 0.5F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(0.5F, 0.5F, 0.5F);

        renderSeedPile(entity, partialTickTime);

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    private void renderSeedPile(EntitySeedsPile par1EntityItem, float partialTickTime) {
        Tessellator tessellator = Tessellator.getInstance();

        float xOffset = 0.5F;
        float yOffset = 0.25F;

        if (this.renderManager.options.fancyGraphics) {
            GL11.glPushMatrix();
            GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);

            float thickness = 0.0625F;

            GL11.glTranslatef(-xOffset, -yOffset, -thickness / 2);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

//			ItemRenderer.renderItemIn2D(tessellator, 1, 0, 0, 1, 16, 16, thickness);

            GL11.glPopMatrix();
        } else {
            GL11.glPushMatrix();
            GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

//            TODO
//			tessellator.startDrawingQuads();
//			tessellator.setNormal(0.0F, 1.0F, 0.0F);
//			tessellator.addVertexWithUV((double)(0.0F - xOffset), (double)(0.0F - yOffset), 0.0D, (double)0, (double)1);
//			tessellator.addVertexWithUV((double)(1.0F - xOffset), (double)(0.0F - yOffset), 0.0D, (double)1, (double)1);
//			tessellator.addVertexWithUV((double)(1.0F - xOffset), (double)(1.0F - yOffset), 0.0D, (double)1, (double)0);
//			tessellator.addVertexWithUV((double)(0.0F - xOffset), (double)(1.0F - yOffset), 0.0D, (double)0, (double)0);
            tessellator.draw();

            GL11.glPopMatrix();
        }
    }
}
