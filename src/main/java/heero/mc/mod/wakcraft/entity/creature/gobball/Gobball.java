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
	public static class RenderGobball extends RendererWCreature {
		private static final ResourceLocation texture = new ResourceLocation(WInfo.MODID.toLowerCase(), "textures/mobs/bouftou.png");

		public RenderGobball(ModelBase model, float shadowSize) {
			super(model, shadowSize);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity entity) {
			return texture;
		}
	}
}
