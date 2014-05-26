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

public class BlackGobbly extends Gobball {
	public BlackGobbly(World par1World) {
		super(par1World);
	}

	public BlackGobbly createChild(EntityAgeable par1EntityAgeable) {
		return new BlackGobbly(this.worldObj);
	}

	@SideOnly(Side.CLIENT)
	public static class RenderBouftonNoir extends RenderLiving {
		private static final ResourceLocation bouftonNoir = new ResourceLocation(
				WInfo.MODID, "textures/mobs/bouftonnoir.png");

		public RenderBouftonNoir(ModelBase par1ModelBase, float par2) {
			super(par1ModelBase, par2);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity var1) {
			return bouftonNoir;
		}

	}
}
