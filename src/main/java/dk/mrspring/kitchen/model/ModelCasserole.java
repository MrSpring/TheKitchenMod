package dk.mrspring.kitchen.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Casserole - MrSpring
 * Created using Tabula 4.1.1
 */
public class ModelCasserole extends ModelBase
{
    public ModelRenderer side4;
    public ModelRenderer side3;
    public ModelRenderer side2;
    public ModelRenderer side1;
    public ModelRenderer bottom;

    public ModelCasserole()
    {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.side1 = new ModelRenderer(this, 0, 12);
        this.side1.setRotationPoint(-5.0F, 20.0F, 3.0F);
        this.side1.addBox(0.0F, 0.0F, 0.0F, 10, 3, 1, 0.0F);
        this.side2 = new ModelRenderer(this, 0, 20);
        this.side2.setRotationPoint(5.0F, 20.0F, -3.0F);
        this.side2.addBox(0.0F, 0.0F, 0.0F, 1, 3, 6, 0.0F);
        this.side3 = new ModelRenderer(this, 0, 8);
        this.side3.setRotationPoint(-5.0F, 20.0F, -4.0F);
        this.side3.addBox(0.0F, 0.0F, 0.0F, 10, 3, 1, 0.0F);
        this.side4 = new ModelRenderer(this, 10, 16);
        this.side4.setRotationPoint(-6.0F, 20.0F, -3.0F);
        this.side4.addBox(0.0F, 0.0F, 0.0F, 1, 3, 6, 0.0F);
        this.bottom = new ModelRenderer(this, 0, 0);
        this.bottom.setRotationPoint(-5.0F, 23.0F, -3.0F);
        this.bottom.addBox(0.0F, 0.0F, 0.0F, 10, 1, 6, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.side1.render(f5);
        this.side2.render(f5);
        this.side3.render(f5);
        this.side4.render(f5);
        this.bottom.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
