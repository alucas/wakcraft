package heero.mc.mod.wakcraft.client.renderer.fight;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public class FighterRenderer {
	private Minecraft mc;
	private float fovModifierHandPrev;
	private float fovModifierHand;
	private float fovMultiplierTemp;
	private ItemRenderer itemRenderer;
	private DynamicTexture lightmapTexture;
	private ResourceLocation locationLightMap;

	public FighterRenderer (Minecraft minecraft, ItemRenderer itemRenderer) {
		this.mc = minecraft;
		this.itemRenderer = itemRenderer;
		this.lightmapTexture = new DynamicTexture(16, 16);
		this.locationLightMap = minecraft.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
	}

	public void renderHand(float partialTick, int renderPass) {
		if (mc.entityRenderer.debugViewDirection <= 0) {
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			float f1 = 0.07F;

			if (mc.gameSettings.anaglyph) {
				GL11.glTranslatef((float) (-(renderPass * 2 - 1)) * f1, 0.0F, 0.0F);
			}

			Project.gluPerspective(this.getFOVModifier(partialTick, false), (float) mc.displayWidth / (float) mc.displayHeight, 0.05F, mc.gameSettings.renderDistanceChunks * 32.0F);

			if (mc.playerController.enableEverythingIsScrewedUpMode()) {
				float f2 = 0.6666667F;
				GL11.glScalef(1.0F, f2, 1.0F);
			}

			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();

			if (mc.gameSettings.anaglyph) {
				GL11.glTranslatef((float) (renderPass * 2 - 1) * 0.1F, 0.0F, 0.0F);
			}

			GL11.glPushMatrix();
			this.hurtCameraEffect(partialTick);

			if (mc.gameSettings.viewBobbing) {
				this.setupViewBobbing(partialTick);
			}

			if (mc.gameSettings.thirdPersonView == 0
					&& !mc.renderViewEntity.isPlayerSleeping()
					&& !mc.gameSettings.hideGUI
					&& !mc.playerController
							.enableEverythingIsScrewedUpMode()) {
//				this.enableLightmap((double) partialTick);
				this.itemRenderer.renderItemInFirstPerson(partialTick);
//				this.disableLightmap((double) partialTick);
			}

			GL11.glPopMatrix();

			if (mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping()) {
				this.itemRenderer.renderOverlays(partialTick);
				this.hurtCameraEffect(partialTick);
			}

			if (mc.gameSettings.viewBobbing) {
				this.setupViewBobbing(partialTick);
			}
		}
	}

	private float getFOVModifier(float partialTick, boolean fov) {
		if (mc.entityRenderer.debugViewDirection > 0) {
			return 90.0F;
		} else {
			EntityLivingBase entityplayer = (EntityLivingBase) mc.renderViewEntity;
			float f1 = 70.0F;

			if (fov) {
				f1 += mc.gameSettings.fovSetting * 40.0F;
				f1 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * partialTick;
			}

			if (entityplayer.getHealth() <= 0.0F) {
				float f2 = (float) entityplayer.deathTime + partialTick;
				f1 /= (1.0F - 500.0F / (f2 + 500.0F)) * 2.0F + 1.0F;
			}

			Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(mc.theWorld, entityplayer, partialTick);

			if (block.getMaterial() == Material.water) {
				f1 = f1 * 60.0F / 70.0F;
			}

			return f1;
		}
	}

	public void updateFovModifierHand(EntityPlayerSP player) {
		if (mc.renderViewEntity instanceof EntityPlayerSP) {
			EntityPlayerSP entityplayersp = (EntityPlayerSP) mc.renderViewEntity;
			this.fovMultiplierTemp = entityplayersp.getFOVMultiplier();
		} else {
			this.fovMultiplierTemp = player.getFOVMultiplier();
		}

		this.fovModifierHandPrev = this.fovModifierHand;
		this.fovModifierHand += (this.fovMultiplierTemp - this.fovModifierHand) * 0.5F;

		if (this.fovModifierHand > 1.5F) {
			this.fovModifierHand = 1.5F;
		}

		if (this.fovModifierHand < 0.1F) {
			this.fovModifierHand = 0.1F;
		}
	}

	private void hurtCameraEffect(float par1) {
		EntityLivingBase entitylivingbase = mc.renderViewEntity;
		float f1 = (float) entitylivingbase.hurtTime - par1;
		float f2;

		if (entitylivingbase.getHealth() <= 0.0F) {
			f2 = (float) entitylivingbase.deathTime + par1;
			GL11.glRotatef(40.0F - 8000.0F / (f2 + 200.0F), 0.0F, 0.0F, 1.0F);
		}

		if (f1 >= 0.0F) {
			f1 /= (float) entitylivingbase.maxHurtTime;
			f1 = MathHelper.sin(f1 * f1 * f1 * f1 * (float) Math.PI);
			f2 = entitylivingbase.attackedAtYaw;
			GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-f1 * 14.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(f2, 0.0F, 1.0F, 0.0F);
		}
	}

	private void setupViewBobbing(float par1) {
		if (mc.renderViewEntity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) mc.renderViewEntity;
			float f1 = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
			float f2 = -(entityplayer.distanceWalkedModified + f1 * par1);
			float f3 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * par1;
			float f4 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * par1;
			GL11.glTranslatef(MathHelper.sin(f2 * (float) Math.PI) * f3 * 0.5F, -Math.abs(MathHelper.cos(f2 * (float) Math.PI) * f3), 0.0F);
			GL11.glRotatef(MathHelper.sin(f2 * (float) Math.PI) * f3 * 3.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(Math.abs(MathHelper.cos(f2 * (float) Math.PI - 0.2F) * f3) * 5.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);
		}
	}

	public void enableLightmap(double partialTick) {
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);

		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();

		float f = 1.0F / 256.0F;

		GL11.glScalef(f, f, f);
		GL11.glTranslatef(8.0F, 8.0F, 8.0F);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		mc.getTextureManager().bindTexture(this.locationLightMap);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	public void disableLightmap(double partialTick) {
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);

		GL11.glDisable(GL11.GL_TEXTURE_2D);

		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	public void updateRenderer(EntityPlayerSP player) {
		updateFovModifierHand(player);

		itemRenderer.updateEquippedItem();
	}
}