package heero.mc.mod.wakcraft.entity.creature.gobball;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.client.model.ModelGobballWC;
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

public class GobballWC extends GobballGeneric {
    public GobballWC(World world) {
        super(world);
    }

    @Override
    public void initCharacteristics(CharacteristicsProperty property) {
        property.set(Characteristic.HEALTH, 200);
        property.set(Characteristic.ACTION, 5);
        property.set(Characteristic.MOVEMENT, 4);
        property.set(Characteristic.WAKFU, 4);
        property.set(Characteristic.INITIATIVE, 18);
        property.set(Characteristic.LOCK, 10);
        property.set(Characteristic.DODGE, 13);
        property.set(Characteristic.BLOCK, 7);
        property.set(Characteristic.CRITICAL, 9);

        property.set(Characteristic.WATER_RES, 10);
        property.set(Characteristic.EARTH_ATT, 35);
        property.set(Characteristic.EARTH_RES, 20);
        property.set(Characteristic.AIR_RES, -5);
        property.set(Characteristic.FIRE_RES, 10);
    }

    @SideOnly(Side.CLIENT)
    public static class RenderGobballWC extends RendererWCreature<GobballWC> {
        private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/mobs/bouftoucg.png");

        public RenderGobballWC(final RenderManager renderManager, final ModelBase model, final float shadowSize) {
            super(renderManager, model, shadowSize);
        }

        @Override
        protected ResourceLocation getEntityTexture(GobballWC entity) {
            return texture;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class RenderFactoryGobballWC implements IRenderFactory<GobballWC> {
        @Override
        public Render<? super GobballWC> createRenderFor(RenderManager manager) {
            return new GobballWC.RenderGobballWC(manager, new ModelGobballWC(), 0.5f);
        }
    }
}
