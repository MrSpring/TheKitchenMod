package dk.mrspring.kitchen.model;

import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelMixingBowl extends ModelBase
{
    //fields
    ModelRenderer base1;
    ModelRenderer base2;
    ModelRenderer base3;
    ModelRenderer base4;
    ModelRenderer base5;
    ModelRenderer base6;
    ModelRenderer base7;
    ModelRenderer base8;
    ModelRenderer base9;
    ModelRenderer base10;
    ModelRenderer base11;
    ModelRenderer base12;
    ModelRenderer base13;
    ModelRenderer doughCenter;
    ModelRenderer dough2;
    ModelRenderer dough3;
    ModelRenderer dough1;
    ModelRenderer dough4;

    public ModelMixingBowl()
    {
        textureWidth = 64;
        textureHeight = 32;

        base1 = new ModelRenderer(this, 0, 0);
        base1.addBox(0F, 0F, 0F, 1, 6, 1);
        base1.setRotationPoint(4F, 16F, 4F);
        base1.setTextureSize(64, 32);
        base1.mirror = true;
        setRotation(base1, 0F, 0F, 0F);
        base2 = new ModelRenderer(this, 0, 0);
        base2.addBox(0F, 0F, 0F, 8, 2, 1);
        base2.setRotationPoint(-4F, 21F, -5F);
        base2.setTextureSize(64, 32);
        base2.mirror = true;
        setRotation(base2, 0F, 0F, 0F);
        base3 = new ModelRenderer(this, 0, 0);
        base3.addBox(0F, 0F, 0F, 8, 2, 1);
        base3.setRotationPoint(-4F, 21F, 4F);
        base3.setTextureSize(64, 32);
        base3.mirror = true;
        setRotation(base3, 0F, 0F, 0F);
        base4 = new ModelRenderer(this, 0, 0);
        base4.addBox(0F, 0F, 0F, 1, 2, 8);
        base4.setRotationPoint(-5F, 21F, -4F);
        base4.setTextureSize(64, 32);
        base4.mirror = true;
        setRotation(base4, 0F, 0F, 0F);
        base5 = new ModelRenderer(this, 0, 0);
        base5.addBox(0F, 0F, 0F, 1, 2, 8);
        base5.setRotationPoint(4F, 21F, -4F);
        base5.setTextureSize(64, 32);
        base5.mirror = true;
        setRotation(base5, 0F, 0F, 0F);
        base6 = new ModelRenderer(this, 0, 0);
        base6.addBox(0F, 0F, 0F, 1, 6, 1);
        base6.setRotationPoint(4F, 16F, -5F);
        base6.setTextureSize(64, 32);
        base6.mirror = true;
        setRotation(base6, 0F, 0F, 0F);
        base7 = new ModelRenderer(this, 0, 0);
        base7.addBox(0F, 0F, 0F, 1, 6, 1);
        base7.setRotationPoint(-5F, 16F, -5F);
        base7.setTextureSize(64, 32);
        base7.mirror = true;
        setRotation(base7, 0F, 0F, 0F);
        base8 = new ModelRenderer(this, 0, 0);
        base8.addBox(0F, 0F, 0F, 1, 6, 1);
        base8.setRotationPoint(-5F, 16F, 4F);
        base8.setTextureSize(64, 32);
        base8.mirror = true;
        setRotation(base8, 0F, 0F, 0F);
        base9 = new ModelRenderer(this, 0, 0);
        base9.addBox(0F, 0F, 0F, 8, 5, 1);
        base9.setRotationPoint(-4F, 16F, 5F);
        base9.setTextureSize(64, 32);
        base9.mirror = true;
        setRotation(base9, 0F, 0F, 0F);
        base10 = new ModelRenderer(this, 0, 0);
        base10.addBox(0F, 0F, 0F, 1, 5, 8);
        base10.setRotationPoint(-6F, 16F, -4F);
        base10.setTextureSize(64, 32);
        base10.mirror = true;
        setRotation(base10, 0F, 0F, 0F);
        base11 = new ModelRenderer(this, 0, 0);
        base11.addBox(0F, 0F, 0F, 8, 5, 1);
        base11.setRotationPoint(-4F, 16F, -6F);
        base11.setTextureSize(64, 32);
        base11.mirror = true;
        setRotation(base11, 0F, 0F, 0F);
        base12 = new ModelRenderer(this, 0, 0);
        base12.addBox(0F, 0F, 0F, 1, 5, 8);
        base12.setRotationPoint(5F, 16F, -4F);
        base12.setTextureSize(64, 32);
        base12.mirror = true;
        setRotation(base12, 0F, 0F, 0F);
        base13 = new ModelRenderer(this, 0, 0);
        base13.addBox(0F, 0F, 0F, 8, 1, 8);
        base13.setRotationPoint(-4F, 23F, -4F);
        base13.setTextureSize(64, 32);
        base13.mirror = true;
        setRotation(base13, 0F, 0F, 0F);
        doughCenter = new ModelRenderer(this, 0, 0);
        doughCenter.addBox(0F, 0F, 0F, 8, 1, 8);
        doughCenter.setRotationPoint(-4F, 20F, -4F);
        doughCenter.setTextureSize(64, 32);
        doughCenter.mirror = true;
        setRotation(doughCenter, 0F, 0F, 0F);
        dough2 = new ModelRenderer(this, 0, 0);
        dough2.addBox(0F, 0F, 0F, 8, 1, 1);
        dough2.setRotationPoint(-4F, 18F, -5F);
        dough2.setTextureSize(64, 32);
        dough2.mirror = true;
        setRotation(dough2, 0F, 0F, 0F);
        dough3 = new ModelRenderer(this, 0, 0);
        dough3.addBox(0F, 0F, 0F, 1, 1, 8);
        dough3.setRotationPoint(4F, 18F, -4F);
        dough3.setTextureSize(64, 32);
        dough3.mirror = true;
        setRotation(dough3, 0F, 0F, 0F);
        dough1 = new ModelRenderer(this, 0, 0);
        dough1.addBox(0F, 0F, 0F, 8, 1, 1);
        dough1.setRotationPoint(-4F, 18F, 4F);
        dough1.setTextureSize(64, 32);
        dough1.mirror = true;
        setRotation(dough1, 0F, 0F, 0F);
        dough4 = new ModelRenderer(this, 0, 0);
        dough4.addBox(0F, 0F, 0F, 1, 1, 8);
        dough4.setRotationPoint(-5F, 18F, -4F);
        dough4.setTextureSize(64, 32);
        dough4.mirror = true;
        setRotation(dough4, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int metadata)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        base1.render(f5);
        base2.render(f5);
        base3.render(f5);
        base4.render(f5);
        base5.render(f5);
        base6.render(f5);
        base7.render(f5);
        base8.render(f5);
        base9.render(f5);
        base10.render(f5);
        base11.render(f5);
        base12.render(f5);
        base13.render(f5);

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.toTexture("textures/models/raw_waffle.png")));

        if (metadata > 0)
        {
            int rotationPointY = 18;
            switch (metadata)
            {
                case 1:
                    rotationPointY = 22;
                    break;
                case 2:
                    rotationPointY = 20;
                    break;
                case 3:
                    rotationPointY = 18;
                    break;
            }
            if (metadata != 1)
            {
                dough1.rotationPointY = rotationPointY;
                dough1.render(f5);
                dough2.rotationPointY = rotationPointY;
                dough2.render(f5);
                dough3.rotationPointY = rotationPointY;
                dough3.render(f5);
                dough4.rotationPointY = rotationPointY;
                dough4.render(f5);
            }
            doughCenter.rotationPointY = rotationPointY;
            doughCenter.render(f5);
        }
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
