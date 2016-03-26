package dk.mrspring.kitchen.client.model.jam;

import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by MrSpring on 27-10-2014 for TheKitchenMod.
 */
public class ModelJam3 extends ModelBase
{
    //fields
    ResourceLocation texture=new ResourceLocation(ModInfo.toTexture("textures/models/jam.png"));

    ModelRenderer jam;

    public ModelJam3()
    {
        textureWidth = 32;
        textureHeight = 32;

        jam = new ModelRenderer(this, 0, 6);
        jam.addBox(0F, 0F, 0F, 6, 1, 6);
        jam.setRotationPoint(-3F, 22.5F, -3F);
        jam.setTextureSize(32, 32);
        jam.mirror = true;
        setRotation(jam, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        jam.render(f5);
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
