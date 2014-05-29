package heero.mc.mod.wakcraft.entity.monster;

import heero.mc.mod.wakcraft.WInfo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WhiteGobbly extends Gobball {
	public WhiteGobbly(World world) {
		super(world);
	}

	@SideOnly(Side.CLIENT)
	public static class RenderWhiteGobbly extends RenderLiving {
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
