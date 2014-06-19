package heero.mc.mod.wakcraft.entity.creature.gobball;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.client.renderer.entity.RendererWCreature;
import heero.mc.mod.wakcraft.entity.property.CharacteristicsProperty;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlackGobbly extends GobballGeneric {
	public BlackGobbly(World world) {
		super(world);
	}

	@Override
	public void initCharacteristics(CharacteristicsProperty property) {
		property.set(Characteristic.HEALTH, 29);
		property.set(Characteristic.ACTION, 4);
		property.set(Characteristic.MOVEMENT, 3);
		property.set(Characteristic.WAKFU, 4);
		property.set(Characteristic.INITIATIVE, -7);
		property.set(Characteristic.LOCK, -40);
		property.set(Characteristic.DODGE, -40);
		property.set(Characteristic.CRITICAL, 4);

		property.set(Characteristic.WATER_RES, 5);
		property.set(Characteristic.EARTH_ATT, 15);
		property.set(Characteristic.EARTH_RES, 15);
		property.set(Characteristic.AIR_RES, -20);
		property.set(Characteristic.FIRE_RES, 10);
	}

	@SideOnly(Side.CLIENT)
	public static class RenderBlackGobbly extends RendererWCreature {
		private static final ResourceLocation texture = new ResourceLocation(WInfo.MODID, "textures/mobs/bouftonnoir.png");

		public RenderBlackGobbly(ModelBase model, float shadowSize) {
			super(model, shadowSize);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity entity) {
			return texture;
		}
	}
}
