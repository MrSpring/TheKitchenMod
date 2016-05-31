package dk.mrspring.kitchen.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class ModelPart extends ModelRenderer
{
    ModelBase parent;

    public ModelPart(ModelBase parent, String name)
    {
        super(parent, name);
        this.parent = parent;
    }

    public ModelPart(ModelBase parent)
    {
        super(parent);
        this.parent = parent;
    }

    public ModelPart(ModelBase parent, int u, int v)
    {
        super(parent, u, v);
        this.parent = parent;
    }

    @Override
    @Deprecated
    public ModelPart addBox(String name, float xOffset, float yOffset, float zOffset, int width, int height, int depth)
    {
        return this;
    }

    @Override
    @Deprecated
    public ModelPart addBox(float xOffset, float yOffset, float zOffset, int width, int height, int depth)
    {
        return this;
    }

    public ModelPart addBox(int u, int v,
                            float xOffset, float yOffset, float zOffset,
                            int width, int height, int depth)
    {
        return this.addBox(u, v, xOffset, yOffset, zOffset, width, height, depth, 0F);
    }

    @SuppressWarnings("unchecked")
    public ModelPart addBox(int u, int v,
                            float xOffset, float yOffset, float zOffset,
                            int width, int height, int depth, float scale)
    {
        cubeList.add(new ModelBox(this, u, v, xOffset, yOffset, zOffset, width, height, depth, scale));
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