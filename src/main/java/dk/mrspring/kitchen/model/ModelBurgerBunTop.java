package dk.mrspring.kitchen.model;

import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelBurgerBunTop extends ModelBase
{
    //fields
    ModelRenderer topMiddle;
    ModelRenderer side3;
    ModelRenderer side2;
    ModelRenderer side1;
    ModelRenderer side0;
    ModelRenderer middle;

    public ModelBurgerBunTop()
    {
        textureWidth = 64;
        textureHeight = 32;

        topMiddle = new ModelRenderer(this, 0, 0);
        topMiddle.addBox(0F, 0F, 0F, 10, 1, 10);
        topMiddle.setRotationPoint(-5F, 21F, -5F);
        topMiddle.setTextureSize(64, 32);
        topMiddle.mirror = true;
        setRotation(topMiddle, 0F, 0F, 0F);
        side3 = new ModelRenderer(this, 0, 17);
        side3.addBox(0F, 0F, 0F, 10, 2, 1);
        side3.setRotationPoint(-5F, 22F, 5F);
        side3.setTextureSize(64, 32);
        side3.mirror = true;
        setRotation(side3, 0F, 0F, 0F);
        side2 = new ModelRenderer(this, 0, 13);
        side2.addBox(0F, 0F, 0F, 10, 2, 1);
        side2.setRotationPoint(-5F, 22F, -6F);
        side2.setTextureSize(64, 32);
        side2.mirror = true;
        setRotation(side2, 0F, 0F, 0F);
        side1 = new ModelRenderer(this, 41, 13);
        side1.addBox(0F, 0F, 0F, 1, 2, 10);
        side1.setRotationPoint(5F, 22F, -5F);
        side1.setTextureSize(64, 32);
        side1.mirror = true;
        setRotation(side1, 0F, 0F, 0F);
        side0 = new ModelRenderer(this, 41, 0);
        side0.addBox(0F, 0F, 0F, 1, 2, 10);
        side0.setRotationPoint(-6F, 22F, -5F);
        side0.setTextureSize(64, 32);
        side0.mirror = true;
        setRotation(side0, 0F, 0F, 0F);
        middle = new ModelRenderer(this, 0, 0);
        middle.addBox(0F, 0F, 0F, 10, 2, 10);
        middle.setRotationPoint(-5F, 22F, -5F);
        middle.setTextureSize(64, 32);
        middle.mirror = true;
        setRotation(middle, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.modid + ":textures/models/burger_bun_top.png"));
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        topMiddle.render(f5);
        side3.render(f5);
        side2.render(f5);
        side1.render(f5);
        side0.render(f5);
        middle.render(f5);
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
