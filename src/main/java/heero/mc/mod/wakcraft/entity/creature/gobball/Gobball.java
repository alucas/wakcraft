package heero.mc.mod.wakcraft.entity.creature.gobball;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.client.model.ModelGobball;
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

public class Gobball extends GobballGeneric {
    public Gobball(World world) {
        super(world);
    }

    @Override
    public void initCharacteristics(CharacteristicsProperty property) {
        property.set(Characteristic.HEALTH, 84);
        property.set(Characteristic.ACTION, 5);
        property.set(Characteristic.MOVEMENT, 4);
        property.set(Characteristic.WAKFU, 4);
        property.set(Characteristic.INITIATIVE, 4);
        property.set(Characteristic.LOCK, -7);
        property.set(Characteristic.DODGE, -6);

        property.set(Characteristic.WATER_RES, 4);
        property.set(Characteristic.EARTH_ATT, 8);
        property.set(Characteristic.EARTH_RES, 8);
        property.set(Characteristic.AIR_RES, -7);
        property.set(Characteristic.FIRE_RES, 4);
    }

    @SideOnly(Side.CLIENT)
    public static class RenderGobball extends RendererWCreature<Gobball> {
        private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/mobs/bouftou.png");

        public RenderGobball(final RenderManager renderManager, final ModelBase model, final float shadowSize) {
            super(renderManager, model, shadowSize);
        }

        @Override
        protected ResourceLocation getEntityTexture(Gobball entity) {
            return texture;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class RenderFactoryGobball implements IRenderFactory<Gobball> {
        @Override
        public Render<? super Gobball> createRenderFor(RenderManager manager) {
            return new Gobball.RenderGobball(manager, new ModelGobball(), 0.5f);
        }
    }
}
