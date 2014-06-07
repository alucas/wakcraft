package heero.mc.mod.wakcraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelFightArrow extends ModelBase {
	ModelRenderer blade1;
	ModelRenderer blade2;
	ModelRenderer blade3;
	ModelRenderer blade4;

	public ModelFightArrow() {
		textureWidth = 64;
		textureHeight = 32;

		blade1 = new ModelRenderer(this, 0, 0);
		blade1.addBox(-2F, 0F, 0F, 4, 7, 1);
		blade1.setRotationPoint(0F, 0F, 0F);
		blade1.setTextureSize(64, 32);
		blade1.mirror = true;
		setRotation(blade1, 0F, 0F, 0F);

		blade2 = new ModelRenderer(this, 10, 0);
		blade2.addBox(-3F, 7F, 0F, 6, 1, 1);
		blade2.setRotationPoint(0F, 0F, 0F);
		blade2.setTextureSize(64, 32);
		blade2.mirror = true;
		setRotation(blade2, 0F, 0F, 0F);

		blade3 = new ModelRenderer(this, 10, 2);
		blade3.addBox(-2F, 8F, 0F, 4, 1, 1);
		blade3.setRotationPoint(0F, 0F, 0F);
		blade3.setTextureSize(64, 32);
		blade3.mirror = true;
		setRotation(blade3, 0F, 0F, 0F);

		blade4 = new ModelRenderer(this, 10, 4);
		blade4.addBox(-1F, 9F, 0F, 2, 1, 1);
		blade4.setRotationPoint(0F, 0F, 0F);
		blade4.setTextureSize(64, 32);
		blade4.mirror = true;
		setRotation(blade4, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float par1, float par2, float par3, float par4, float par5, float par6) {
		setRotationAngles(par1, par2, par3, par4, par5, par6, entity);

		blade1.render(par6);
		blade2.render(par6);
		blade3.render(par6);
		blade4.render(par6);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
		float posY = MathHelper.cos((float) (par3 * 9F / 360F * Math.PI)) * 0.1F;
		blade1.offsetY = posY;
		blade2.offsetY = posY;
		blade3.offsetY = posY;
		blade4.offsetY = posY;
	}
}
