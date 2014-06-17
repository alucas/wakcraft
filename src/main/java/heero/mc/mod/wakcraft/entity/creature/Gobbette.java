package heero.mc.mod.wakcraft.entity.creature;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.client.renderer.entity.RendererWCreature;
import heero.mc.mod.wakcraft.entity.property.AbilitiesProperty;
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
	public void initAbility(AbilitiesProperty property) {
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
