package heero.mc.mod.wakcraft.entity.monster;

import heero.mc.mod.wakcraft.WInfo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Gobbette extends Gobball {
	public Gobbette(World world) {
		super(world);
	}

	@Override
	public Gobbette createChild(EntityAgeable entity) {
		return new Gobbette(this.worldObj);
	}

	@SideOnly(Side.CLIENT)
	public static class RenderGobette extends RenderLiving {
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
