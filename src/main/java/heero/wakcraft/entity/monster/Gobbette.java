package heero.wakcraft.entity.monster;

import heero.wakcraft.WakcraftInfo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Gobbette extends Gobball {
	public Gobbette(World par1World) {
		super(par1World);
	}

	public Gobbette createChild(EntityAgeable par1EntityAgeable) {
		return new Gobbette(this.worldObj);
	}

	@SideOnly(Side.CLIENT)
	public static class RenderBouffette extends RenderLiving {
		private static final ResourceLocation bouffette = new ResourceLocation(
				WakcraftInfo.MODID, "textures/mobs/bouffette.png");

		public RenderBouffette(ModelBase par1ModelBase, float par2) {
			super(par1ModelBase, par2);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity var1) {
			return bouffette;
		}

	}
}
