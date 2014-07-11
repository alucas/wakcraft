package heero.mc.mod.wakcraft.entity.creature.tofu;

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

public class BabyTofu extends TofuGeneric{

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
	public static class RenderBabyTofu extends RendererWCreature {
		private static final ResourceLocation texture = new ResourceLocation(WInfo.MODID.toLowerCase(), "textures/mobs/babytofu.png");

		public RenderBabyTofu(ModelBase model, float shadowSize) {
			super(model, shadowSize);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity entity) {
			return texture;
		}
	}

}
