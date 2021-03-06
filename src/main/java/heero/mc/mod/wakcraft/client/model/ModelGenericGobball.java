// Date: 03/04/2014 23:47:34
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package heero.mc.mod.wakcraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelGenericGobball extends ModelBase {
    ModelRenderer legFR;
    ModelRenderer legBR;
    ModelRenderer legFL;
    ModelRenderer legBL;
    ModelRenderer body;

    public ModelGenericGobball() {
        textureWidth = 128;
        textureHeight = 64;

        legFR = new ModelRenderer(this, 0, 4);
        legFR.addBox(0F, 0F, 0F, 3, 5, 3);
        legFR.setRotationPoint(4F, 19F, -6F);
        legFR.setTextureSize(128, 64);
        legFR.mirror = true;
        setRotation(legFR, 0F, 0F, 0F);

        legBR = new ModelRenderer(this, 0, 4);
        legBR.addBox(0F, 0F, 0F, 3, 5, 3);
        legBR.setRotationPoint(4F, 19F, 3F);
        legBR.setTextureSize(128, 64);
        legBR.mirror = true;
        setRotation(legBR, 0F, 0F, 0F);

        legFL = new ModelRenderer(this, 0, 4);
        legFL.addBox(0F, 0F, 0F, 3, 5, 3);
        legFL.setRotationPoint(-7F, 19F, -6F);
        legFL.setTextureSize(128, 64);
        legFL.mirror = true;
        setRotation(legFL, 0F, 0F, 0F);

        legBL = new ModelRenderer(this, 0, 4);
        legBL.addBox(0F, 0F, 0F, 3, 5, 3);
        legBL.setRotationPoint(-7F, 19F, 3F);
        legBL.setTextureSize(128, 64);
        legBL.mirror = true;
        setRotation(legBL, 0F, 0F, 0F);

        body = new ModelRenderer(this, 0, 0);
        body.addBox(0F, 0F, 0F, 16, 14, 16);
        body.setRotationPoint(-8F, 7F, -8F);
        body.setTextureSize(128, 64);
        body.mirror = true;
        setRotation(body, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3,
                       float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);

        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        legFR.render(f5);
        legBR.render(f5);
        legFL.render(f5);
        legBL.render(f5);
        body.render(f5);
    }

    protected void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3,
                                  float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        this.legFR.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.legBR.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
        this.legFL.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
        this.legBL.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }

}
