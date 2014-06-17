package heero.mc.mod.wakcraft.entity.creature;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.characteristic.CharacteristicsManager.CHARACTERISTIC;
import heero.mc.mod.wakcraft.client.renderer.entity.RendererWCreature;
import heero.mc.mod.wakcraft.entity.property.CharacteristicsProperty;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Gobbette extends GobballGeneric {
	public Gobbette(World world) {
		super(world);
	}

	@Override
	public void initCharacteristics(CharacteristicsProperty property) {
		property.set(CHARACTERISTIC.HEALTH, 110);
		property.set(CHARACTERISTIC.ACTION, 5);
		property.set(CHARACTERISTIC.MOVEMENT, 4);
		property.set(CHARACTERISTIC.WAKFU, 4);
		property.set(CHARACTERISTIC.INITIATIVE, 16);
		property.set(CHARACTERISTIC.DODGE, 3);

		property.set(CHARACTERISTIC.WATER_RES, 5);
		property.set(CHARACTERISTIC.EARTH_RES, 12);
		property.set(CHARACTERISTIC.AIR_RES, -7);
		property.set(CHARACTERISTIC.FIRE_RES, 5);
	}

	@SideOnly(Side.CLIENT)
	public static class RenderGobette extends RendererWCreature {
		private static final ResourceLocation texture = new ResourceLocation(WInfo.MODID, "textures/mobs/bouffette.png");

		public RenderGobette(ModelBase model, float shadowSize) {
			super(model, shadowSize);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity entity) {
			return texture;
		}
	}
}
