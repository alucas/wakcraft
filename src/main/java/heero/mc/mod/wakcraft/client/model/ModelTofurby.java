package heero.mc.mod.wakcraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTofurby extends ModelBase {

    ModelRenderer Corps;
    ModelRenderer Chouchou;
    ModelRenderer Plume1, Plume2, Plume3;
    ModelRenderer AileDroite;
    ModelRenderer AileGauche;
    ModelRenderer Bec;
    ModelRenderer PatteDroite;
    ModelRenderer PatteGauche;

    public ModelTofurby() {
        textureWidth = 512;
        textureHeight = 512;

        Corps = new ModelRenderer(this, 185, 114);
        Corps.addBox(0F, 0F, 0F, 14, 21, 14);
        Corps.setRotationPoint(-6F, 3F, -6F);
        Corps.setTextureSize(512, 512);
        Corps.mirror = true;
        setRotation(Corps, 0F, 0F, 0F);

        Bec = new ModelRenderer(this, 206, 163);
        Bec.addBox(0F, 0F, 0F, 3, 2, 2);
        Bec.setRotationPoint(-0.6F, 8F, -6F);
        Bec.setTextureSize(512, 512);
        Bec.mirror = true;
        setRotation(Bec, -0.7807508F, 0F, -0.0174533F);

        Chouchou = new ModelRenderer(this, 205, 90);
        Chouchou.addBox(0F, 0F, 0F, 2, 1, 2);
        Chouchou.setRotationPoint(0F, 2F, 0F);
        Chouchou.setTextureSize(512, 512);
        Chouchou.mirror = true;
        setRotation(Chouchou, 0F, 0F, 0F);

        Plume1 = new ModelRenderer(this, 193, 71);
        Plume1.addBox(0F, 0F, 0F, 2, 2, 1);
        Plume1.setRotationPoint(0F, 0F, 0.2F);
        Plume1.setTextureSize(512, 512);
        Plume1.mirror = true;
        setRotation(Plume1, -0.0371786F, 0.0371786F, 0.4833219F);

        Plume2 = new ModelRenderer(this, 210, 75);
        Plume2.addBox(0F, 0F, 0F, 2, 2, 1);
        Plume2.setRotationPoint(1.3F, 0F, 0.2F);
        Plume2.setTextureSize(512, 512);
        Plume2.mirror = true;
        setRotation(Plume2, 0F, -0.4461433F, 0.7435722F);

        Plume3 = new ModelRenderer(this, 226, 75);
        Plume3.addBox(0F, 0F, 0F, 2, 2, 1);
        Plume3.setRotationPoint(0.2F, 0F, 1F);
        Plume3.setTextureSize(512, 512);
        Plume3.mirror = true;
        setRotation(Plume3, 0F, 1.07818F, 0.669215F);

        AileDroite = new ModelRenderer(this, 120, 129);
        AileDroite.addBox(0F, 0F, 0F, 2, 2, 2);
        AileDroite.setRotationPoint(-7.9F, 10F, 0F);
        AileDroite.setTextureSize(512, 512);
        AileDroite.mirror = true;
        setRotation(AileDroite, 0F, 0F, 0.1745329F);

        AileGauche = new ModelRenderer(this, 285, 127);
        AileGauche.addBox(0F, 0F, 0F, 2, 2, 2);
        AileGauche.setRotationPoint(7.9F, 10F, 0F);
        AileGauche.setTextureSize(512, 512);
        AileGauche.mirror = true;
        setRotation(AileGauche, 0F, 0F, -0.1745329F);

        PatteDroite = new ModelRenderer(this, 187, 185);
        PatteDroite.addBox(0F, 0F, 0F, 2, 3, 1);
        PatteDroite.setRotationPoint(-5F, 21F, -7F);
        PatteDroite.setTextureSize(512, 512);
        PatteDroite.mirror = true;
        setRotation(PatteDroite, 0F, 0F, 0F);

        PatteGauche = new ModelRenderer(this, 228, 186);
        PatteGauche.addBox(0F, 0F, 0F, 2, 3, 1);
        PatteGauche.setRotationPoint(5F, 21F, -7F);
        PatteGauche.setTextureSize(512, 512);
        PatteGauche.mirror = true;
        setRotation(PatteGauche, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Corps.render(f5);
        Chouchou.render(f5);
        Plume1.render(f5);
        Plume2.render(f5);
        Plume3.render(f5);
        AileDroite.render(f5);
        AileGauche.render(f5);
        Bec.render(f5);
        PatteDroite.render(f5);
        PatteGauche.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
