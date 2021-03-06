// Date: 09/04/2014 19:34:02
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package heero.mc.mod.wakcraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPhoenix extends ModelBase {
    // fields
    ModelRenderer socleDown;
    ModelRenderer socleUp;
    ModelRenderer legLeft;
    ModelRenderer legRight;
    ModelRenderer toeLeft1;
    ModelRenderer toeLeft2;
    ModelRenderer toeRight1;
    ModelRenderer toeRight2;
    ModelRenderer body;
    ModelRenderer head;
    ModelRenderer wingLeft;
    ModelRenderer wingRight;
    ModelRenderer beak1;
    ModelRenderer beak2;

    public ModelPhoenix() {
        textureWidth = 128;
        textureHeight = 64;

        socleDown = new ModelRenderer(this, 0, 0);
        socleDown.addBox(0F, 0F, 0F, 16, 8, 16);
        socleDown.setRotationPoint(-8F, 16F, -8F);
        socleDown.setTextureSize(128, 64);
        socleDown.mirror = true;
        setRotation(socleDown, 0F, 0F, 0F);
        socleUp = new ModelRenderer(this, 0, 24);
        socleUp.addBox(0F, 0F, 0F, 10, 8, 10);
        socleUp.setRotationPoint(-5F, 8F, -5F);
        socleUp.setTextureSize(128, 64);
        socleUp.mirror = true;
        setRotation(socleUp, 0F, 0F, 0F);
        legLeft = new ModelRenderer(this, 64, 20);
        legLeft.addBox(0F, 0F, 0F, 1, 3, 1);
        legLeft.setRotationPoint(1F, 5F, -1F);
        legLeft.setTextureSize(128, 64);
        legLeft.mirror = true;
        setRotation(legLeft, 0F, 0F, 0F);
        legRight = new ModelRenderer(this, 64, 20);
        legRight.addBox(0F, 0F, 0F, 1, 3, 1);
        legRight.setRotationPoint(-2F, 5F, -2F);
        legRight.setTextureSize(128, 64);
        legRight.mirror = true;
        setRotation(legRight, 0F, 0F, 0F);
        toeLeft1 = new ModelRenderer(this, 68, 20);
        toeLeft1.addBox(0F, 0F, -0.5F, 3, 1, 1);
        toeLeft1.setRotationPoint(1.5F, 7F, -1F);
        toeLeft1.setTextureSize(128, 64);
        toeLeft1.mirror = true;
        setRotation(toeLeft1, 0F, 1.308997F, 0F);
        toeLeft2 = new ModelRenderer(this, 68, 20);
        toeLeft2.addBox(0F, 0F, -0.5F, 3, 1, 1);
        toeLeft2.setRotationPoint(1.5F, 7F, -1F);
        toeLeft2.setTextureSize(128, 64);
        toeLeft2.mirror = true;
        setRotation(toeLeft2, 0F, 1.832596F, 0F);
        toeRight1 = new ModelRenderer(this, 68, 20);
        toeRight1.addBox(0F, 0F, -0.5F, 3, 1, 1);
        toeRight1.setRotationPoint(-1.5F, 7F, -1F);
        toeRight1.setTextureSize(128, 64);
        toeRight1.mirror = true;
        setRotation(toeRight1, 0F, 1.308997F, 0F);
        toeRight2 = new ModelRenderer(this, 68, 20);
        toeRight2.addBox(0F, 0F, -0.5F, 3, 1, 1);
        toeRight2.setRotationPoint(-1.5F, 7F, -1F);
        toeRight2.setTextureSize(128, 64);
        toeRight2.mirror = true;
        setRotation(toeRight2, 0F, 1.832596F, 0F);
        body = new ModelRenderer(this, 40, 24);
        body.addBox(0F, 0F, 0F, 4, 4, 4);
        body.setRotationPoint(-2F, 1F, -3F);
        body.setTextureSize(128, 64);
        body.mirror = true;
        setRotation(body, 0F, 0F, 0F);
        head = new ModelRenderer(this, 0, 42);
        head.addBox(0F, 0F, 0F, 6, 5, 5);
        head.setRotationPoint(-3F, -4F, -4F);
        head.setTextureSize(128, 64);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);
        wingLeft = new ModelRenderer(this, 64, 0);
        wingLeft.addBox(0F, 0F, 0F, 0, 13, 7);
        wingLeft.setRotationPoint(2F, -8F, 1F);
        wingLeft.setTextureSize(128, 64);
        wingLeft.mirror = true;
        setRotation(wingLeft, 0F, 0F, 0F);
        wingRight = new ModelRenderer(this, 64, 0);
        wingRight.addBox(0F, 0F, 0F, 0, 13, 7);
        wingRight.setRotationPoint(-2F, -8F, 1F);
        wingRight.setTextureSize(128, 64);
        wingRight.mirror = true;
        setRotation(wingRight, 0F, 0F, 0F);
        beak1 = new ModelRenderer(this, 22, 42);
        beak1.addBox(0F, 0F, 0F, 4, 4, 3);
        beak1.setRotationPoint(-2F, -3F, -7F);
        beak1.setTextureSize(128, 64);
        beak1.mirror = true;
        setRotation(beak1, 0.1745329F, 0F, 0F);
        beak2 = new ModelRenderer(this, 36, 42);
        beak2.addBox(0F, -3F, 0F, 2, 3, 2);
        beak2.setRotationPoint(-1F, 0F, -8F);
        beak2.setTextureSize(128, 64);
        beak2.mirror = true;
        setRotation(beak2, -0.418879F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3,
                       float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        socleDown.render(f5);
        socleUp.render(f5);
        legLeft.render(f5);
        legRight.render(f5);
        toeLeft1.render(f5);
        toeLeft2.render(f5);
        toeRight1.render(f5);
        toeRight2.render(f5);
        body.render(f5);
        head.render(f5);
        wingLeft.render(f5);
        wingRight.render(f5);
        beak1.render(f5);
        beak2.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3,
                                  float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
