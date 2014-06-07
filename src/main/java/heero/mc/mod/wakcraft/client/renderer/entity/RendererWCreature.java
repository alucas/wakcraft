package heero.mc.mod.wakcraft.client.renderer.entity;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.client.model.ModelFightArrow;
import heero.mc.mod.wakcraft.helper.FightHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public abstract class RendererWCreature extends RendererLivingEntity {
	static final float PIXEL_SIZE = 0.0625F;
	static final ResourceLocation TEXTURE = new ResourceLocation(WInfo.MODID.toLowerCase(), "textures/entity/fightarrow.png");

	ModelBase fightArrow = new ModelFightArrow();

	public RendererWCreature(ModelBase model, float shadowSize) {
		super(model, shadowSize);
	}

	@Override
	public void doRender(EntityLivingBase entity, double relativeX, double relativeY,
			double relativeZ, float rotationYaw, float partialTickTime) {
		super.doRender(entity, relativeX, relativeY, relativeZ, rotationYaw, partialTickTime);

		if (FightHelper.isFighter(entity) && FightHelper.isFighting(entity)) {
			doRenderFightArrow(entity, relativeX, relativeY, relativeZ, partialTickTime);
		}
	}

	public void doRenderFightArrow(EntityLivingBase entity,
			double relativeX, double relativeY, double relativeZ, float partialTickTime) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);

		renderLivingAt(entity, relativeX, relativeY + entity.height + 1.5, relativeZ);

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);

		doRenderModel(fightArrow, TEXTURE, entity, 0, 0, entity.ticksExisted, 0, 0, PIXEL_SIZE);

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	protected void doRenderModel(ModelBase model, ResourceLocation texture,
			EntityLivingBase entity, float limbSwing, float limbSwingAmount,
			float age, float RotationYaw, float RotationPitch, float pixelSize) {
		this.bindTexture(texture);

		if (!entity.isInvisible()) {
			model.render(entity, limbSwing, limbSwingAmount, age, RotationYaw, RotationPitch, pixelSize);
		} else if (!entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)) {
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
			GL11.glDepthMask(false);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);

			model.render(entity, limbSwing, limbSwingAmount, age, RotationYaw, RotationPitch, pixelSize);

			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glPopMatrix();
			GL11.glDepthMask(true);
		} else {
			model.setRotationAngles(limbSwing, limbSwingAmount, age, RotationYaw, RotationPitch, pixelSize, entity);
		}
	}
}
