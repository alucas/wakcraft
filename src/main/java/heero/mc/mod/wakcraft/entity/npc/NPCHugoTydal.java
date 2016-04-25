package heero.mc.mod.wakcraft.entity.npc;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.client.model.npc.ModelHugoTydal;
import heero.mc.mod.wakcraft.client.renderer.entity.RendererWCreature;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NPCHugoTydal extends EntityWNPC {

    public NPCHugoTydal(World world) {
        super(world);

        // 1.2 block wide/tall
        this.setSize(0.6F, 1.8F);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    /**
     * Called frequently so the entity can update its state every tick as
     * required. For example, zombies and skeletons use this to react to
     * sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow,
     * gets into the saddle on a pig.
     */
    @Override
    public boolean interact(EntityPlayer player) {
        return super.interact(player);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected String getLivingSound() {
        return "mob.chicken.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound() {
        return "mob.chicken.say";
    }

    @SideOnly(Side.CLIENT)
    public static class RenderHugoTydal extends RendererWCreature<NPCHugoTydal> {
        private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/npc/hugo_tydal.png");

        public RenderHugoTydal(final RenderManager renderManager, final ModelBase model, final float shadowSize) {
            super(renderManager, model, shadowSize);

            addLayer(new LayerHeldItem(this));
        }

        @Override
        protected ResourceLocation getEntityTexture(NPCHugoTydal entity) {
            return texture;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class RenderFactoryHugoTydal implements IRenderFactory<NPCHugoTydal> {
        @Override
        public Render<? super NPCHugoTydal> createRenderFor(RenderManager manager) {
            return new RenderHugoTydal(manager, new ModelHugoTydal(), 0.5f);
        }
    }
}
