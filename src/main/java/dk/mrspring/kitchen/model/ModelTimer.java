package dk.mrspring.kitchen.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTimer extends ModelBase
{
    //fields
    ModelRenderer base4;
    ModelRenderer arm;
    ModelRenderer base5;
    ModelRenderer base1;
    ModelRenderer base3;
    ModelRenderer base2;

    public ModelTimer()
    {
        textureWidth = 64;
        textureHeight = 32;

        base4 = new ModelRenderer(this, 0, 15);
        base4.addBox(0F, 0F, 0F, 4, 1, 1);
        base4.setRotationPoint(0F, 23F, 0F);
        base4.setTextureSize(64, 32);
        base4.mirror = true;
        setRotation(base4, 0F, 0F, 0F);
        arm = new ModelRenderer(this, 11, 0);
        arm.addBox(-0.5F, -2.5F, 0F, 1, 3, 1);
        arm.setRotationPoint(2F, 21F, -0.5F);
        arm.setTextureSize(64, 32);
        arm.mirror = true;
        setRotation(arm, 0F, 0F, 0F);
        base5 = new ModelRenderer(this, 0, 12);
        base5.addBox(0F, 0F, 0F, 4, 1, 1);
        base5.setRotationPoint(0F, 18F, 0F);
        base5.setTextureSize(64, 32);
        base5.mirror = true;
        setRotation(base5, 0F, 0F, 0F);
        base1 = new ModelRenderer(this, 0, 0);
        base1.addBox(0F, 0F, 0F, 4, 4, 1);
        base1.setRotationPoint(0F, 19F, 0F);
        base1.setTextureSize(64, 32);
        base1.mirror = true;
        setRotation(base1, 0F, 0F, 0F);
        base3 = new ModelRenderer(this, 0, 6);
        base3.addBox(0F, 0F, 0F, 1, 4, 1);
        base3.setRotationPoint(-1F, 19F, 0F);
        base3.setTextureSize(64, 32);
        base3.mirror = true;
        setRotation(base3, 0F, 0F, 0F);
        base2 = new ModelRenderer(this, 5, 6);
        base2.addBox(0F, 0F, 0F, 1, 4, 1);
        base2.setRotationPoint(4F, 19F, 0F);
        base2.setTextureSize(64, 32);
        base2.mirror = true;
        setRotation(base2, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float time)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        base1.render(f5);
        base2.render(f5);
        base3.render(f5);
        base4.render(f5);
        base5.render(f5);
//        arm.rotateAngleZ = (time * 360);
        System.out.println("Rendering timer, time: " + time);
        arm.rotateAngleZ = time * ((float) Math.PI * 2);
        arm.render(f5);
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
    }

}
