package dk.mrspring.kitchen.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHandBook extends ModelBase
{
    ModelRenderer backLeft;
    ModelRenderer backRight;
    ModelRenderer back;
    ModelRenderer pageLeft;
    ModelRenderer pageRight;
    ModelRenderer armRight;
    ModelRenderer armLeft;

    public ModelHandBook()
    {
        textureWidth = 64;
        textureHeight = 64;

        backLeft = new ModelRenderer(this, 0, 14);
        backLeft.addBox(-8F, 0F, 0F, 8, 1, 12);
        backLeft.setRotationPoint(-1F, 23F, -6F);
        backLeft.setTextureSize(64, 64);
        backLeft.mirror = true;
        setRotation(backLeft, 0F, 0F, 0.0872665F);
        backRight = new ModelRenderer(this, 0, 0);
        backRight.addBox(0F, 0F, 0F, 8, 1, 12);
        backRight.setRotationPoint(1F, 23F, -6F);
        backRight.setTextureSize(64, 64);
        backRight.mirror = true;
        setRotation(backRight, 0F, 0F, -0.0872665F);
        back = new ModelRenderer(this, 35, 34);
        back.addBox(0F, 0F, 0F, 2, 1, 12);
        back.setRotationPoint(-1F, 22.7F, -6F);
        back.setTextureSize(64, 64);
        back.mirror = true;
        setRotation(back, 0F, 0F, 0F);
        pageLeft = new ModelRenderer(this, 0, 40);
        pageLeft.addBox(-7F, -1F, 1F, 8, 1, 10);
        pageLeft.setRotationPoint(-1F, 23F, -6F);
        pageLeft.setTextureSize(64, 64);
        pageLeft.mirror = true;
        setRotation(pageLeft, 0F, 0F, 0.0872665F);
        pageRight = new ModelRenderer(this, 0, 28);
        pageRight.addBox(-1F, -1F, 1F, 8, 1, 10);
        pageRight.setRotationPoint(1F, 23F, -6F);
        pageRight.setTextureSize(64, 64);
        pageRight.mirror = true;
        setRotation(pageRight, 0F, 0F, -0.0872665F);
        armRight = new ModelRenderer(this, 40, 16);
        armRight.addBox(-4F, 0F, 0F, 4, 12, 4);
        armRight.setRotationPoint(11F, 29F, -11F);
        armRight.setTextureSize(64, 64);
        armRight.mirror = true;
        setRotation(armRight, 1.745329F, -0.4537856F, 0F);
        armLeft = new ModelRenderer(this, 40, 16);
        armLeft.addBox(0F, 0F, 0F, 4, 12, 4);
        armLeft.setRotationPoint(-11F, 29F, -11F);
        armLeft.setTextureSize(64, 64);
        setRotation(armLeft, 1.745329F, 0.4537856F, 0F);
        armLeft.mirror = false;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        backLeft.render(f5);
        backRight.render(f5);
        back.render(f5);
        pageLeft.render(f5);
        pageRight.render(f5);

        Minecraft.getMinecraft().renderEngine.bindTexture(((EntityClientPlayerMP) entity).getLocationSkin());

        armRight.render(f5);
        armLeft.render(f5);
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
