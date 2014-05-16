package heero.wakcraft.entity.monster;

import heero.wakcraft.WInfo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WhiteGobbly extends Gobball {
	public WhiteGobbly(World par1World) {
		super(par1World);
	}

	public WhiteGobbly createChild(EntityAgeable par1EntityAgeable) {
		return new WhiteGobbly(this.worldObj);
	}

	@SideOnly(Side.CLIENT)
	public static class RenderBouftonBlanc extends RenderLiving {
		private static final ResourceLocation bouftonBlanc = new ResourceLocation(
				WInfo.MODID, "textures/mobs/boufton.png");

		public RenderBouftonBlanc(ModelBase par1ModelBase, float par2) {
			super(par1ModelBase, par2);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity var1) {
			return bouftonBlanc;
		}

	}
}
