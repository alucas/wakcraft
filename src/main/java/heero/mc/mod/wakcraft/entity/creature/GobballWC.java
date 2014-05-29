package heero.mc.mod.wakcraft.entity.creature;

import heero.mc.mod.wakcraft.WInfo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GobballWC extends Gobball {
	public GobballWC(World world) {
		super(world);
	}

	@SideOnly(Side.CLIENT)
	public static class RenderGobballWC extends RenderLiving {
		private static final ResourceLocation texture = new ResourceLocation(WInfo.MODID, "textures/mobs/bouftoucg.png");

		public RenderGobballWC(ModelBase model, float shadowSize) {
			super(model, shadowSize);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity entity) {
			return texture;
		}
	}
}
