package dk.mrspring.kitchen.model.block;

import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModelWaffleIron extends ModelBase
{
    //fields
    ModelRenderer topBase1;
    ModelRenderer topBase2;
    ModelRenderer topBase3;
    ModelRenderer topBase4;
    ModelRenderer topBase5;
    ModelRenderer topBase6;
    ModelRenderer topBase7;
    ModelRenderer topBase8;
    ModelRenderer bottomBase1;
    ModelRenderer bottomBase2;
    ModelRenderer bottomBase3;
    ModelRenderer bottomBase4;
    ModelRenderer bottomBase5;
    ModelRenderer bottomBase6;
    ModelRenderer bottomBase7;
    ModelRenderer bottomBase8;
    ModelRenderer bottomBase9;
    ModelRenderer bottomBase10;
    ModelRenderer bottomBase11;
    ModelRenderer bottomBase12;
    ModelRenderer waffleMesh;

    public ModelWaffleIron()
    {
        textureWidth = 64;
        textureHeight = 32;

        topBase1 = new ModelRenderer(this, 13, 12);
        topBase1.addBox(0F, -2F, -9F, 12, 2, 8);
        topBase1.setRotationPoint(-6F, 19F, 5F);
        topBase1.setTextureSize(64, 32);
        topBase1.mirror = true;
        setRotation(topBase1, 0F, 0F, 0F);
        bottomBase1 = new ModelRenderer(this, 0, 0);
        bottomBase1.addBox(0F, 0F, 0F, 1, 1, 1);
        bottomBase1.setRotationPoint(-5F, 23F, 2F);
        bottomBase1.setTextureSize(64, 32);
        bottomBase1.mirror = true;
        setRotation(bottomBase1, 0F, 0F, 0F);
        bottomBase2 = new ModelRenderer(this, 0, 0);
        bottomBase2.addBox(0F, 0F, 0F, 1, 1, 1);
        bottomBase2.setRotationPoint(4F, 23F, -3F);
        bottomBase2.setTextureSize(64, 32);
        bottomBase2.mirror = true;
        setRotation(bottomBase2, 0F, 0F, 0F);
        bottomBase3 = new ModelRenderer(this, 0, 0);
        bottomBase3.addBox(0F, 0F, 0F, 1, 1, 1);
        bottomBase3.setRotationPoint(4F, 23F, 2F);
        bottomBase3.setTextureSize(64, 32);
        bottomBase3.mirror = true;
        setRotation(bottomBase3, 0F, 0F, 0F);
        bottomBase4 = new ModelRenderer(this, 0, 0);
        bottomBase4.addBox(0F, 0F, 0F, 1, 1, 1);
        bottomBase4.setRotationPoint(-5F, 23F, -3F);
        bottomBase4.setTextureSize(64, 32);
        bottomBase4.mirror = true;
        setRotation(bottomBase4, 0F, 0F, 0F);
        topBase2 = new ModelRenderer(this, 0, 18);
        topBase2.addBox(0F, -0.5F, -8.5F, 1, 1, 7);
        topBase2.setRotationPoint(4.5F, 19F, 5F);
        topBase2.setTextureSize(64, 32);
        topBase2.mirror = true;
        setRotation(topBase2, 0F, 0F, 0F);
        bottomBase5 = new ModelRenderer(this, 0, 4);
        bottomBase5.addBox(0F, 0F, 0F, 2, 2, 1);
        bottomBase5.setRotationPoint(-3F, 19F, 4F);
        bottomBase5.setTextureSize(64, 32);
        bottomBase5.mirror = true;
        setRotation(bottomBase5, 0F, 0F, 0F);
        topBase3 = new ModelRenderer(this, 0, 4);
        topBase3.addBox(0F, -1F, -1F, 2, 1, 1);
        topBase3.setRotationPoint(-3F, 19F, 5F);
        topBase3.setTextureSize(64, 32);
        topBase3.mirror = true;
        setRotation(topBase3, 0F, 0F, 0F);
        topBase4 = new ModelRenderer(this, 0, 15);
        topBase4.addBox(0F, -0.5F, -8.5F, 9, 1, 1);
        topBase4.setRotationPoint(-4.5F, 19F, 5F);
        topBase4.setTextureSize(64, 32);
        topBase4.mirror = true;
        setRotation(topBase4, 0F, 0F, 0F);
        bottomBase6 = new ModelRenderer(this, 0, 4);
        bottomBase6.addBox(0F, 0F, 0F, 2, 2, 1);
        bottomBase6.setRotationPoint(2F, 19F, 4F);
        bottomBase6.setTextureSize(64, 32);
        bottomBase6.mirror = true;
        setRotation(bottomBase6, 0F, 0F, 0F);
        topBase5 = new ModelRenderer(this, 0, 4);
        topBase5.addBox(0F, -1F, -1F, 2, 1, 1);
        topBase5.setRotationPoint(2F, 19F, 5F);
        topBase5.setTextureSize(64, 32);
        topBase5.mirror = true;
        setRotation(topBase5, 0F, 0F, 0F);
        bottomBase7 = new ModelRenderer(this, 0, 0);
        bottomBase7.addBox(0F, 0F, 0F, 12, 3, 8);
        bottomBase7.setRotationPoint(-6F, 20F, -4F);
        bottomBase7.setTextureSize(64, 32);
        bottomBase7.mirror = true;
        setRotation(bottomBase7, 0F, 0F, 0F);
        bottomBase8 = new ModelRenderer(this, 0, 18);
        bottomBase8.addBox(0F, 0F, 0F, 1, 1, 7);
        bottomBase8.setRotationPoint(-5.5F, 19.5F, -3.5F);
        bottomBase8.setTextureSize(64, 32);
        bottomBase8.mirror = true;
        setRotation(bottomBase8, 0F, 0F, 0F);
        bottomBase9 = new ModelRenderer(this, 0, 15);
        bottomBase9.addBox(0F, 0F, 0F, 9, 1, 1);
        bottomBase9.setRotationPoint(-4.5F, 19.5F, -3.5F);
        bottomBase9.setTextureSize(64, 32);
        bottomBase9.mirror = true;
        setRotation(bottomBase9, 0F, 0F, 0F);
        bottomBase10 = new ModelRenderer(this, 0, 18);
        bottomBase10.addBox(0F, 0F, 0F, 1, 1, 7);
        bottomBase10.setRotationPoint(4.5F, 19.5F, -3.5F);
        bottomBase10.setTextureSize(64, 32);
        bottomBase10.mirror = true;
        setRotation(bottomBase10, 0F, 0F, 0F);
        bottomBase11 = new ModelRenderer(this, 0, 15);
        bottomBase11.addBox(0F, 0F, 0F, 9, 1, 1);
        bottomBase11.setRotationPoint(-4.5F, 19.5F, 2.5F);
        bottomBase11.setTextureSize(64, 32);
        bottomBase11.mirror = true;
        setRotation(bottomBase11, 0F, 0F, 0F);
        topBase6 = new ModelRenderer(this, 0, 15);
        topBase6.addBox(0F, -0.5F, -2.5F, 9, 1, 1);
        topBase6.setRotationPoint(-4.5F, 19F, 5F);
        topBase6.setTextureSize(64, 32);
        topBase6.mirror = true;
        setRotation(topBase6, 0F, 0F, 0F);
        topBase7 = new ModelRenderer(this, 0, 18);
        topBase7.addBox(0F, -0.5F, -8.5F, 1, 1, 7);
        topBase7.setRotationPoint(-5.5F, 19F, 5F);
        topBase7.setTextureSize(64, 32);
        topBase7.mirror = true;
        setRotation(topBase7, 0F, 0F, 0F);
        topBase8 = new ModelRenderer(this, 2, 20);
        topBase8.addBox(0F, -0.5F, -7.5F, 1, 1, 5);
        topBase8.setRotationPoint(-0.5F, 19F, 5F);
        topBase8.setTextureSize(64, 32);
        topBase8.mirror = true;
        setRotation(topBase8, 0F, 0F, 0F);
        waffleMesh = new ModelRenderer(this, 0, 0);
        waffleMesh.addBox(0F, 0F, 0F, 10, 1, 6);
        waffleMesh.setRotationPoint(-5.0F, 19.75F, -3F);
        waffleMesh.setTextureSize(16, 16);
        waffleMesh.mirror = true;
        setRotation(waffleMesh, 0F, 0F, 0F);
        bottomBase12 = new ModelRenderer(this, 2, 20);
        bottomBase12.addBox(0F, 0F, 0F, 1, 1, 5);
        bottomBase12.setRotationPoint(-0.5F, 19.5F, -2.5F);
        bottomBase12.setTextureSize(64, 32);
        bottomBase12.mirror = true;
        setRotation(bottomBase12, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int lidAngle, int lidDirection, int waffleState, float[] doughColor, float partial)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        bottomBase1.render(f5);
        bottomBase2.render(f5);
        bottomBase3.render(f5);
        bottomBase4.render(f5);
        bottomBase5.render(f5);
        bottomBase6.render(f5);
        bottomBase7.render(f5);
        bottomBase8.render(f5);
        bottomBase9.render(f5);
        bottomBase10.render(f5);
        bottomBase11.render(f5);
        bottomBase12.render(f5);

//        System.out.println(f + ", " + f1 + ", " + f2 + ", " + f3 + ", " + f4 + ", " + f5);

        float rotation = -lidAngle;
        rotation += (float) -lidDirection * partial;
        rotation = Math.max(-15, Math.min(0, rotation));
//        System.out.println(lidDirection);
        rotation *= 0.12F;
        topBase1.rotateAngleX = rotation;
        topBase1.render(f5);

        topBase2.rotateAngleX = rotation;
        topBase2.render(f5);

        topBase3.rotateAngleX = rotation;
        topBase3.render(f5);

        topBase4.rotateAngleX = rotation;
        topBase4.render(f5);

        topBase5.rotateAngleX = rotation;
        topBase5.render(f5);

        topBase6.rotateAngleX = rotation;
        topBase6.render(f5);

        topBase7.rotateAngleX = rotation;
        topBase7.render(f5);

        topBase8.rotateAngleX = rotation;
        topBase8.render(f5);

        if (waffleState > 0)
        {
            if (waffleState == 1)
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.toTexture("textures/models/raw_waffle.png")));
            else if (waffleState == 2)
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.toTexture("textures/models/cooked_waffle.png")));
            else
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.toTexture("textures/models/burnt_waffle.png")));
            GL11.glPushMatrix();
            GL11.glColor4f(doughColor[0] / 255, doughColor[1] / 255, doughColor[2] / 255, 1);
            waffleMesh.render(f5);
            GL11.glPopMatrix();
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
