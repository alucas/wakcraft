package heero.mc.mod.wakcraft.entity.creature.meow;

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

public class BowMeow extends MeowGeneric {
		public BowMeow(World world) {
			super(world);
		}

		@Override
		public void initCharacteristics(CharacteristicsProperty property) {
			property.set(Characteristic.HEALTH, 26);
			property.set(Characteristic.ACTION, 4);
			property.set(Characteristic.MOVEMENT, 4);
			property.set(Characteristic.WAKFU, 2);
			property.set(Characteristic.INITIATIVE, 15);
			property.set(Characteristic.LOCK, -20);
			property.set(Characteristic.DODGE, -10);

			property.set(Characteristic.WATER_RES, -10);
			property.set(Characteristic.EARTH_ATT, 10);
			property.set(Characteristic.EARTH_RES, 5);
			property.set(Characteristic.AIR_RES, 5);
			property.set(Characteristic.FIRE_RES, 5);
		}

		@SideOnly(Side.CLIENT)
		public static class RenderBowMeow extends RendererWCreature {
			private static final ResourceLocation texture = new ResourceLocation(WInfo.MODID.toLowerCase(), "textures/mobs/chachanoir.png");

			public RenderBowMeow(ModelBase model, float shadowSize) {
				super(model, shadowSize);
			}

			@Override
			protected ResourceLocation getEntityTexture(Entity entity) {
				return texture;
			}
		}
	}
