package dk.mrspring.kitchen.model.jam;

import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by MrSpring on 27-10-2014 for TheKitchenMod.
 */
public class ModelJam1 extends ModelBase
{
    //fields
    ResourceLocation texture=new ResourceLocation(ModInfo.toTexture("textures/models/jam.png"));

    ModelRenderer jam1;
    ModelRenderer jam2;
    ModelRenderer jam3;
    ModelRenderer jam4;
    ModelRenderer jam5;

    public ModelJam1()
    {
        textureWidth = 32;
        textureHeight = 32;

        jam1 = new ModelRenderer(this, 0, 0);
        jam1.addBox(0F, 0F, 0F, 3, 1, 1);
        jam1.setRotationPoint(-2.5F, 22.5F, 1F);
        jam1.setTextureSize(32, 32);
        jam1.mirror = true;
        setRotation(jam1, 0F, 0F, 0F);
        jam2 = new ModelRenderer(this, 5, 0);
        jam2.addBox(0F, 0F, 0F, 1, 1, 2);
        jam2.setRotationPoint(1F, 22.5F, 0F);
        jam2.setTextureSize(32, 32);
        jam2.mirror = true;
        setRotation(jam2, 0F, 0F, 0F);
        jam3 = new ModelRenderer(this, 0, 9);
        jam3.addBox(0F, 0F, 0F, 1, 1, 1);
        jam3.setRotationPoint(0F, 22.5F, 0F);
        jam3.setTextureSize(32, 32);
        jam3.mirror = true;
        setRotation(jam3, 0F, 0F, 0F);
        jam4 = new ModelRenderer(this, 0, 6);
        jam4.addBox(0F, 0F, 0F, 1, 1, 2);
        jam4.setRotationPoint(1F, 22.5F, -2.5F);
        jam4.setTextureSize(32, 32);
        jam4.mirror = true;
        setRotation(jam4, 0F, 0F, 0F);
        jam5 = new ModelRenderer(this, 0, 6);
        jam5.addBox(0F, 0F, 0F, 2, 1, 2);
        jam5.setRotationPoint(-2F, 22.5F, -2.5F);
        jam5.setTextureSize(32, 32);
        jam5.mirror = true;
        setRotation(jam5, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(entity, f, f1, f2, f3, f4, f5);

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        jam1.render(f5);
        jam2.render(f5);
        jam3.render(f5);
        jam4.render(f5);
        jam5.render(f5);
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
