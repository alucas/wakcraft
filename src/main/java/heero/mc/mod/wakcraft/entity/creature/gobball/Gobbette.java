package heero.mc.mod.wakcraft.entity.creature.gobball;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.client.model.ModelGobbette;
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

public class Gobbette extends GobballGeneric {
    public Gobbette(World world) {
        super(world);
    }

    @Override
    public void initCharacteristics(CharacteristicsProperty property) {
        property.set(Characteristic.HEALTH, 110);
        property.set(Characteristic.ACTION, 5);
        property.set(Characteristic.MOVEMENT, 4);
        property.set(Characteristic.WAKFU, 4);
        property.set(Characteristic.INITIATIVE, 16);
        property.set(Characteristic.DODGE, 3);

        property.set(Characteristic.WATER_RES, 5);
        property.set(Characteristic.EARTH_RES, 12);
        property.set(Characteristic.AIR_RES, -7);
        property.set(Characteristic.FIRE_RES, 5);
    }

    @SideOnly(Side.CLIENT)
    public static class RenderGobbette extends RendererWCreature<Gobbette> {
        private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/mobs/bouffette.png");

        public RenderGobbette(final RenderManager renderManager, final ModelBase model, final float shadowSize) {
            super(renderManager, model, shadowSize);
        }

        @Override
        protected ResourceLocation getEntityTexture(Gobbette entity) {
            return texture;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class RenderFactoryGobbette implements IRenderFactory<Gobbette> {
        @Override
        public Render<? super Gobbette> createRenderFor(RenderManager manager) {
            return new Gobbette.RenderGobbette(manager, new ModelGobbette(), 0.5f);
        }
    }
}
