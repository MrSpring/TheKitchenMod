package dk.mrspring.kitchen.model.butter;

import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModelButter2 extends ModelBase
{
    //fields
    ModelRenderer butterbase;
    ModelRenderer butter01;
    ModelRenderer butter02;
    ModelRenderer butter03;
    ModelRenderer butter04;
    ModelRenderer butter05;
    ModelRenderer butter06;
    ModelRenderer butter07;
    ModelRenderer butter08;
    ModelRenderer butter09;
    ModelRenderer butter10;
    ModelRenderer butter11;
    ModelRenderer butter12;
    ModelRenderer butter13;
    ModelRenderer butter14;
    ModelRenderer butter15;

    public ModelButter2()
    {
        textureWidth = 32;
        textureHeight = 32;

        butterbase = new ModelRenderer(this, 0, 0);
        butterbase.addBox(0F, 0F, 0F, 3, 3, 6);
        butterbase.setRotationPoint(-2F, 21F, -3F);
        butterbase.setTextureSize(32, 32);
        butterbase.mirror = true;
        setRotation(butterbase, 0F, 0F, 0F);
        butter01 = new ModelRenderer(this, 0, 0);
        butter01.addBox(0F, 0F, 0F, 1, 1, 1);
        butter01.setRotationPoint(-2F, 23F, 3F);
        butter01.setTextureSize(32, 32);
        butter01.mirror = true;
        setRotation(butter01, 0F, 0F, 0F);
        butter02 = new ModelRenderer(this, 0, 0);
        butter02.addBox(0F, 0F, 0F, 1, 1, 1);
        butter02.setRotationPoint(-2F, 23F, -4F);
        butter02.setTextureSize(32, 32);
        butter02.mirror = true;
        setRotation(butter02, 0F, 0F, 0F);
        butter03 = new ModelRenderer(this, 0, 0);
        butter03.addBox(0F, 0F, 0F, 1, 1, 2);
        butter03.setRotationPoint(-3F, 22F, 0F);
        butter03.setTextureSize(32, 32);
        butter03.mirror = true;
        setRotation(butter03, 0F, 0F, 0F);
        butter04 = new ModelRenderer(this, 0, 0);
        butter04.addBox(0F, 0F, 0F, 1, 2, 1);
        butter04.setRotationPoint(1F, 22F, 3F);
        butter04.setTextureSize(32, 32);
        butter04.mirror = true;
        setRotation(butter04, 0F, 0F, 0F);
        butter05 = new ModelRenderer(this, 0, 0);
        butter05.addBox(0F, 0F, 0F, 1, 1, 1);
        butter05.setRotationPoint(2F, 23F, 3F);
        butter05.setTextureSize(32, 32);
        butter05.mirror = true;
        setRotation(butter05, 0F, 0F, 0F);
        butter06 = new ModelRenderer(this, 0, 0);
        butter06.addBox(0F, 0F, 0F, 2, 1, 2);
        butter06.setRotationPoint(-1F, 23F, 3F);
        butter06.setTextureSize(32, 32);
        butter06.mirror = true;
        setRotation(butter06, 0F, 0F, 0F);
        butter07 = new ModelRenderer(this, 0, 0);
        butter07.addBox(0F, 0F, 0F, 1, 1, 3);
        butter07.setRotationPoint(1F, 23F, 0F);
        butter07.setTextureSize(32, 32);
        butter07.mirror = true;
        setRotation(butter07, 0F, 0F, 0F);
        butter08 = new ModelRenderer(this, 0, 0);
        butter08.addBox(0F, 0F, 0F, 1, 1, 3);
        butter08.setRotationPoint(1F, 23F, 0F);
        butter08.setTextureSize(32, 32);
        butter08.mirror = true;
        setRotation(butter08, 0F, 0F, 0F);
        butter09 = new ModelRenderer(this, 0, 0);
        butter09.addBox(0F, 0F, 0F, 2, 1, 2);
        butter09.setRotationPoint(2F, 23F, -3F);
        butter09.setTextureSize(32, 32);
        butter09.mirror = true;
        setRotation(butter09, 0F, 0F, 0F);
        butter10 = new ModelRenderer(this, 0, 0);
        butter10.addBox(0F, 0F, 0F, 2, 1, 2);
        butter10.setRotationPoint(2F, 23F, -3F);
        butter10.setTextureSize(32, 32);
        butter10.mirror = true;
        setRotation(butter10, 0F, 0F, 0F);
        butter11 = new ModelRenderer(this, 0, 0);
        butter11.addBox(0F, 0F, 0F, 1, 2, 1);
        butter11.setRotationPoint(-3F, 22F, -3F);
        butter11.setTextureSize(32, 32);
        butter11.mirror = true;
        setRotation(butter11, 0F, 0F, 0F);
        butter12 = new ModelRenderer(this, 0, 0);
        butter12.addBox(0F, 0F, 0F, 1, 1, 4);
        butter12.setRotationPoint(-3F, 23F, -1F);
        butter12.setTextureSize(32, 32);
        butter12.mirror = true;
        setRotation(butter12, 0F, 0F, 0F);
        butter13 = new ModelRenderer(this, 0, 0);
        butter13.addBox(0F, 0F, 0F, 1, 2, 3);
        butter13.setRotationPoint(1F, 22F, -3F);
        butter13.setTextureSize(32, 32);
        butter13.mirror = true;
        setRotation(butter13, 0F, 0F, 0F);
        butter14 = new ModelRenderer(this, 0, 0);
        butter14.addBox(0F, 0F, 0F, 2, 2, 1);
        butter14.setRotationPoint(-1F, 22F, -4F);
        butter14.setTextureSize(32, 32);
        butter14.mirror = true;
        setRotation(butter14, 0F, 0F, 0F);
        butter15 = new ModelRenderer(this, 0, 0);
        butter15.addBox(0F, 0F, 0F, 1, 1, 3);
        butter15.setRotationPoint(-3F, 23F, 0F);
        butter15.setTextureSize(32, 32);
        butter15.mirror = true;
        setRotation(butter15, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.modid + ":textures/models/butter.png"));

//        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
//        GL11.glScalef(0.7F, 0.7F, 0.7F);
//        GL11.glTranslatef(0.0F, -1.475F, 0.215F);

        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        butterbase.render(f5);
        butter01.render(f5);
        butter02.render(f5);
        butter03.render(f5);
        butter04.render(f5);
        butter05.render(f5);
        butter06.render(f5);
        butter07.render(f5);
        butter08.render(f5);
        butter09.render(f5);
        butter10.render(f5);
        butter11.render(f5);
        butter12.render(f5);
        butter13.render(f5);
        butter14.render(f5);
        butter15.render(f5);
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