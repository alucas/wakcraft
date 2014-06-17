package heero.mc.mod.wakcraft.entity.creature;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.ability.AbilityManager.ABILITY;
import heero.mc.mod.wakcraft.client.renderer.entity.RendererWCreature;
import heero.mc.mod.wakcraft.entity.property.AbilitiesProperty;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WhiteGobbly extends GobballGeneric {
	public WhiteGobbly(World world) {
		super(world);
	}

	@Override
	public void initAbility(AbilitiesProperty property) {
		property.set(ABILITY.HEALTH, 29);
		property.set(ABILITY.ACTION, 4);
		property.set(ABILITY.MOVEMENT, 3);
		property.set(ABILITY.WAKFU, 4);
		property.set(ABILITY.INITIATIVE, -8);
		property.set(ABILITY.LOCK, -40);
		property.set(ABILITY.DODGE, -40);

		property.set(ABILITY.WATER_RES, 5);
		property.set(ABILITY.EARTH_ATT, 10);
		property.set(ABILITY.EARTH_RES, 15);
		property.set(ABILITY.AIR_RES, -20);
		property.set(ABILITY.FIRE_RES, 5);
	}

	@SideOnly(Side.CLIENT)
	public static class RenderWhiteGobbly extends RendererWCreature {
		private static final ResourceLocation texture = new ResourceLocation(WInfo.MODID, "textures/mobs/boufton.png");

		public RenderWhiteGobbly(ModelBase model, float shadowSize) {
			super(model, shadowSize);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity entity) {
			return texture;
		}
	}
}
