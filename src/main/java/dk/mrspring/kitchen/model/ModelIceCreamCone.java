package dk.mrspring.kitchen.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModelIceCreamCone extends ModelBase
{
    //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;
    ModelRenderer Shape10;
    ModelRenderer Shape11;
    ModelRenderer Shape12;
    ModelRenderer Shape13;
    ModelRenderer Shape14;
    ModelRenderer Shape15;

    public ModelIceCreamCone()
    {
        textureWidth = 32;
        textureHeight = 16;

        Shape1 = new ModelRenderer(this, 0, 8);
        Shape1.addBox(0F, 0F, 0F, 1, 3, 3);
        Shape1.setRotationPoint(1.5F, 13F, -1.5F);
        Shape1.setTextureSize(32, 16);
        Shape1.mirror = true;
        setRotation(Shape1, 0F, 0F, 0F);
        Shape2 = new ModelRenderer(this, 0, 0);
        Shape2.addBox(0F, 0F, 0F, 1, 2, 1);
        Shape2.setRotationPoint(-0.5F, 22F, -0.5F);
        Shape2.setTextureSize(32, 16);
        Shape2.mirror = true;
        setRotation(Shape2, 0F, 0F, 0F);
        Shape3 = new ModelRenderer(this, 1, 4);
        Shape3.addBox(0F, 0F, 0F, 3, 3, 1);
        Shape3.setRotationPoint(-1.5F, 10F, -3.5F);
        Shape3.setTextureSize(32, 16);
        Shape3.mirror = true;
        setRotation(Shape3, 0F, 0F, 0F);
        Shape4 = new ModelRenderer(this, 0, 0);
        Shape4.addBox(0F, 0F, 0F, 2, 3, 2);
        Shape4.setRotationPoint(-1F, 19F, -1F);
        Shape4.setTextureSize(32, 16);
        Shape4.mirror = true;
        setRotation(Shape4, 0F, 0F, 0F);
        Shape5 = new ModelRenderer(this, 0, 0);
        Shape5.addBox(0F, 0F, 0F, 3, 3, 1);
        Shape5.setRotationPoint(-1.5F, 13F, -2.5F);
        Shape5.setTextureSize(32, 16);
        Shape5.mirror = true;
        setRotation(Shape5, 0F, 0F, 0F);
        Shape6 = new ModelRenderer(this, 16, 6);
        Shape6.addBox(0F, 0F, 0F, 1, 3, 1);
        Shape6.setRotationPoint(1.5F, 10F, 1.5F);
        Shape6.setTextureSize(32, 16);
        Shape6.mirror = true;
        setRotation(Shape6, 0F, 0F, 0F);
        Shape7 = new ModelRenderer(this, 10, 0);
        Shape7.addBox(0F, 0F, 0F, 3, 3, 1);
        Shape7.setRotationPoint(-1.5F, 13F, 1.5F);
        Shape7.setTextureSize(32, 16);
        Shape7.mirror = true;
        setRotation(Shape7, 0F, 0F, 0F);
        Shape8 = new ModelRenderer(this, 9, 2);
        Shape8.addBox(0F, 0F, 0F, 1, 3, 3);
        Shape8.setRotationPoint(-2.5F, 13F, -1.5F);
        Shape8.setTextureSize(32, 16);
        Shape8.mirror = true;
        setRotation(Shape8, 0F, 0F, 0F);
        Shape9 = new ModelRenderer(this, 1, 1);
        Shape9.addBox(0F, 0F, 0F, 3, 3, 1);
        Shape9.setRotationPoint(-1.5F, 10F, 2.5F);
        Shape9.setTextureSize(32, 16);
        Shape9.mirror = true;
        setRotation(Shape9, 0F, 0F, 0F);
        Shape10 = new ModelRenderer(this, 13, 10);
        Shape10.addBox(0F, 0F, 0F, 1, 3, 3);
        Shape10.setRotationPoint(-3.5F, 10F, -1.5F);
        Shape10.setTextureSize(32, 16);
        Shape10.mirror = true;
        setRotation(Shape10, 0F, 0F, 0F);
        Shape11 = new ModelRenderer(this, 6, 9);
        Shape11.addBox(0F, 0F, 0F, 1, 3, 3);
        Shape11.setRotationPoint(2.5F, 10F, -1.5F);
        Shape11.setTextureSize(32, 16);
        Shape11.mirror = true;
        setRotation(Shape11, 0F, 0F, 0F);
        Shape12 = new ModelRenderer(this, 10, 8);
        Shape12.addBox(0F, 0F, 0F, 1, 3, 1);
        Shape12.setRotationPoint(1.5F, 10F, -2.5F);
        Shape12.setTextureSize(32, 16);
        Shape12.mirror = true;
        setRotation(Shape12, 0F, 0F, 0F);
        Shape13 = new ModelRenderer(this, 13, 9);
        Shape13.addBox(0F, 0F, 0F, 1, 3, 1);
        Shape13.setRotationPoint(-2.5F, 10F, -2.5F);
        Shape13.setTextureSize(32, 16);
        Shape13.mirror = true;
        setRotation(Shape13, 0F, 0F, 0F);
        Shape14 = new ModelRenderer(this, 3, 11);
        Shape14.addBox(0F, 0F, 0F, 1, 3, 1);
        Shape14.setRotationPoint(-2.5F, 10F, 1.5F);
        Shape14.setTextureSize(32, 16);
        Shape14.mirror = true;
        setRotation(Shape14, 0F, 0F, 0F);
        Shape15 = new ModelRenderer(this, 0, 4);
        Shape15.addBox(0F, 0F, 0F, 3, 3, 3);
        Shape15.setRotationPoint(-1.5F, 16F, -1.5F);
        Shape15.setTextureSize(32, 16);
        Shape15.mirror = true;
        setRotation(Shape15, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("kitchen", "textures/models/ice_cream_cone.png"));
        GL11.glRotatef(180, 0, 0, 1);
        float scale = 0.8F;
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(0.01F, -0.45F, 0.28F);
        GL11.glColor4f(1, 1, 1, 1);
        Shape1.render(f5);
        Shape2.render(f5);
        Shape3.render(f5);
        Shape4.render(f5);
        Shape5.render(f5);
        Shape6.render(f5);
        Shape7.render(f5);
        Shape8.render(f5);
        Shape9.render(f5);
        Shape10.render(f5);
        Shape11.render(f5);
        Shape12.render(f5);
        Shape13.render(f5);
        Shape14.render(f5);
        Shape15.render(f5);
        GL11.glPopMatrix();
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
