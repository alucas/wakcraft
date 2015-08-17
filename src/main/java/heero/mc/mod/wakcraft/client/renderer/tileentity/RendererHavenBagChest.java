package heero.mc.mod.wakcraft.client.renderer.tileentity;

import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBagChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.Calendar;

@SideOnly(Side.CLIENT)
public class RendererHavenBagChest extends TileEntitySpecialRenderer {
	private static final ResourceLocation textureTrapped = new ResourceLocation("textures/entity/chest/trapped.png");
	private static final ResourceLocation textureChristmas = new ResourceLocation("textures/entity/chest/christmas.png");
	private static final ResourceLocation textureNormal = new ResourceLocation("textures/entity/chest/normal.png");
	private ModelChest simpleChest = new ModelChest();
	private boolean isChristmas;

	public RendererHavenBagChest() {
		Calendar calendar = Calendar.getInstance();

		if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26) {
			this.isChristmas = true;
		}
	}

	public void renderTileEntityAt(TileEntityHavenBagChest entity, double x, double y, double z, float partialTicks, int destroyStage) {
		ModelChest modelchest = this.simpleChest;

		// 1 == trapped chest
		if (entity.getChestType() == 1) {
			this.bindTexture(textureTrapped);
		} else if (this.isChristmas) {
			this.bindTexture(textureChristmas);
		} else {
			this.bindTexture(textureNormal);
		}

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef((float) -90, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		float lidAngle = entity.prevLidAngle + (entity.lidAngle - entity.prevLidAngle) * partialTicks;

		lidAngle = 1.0F - lidAngle;
		lidAngle = 1.0F - lidAngle * lidAngle * lidAngle;
		modelchest.chestLid.rotateAngleX = -(lidAngle * (float) Math.PI / 2.0F);
		modelchest.renderAll();

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

    @Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float partialTicks, int destroyStage) {
		this.renderTileEntityAt((TileEntityHavenBagChest) entity, x, y, z, partialTicks, destroyStage);
	}
}