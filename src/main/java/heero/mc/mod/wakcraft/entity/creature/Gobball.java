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

public class Gobball extends GobballGeneric {
	public Gobball(World world) {
		super(world);
	}

	@Override
	public void initAbility(AbilitiesProperty property) {
		property.set(ABILITY.HEALTH, 84);
		property.set(ABILITY.ACTION, 5);
		property.set(ABILITY.MOVEMENT, 4);
		property.set(ABILITY.WAKFU, 4);
		property.set(ABILITY.INITIATIVE, 4);
		property.set(ABILITY.LOCK, -7);
		property.set(ABILITY.DODGE, -6);

		property.set(ABILITY.WATER_RES, 4);
		property.set(ABILITY.EARTH_ATT, 8);
		property.set(ABILITY.EARTH_RES, 8);
		property.set(ABILITY.AIR_RES, -7);
		property.set(ABILITY.FIRE_RES, 4);
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
