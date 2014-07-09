package heero.mc.mod.wakcraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBabyTofu extends ModelBase{
	  //fields
    ModelRenderer Corps;
    ModelRenderer AileDroite;
    ModelRenderer AileGauche;
    ModelRenderer Bec;
    ModelRenderer PatteGauche;
    ModelRenderer PatteDroite;
    ModelRenderer Plume1;
    ModelRenderer Plume2;
    ModelRenderer Plume3;
  
  public ModelBabyTofu()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Corps = new ModelRenderer(this, 20, 10);
      Corps.addBox(0F, 0F, 0F, 5, 5, 5);
      Corps.setRotationPoint(0F, 18F, 0F);
      Corps.setTextureSize(64, 32);
      Corps.mirror = true;
      setRotation(Corps, 0F, 0F, 0F);
      AileDroite = new ModelRenderer(this, 12, 15);
      AileDroite.addBox(0F, 0F, 0F, 1, 1, 1);
      AileDroite.setRotationPoint(-1F, 21F, 2F);
      AileDroite.setTextureSize(64, 32);
      AileDroite.mirror = true;
      setRotation(AileDroite, -0.3490659F, -0.3490659F, -0.0174533F);
      AileGauche = new ModelRenderer(this, 43, 15);
      AileGauche.addBox(0F, 0F, 0F, 1, 1, 1);
      AileGauche.setRotationPoint(5F, 21F, 2F);
      AileGauche.setTextureSize(64, 32);
      AileGauche.mirror = true;
      setRotation(AileGauche, -0.3490659F, 0.3490659F, 0.0174533F);
      Bec = new ModelRenderer(this, 27, 21);
      Bec.addBox(0F, 0F, 0F, 2, 1, 1);
      Bec.setRotationPoint(1.5F, 21.7F, -0.75F);
      Bec.setTextureSize(64, 32);
      Bec.mirror = true;
      setRotation(Bec, 0.7716627F, 0F, 0F);
      PatteGauche = new ModelRenderer(this, 38, 22);
      PatteGauche.addBox(0F, 0F, 0F, 2, 1, 3);
      PatteGauche.setRotationPoint(3F, 23F, -1.3F);
      PatteGauche.setTextureSize(64, 32);
      PatteGauche.mirror = true;
      setRotation(PatteGauche, 0F, -0.1396263F, 0F);
      PatteDroite = new ModelRenderer(this, 13, 22);
      PatteDroite.addBox(0F, 0F, 0F, 2, 1, 3);
      PatteDroite.setRotationPoint(0F, 23F, -1F);
      PatteDroite.setTextureSize(64, 32);
      PatteDroite.mirror = true;
      setRotation(PatteDroite, 0F, 0.1396263F, 0F);
      Plume1 = new ModelRenderer(this, 36, 4);
      Plume1.addBox(0F, 0F, 0F, 1, 4, 0);
      Plume1.setRotationPoint(3F, 14.2F, 3F);
      Plume1.setTextureSize(64, 32);
      Plume1.mirror = true;
      setRotation(Plume1, -0.1047198F, 0.0872665F, 0.2443461F);
      Plume2 = new ModelRenderer(this, 20, 4);
      Plume2.addBox(0F, 0F, 0F, 1, 3, 0);
      Plume2.setRotationPoint(0.8F, 15.3F, 3F);
      Plume2.setTextureSize(64, 32);
      Plume2.mirror = true;
      setRotation(Plume2, -0.122173F, 0F, -0.1745329F);
      Plume3 = new ModelRenderer(this, 28, 2);
      Plume3.addBox(0F, 0F, 0F, 1, 4, 0);
      Plume3.setRotationPoint(1.8F, 14.5F, 3.5F);
      Plume3.setTextureSize(64, 32);
      Plume3.mirror = true;
      setRotation(Plume3, -0.1745329F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Corps.render(f5);
    AileDroite.render(f5);
    AileGauche.render(f5);
    Bec.render(f5);
    PatteGauche.render(f5);
    PatteDroite.render(f5);
    Plume1.render(f5);
    Plume2.render(f5);
    Plume3.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.AileDroite.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F;
    this.AileGauche.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
    this.AileDroite.rotateAngleZ = 0.0F;
    this.AileGauche.rotateAngleZ = 0.0F;
    this.PatteDroite.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    this.PatteGauche.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
    this.PatteDroite.rotateAngleY = 0.0F;
    this.PatteGauche.rotateAngleY = 0.0F;
  }

}
