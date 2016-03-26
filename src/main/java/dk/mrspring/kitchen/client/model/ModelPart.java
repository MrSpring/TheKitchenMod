package dk.mrspring.kitchen.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
public class ModelPart extends ModelRenderer
{
    public ModelPart(ModelBase parent, String name)
    {
        super(parent, name);
    }

    public ModelPart(ModelBase parent)
    {
        super(parent);
    }

    public ModelPart(ModelBase parent, int u, int v)
    {
        super(parent, u, v);
    }

    @Override
    public ModelPart addBox(String name, float xOffset, float yOffset, float zOffset, int width, int height, int depth)
    {
        super.addBox(name, xOffset, yOffset, zOffset, width, height, depth);
        return this;
    }

    @Override
    public ModelPart addBox(float xOffset, float yOffset, float zOffset, int width, int height, int depth)
    {
        super.addBox(xOffset, yOffset, zOffset, width, height, depth);
        return this;
    }

    @Override
    public ModelPart setTextureSize(int p_78787_1_, int p_78787_2_)
    {
        super.setTextureSize(p_78787_1_, p_78787_2_);
        return this;
    }

    public ModelPart setPivot(float x, float y, float z)
    {
        this.setRotationPoint(x, y, z);
        return this;
    }

    public ModelPart setMirrored(boolean mirror)
    {
        this.mirror = mirror;
        return this;
    }

    public ModelPart setRotation(float x, float y, float z)
    {
        this.rotateAngleX = x;
        this.rotateAngleY = y;
        this.rotateAngleZ = z;
        return this;
    }
}
