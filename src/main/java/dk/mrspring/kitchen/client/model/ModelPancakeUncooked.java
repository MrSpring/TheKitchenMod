package dk.mrspring.kitchen.client.model;

import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by MrSpring on 11-12-2014 for TheKitchenMod.
 */
public class ModelPancakeUncooked extends ModelBase
{
    ResourceLocation texture = new ResourceLocation(ModInfo.toTexture("textures/models/pancake_raw.png"));

    ModelRenderer pancake;

    public ModelPancakeUncooked()
    {
        textureWidth = 32;
        textureHeight = 32;

        pancake = new ModelRenderer(this, 0, 6);
        pancake.addBox(0F, 0F, 0F, 6, 1, 6);
        pancake.setRotationPoint(-3F, 22.5F, -3F);
        pancake.setTextureSize(32, 32);
        pancake.mirror = true;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        pancake.render(f5);
    }
}
