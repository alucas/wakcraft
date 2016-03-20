package heero.mc.mod.wakcraft.entity.creature.tofu;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.client.model.ModelBabyTofu;
import heero.mc.mod.wakcraft.client.renderer.entity.RendererWCreature;
import heero.mc.mod.wakcraft.entity.property.CharacteristicsProperty;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BabyTofu extends TofuGeneric {

    public BabyTofu(World world) {
        super(world);
    }

    @Override
    public void initCharacteristics(CharacteristicsProperty property) {
        property.set(Characteristic.HEALTH, 35);
        property.set(Characteristic.ACTION, 4);
        property.set(Characteristic.MOVEMENT, 5);
        property.set(Characteristic.WAKFU, 4);
        property.set(Characteristic.INITIATIVE, 1);
        property.set(Characteristic.LOCK, -16);
        property.set(Characteristic.DODGE, -12);
        property.set(Characteristic.CRITICAL, 5);

        property.set(Characteristic.WATER_RES, 2);
        property.set(Characteristic.EARTH_RES, 2);
        property.set(Characteristic.AIR_RES, 4);
        property.set(Characteristic.FIRE_RES, -8);

        property.set(Characteristic.AIR_ATT, 5);

    }

    @SideOnly(Side.CLIENT)
    public static class RenderBabyTofu extends RendererWCreature<BabyTofu> {
        private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/mobs/babytofu.png");

        public RenderBabyTofu(final RenderManager renderManager, final ModelBase model, final float shadowSize) {
            super(renderManager, model, shadowSize);
        }

        @Override
        protected ResourceLocation getEntityTexture(BabyTofu entity) {
            return texture;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class RenderFactoryBabyTofu implements IRenderFactory<BabyTofu> {
        @Override
        public Render<? super BabyTofu> createRenderFor(RenderManager manager) {
            return new BabyTofu.RenderBabyTofu(manager, new ModelBabyTofu(), 0.5f);
        }
    }
}
