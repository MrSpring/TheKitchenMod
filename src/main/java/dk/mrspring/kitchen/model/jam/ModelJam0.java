package dk.mrspring.kitchen.model.jam;

/**
 * Created by MrSpring on 27-10-2014 for TheKitchenMod.
 */

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelJam0 extends ModelBase
{
    //fields
    ModelRenderer jam1;
    ModelRenderer jam2;
    ModelRenderer jam3;
    ModelRenderer jam4;
    ModelRenderer jam5;
    ModelRenderer jam6;
    ModelRenderer jam7;
    ModelRenderer jam8;

    public ModelJam0()
    {
        textureWidth = 32;
        textureHeight = 32;

        jam1 = new ModelRenderer(this, 0, 3);
        jam1.addBox(0F, 0F, 0F, 1, 1, 1);
        jam1.setRotationPoint(-1F, 22.5F, 0.5F);
        jam1.setTextureSize(32, 32);
        jam1.mirror = true;
        setRotation(jam1, 0F, 0F, 0F);
        jam2 = new ModelRenderer(this, 5, 3);
        jam2.addBox(0F, 0F, 0F, 1, 1, 1);
        jam2.setRotationPoint(-2.5F, 22.5F, -0.5F);
        jam2.setTextureSize(32, 32);
        jam2.mirror = true;
        setRotation(jam2, 0F, 0F, 0F);
        jam3 = new ModelRenderer(this, 5, 9);
        jam3.addBox(0F, 0F, 0F, 1, 1, 1);
        jam3.setRotationPoint(-0.5F, 22.5F, -2F);
        jam3.setTextureSize(32, 32);
        jam3.mirror = true;
        setRotation(jam3, 0F, 0F, 0F);
        jam4 = new ModelRenderer(this, 0, 0);
        jam4.addBox(0F, 0F, 0F, 1, 1, 1);
        jam4.setRotationPoint(-2.5F, 22.5F, 1F);
        jam4.setTextureSize(32, 32);
        jam4.mirror = true;
        setRotation(jam4, 0F, 0F, 0F);
        jam5 = new ModelRenderer(this, 5, 0);
        jam5.addBox(0F, 0F, 0F, 1, 1, 1);
        jam5.setRotationPoint(1F, 22.5F, 1F);
        jam5.setTextureSize(32, 32);
        jam5.mirror = true;
        setRotation(jam5, 0F, 0F, 0F);
        jam6 = new ModelRenderer(this, 5, 6);
        jam6.addBox(0F, 0F, 0F, 1, 1, 1);
        jam6.setRotationPoint(1F, 22.5F, -2.5F);
        jam6.setTextureSize(32, 32);
        jam6.mirror = true;
        setRotation(jam6, 0F, 0F, 0F);
        jam7 = new ModelRenderer(this, 0, 9);
        jam7.addBox(0F, 0F, 0F, 1, 1, 1);
        jam7.setRotationPoint(0.5F, 22.5F, -0.5F);
        jam7.setTextureSize(32, 32);
        jam7.mirror = true;
        setRotation(jam7, 0F, 0F, 0F);
        jam8 = new ModelRenderer(this, 0, 6);
        jam8.addBox(0F, 0F, 0F, 1, 1, 1);
        jam8.setRotationPoint(-2F, 22.5F, -2.5F);
        jam8.setTextureSize(32, 32);
        jam8.mirror = true;
        setRotation(jam8, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(entity, f, f1, f2, f3, f4, f5);
        jam1.render(f5);
        jam2.render(f5);
        jam3.render(f5);
        jam4.render(f5);
        jam5.render(f5);
        jam6.render(f5);
        jam7.render(f5);
        jam8.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
