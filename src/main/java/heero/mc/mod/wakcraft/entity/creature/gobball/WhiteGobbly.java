package heero.mc.mod.wakcraft.entity.creature.gobball;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.client.renderer.entity.RendererWCreature;
import heero.mc.mod.wakcraft.entity.property.CharacteristicsProperty;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WhiteGobbly extends GobballGeneric {
	public WhiteGobbly(World world) {
		super(world);
	}

	@Override
	public void initCharacteristics(CharacteristicsProperty property) {
		property.set(Characteristic.HEALTH, 29);
		property.set(Characteristic.ACTION, 4);
		property.set(Characteristic.MOVEMENT, 3);
		property.set(Characteristic.WAKFU, 4);
		property.set(Characteristic.INITIATIVE, -8);
		property.set(Characteristic.LOCK, -40);
		property.set(Characteristic.DODGE, -40);

		property.set(Characteristic.WATER_RES, 5);
		property.set(Characteristic.EARTH_ATT, 10);
		property.set(Characteristic.EARTH_RES, 15);
		property.set(Characteristic.AIR_RES, -20);
		property.set(Characteristic.FIRE_RES, 5);
	}

	@SideOnly(Side.CLIENT)
	public static class RenderWhiteGobbly extends RendererWCreature {
		private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/mobs/boufton.png");

		public RenderWhiteGobbly(final RenderManager renderManager, final ModelBase model, final float shadowSize) {
			super(renderManager, model, shadowSize);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity entity) {
			return texture;
		}
	}
}
