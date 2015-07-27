package dk.mrspring.kitchen.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMuffinTray extends ModelBase
{
    public ModelRenderer trayBase;
    public ModelRenderer cup1;
    public ModelRenderer cup2;
    public ModelRenderer cup3;
    public ModelRenderer cup4;
    public ModelRenderer cup5;
    public ModelRenderer cup6;

    public ModelMuffinTray()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.trayBase = new ModelRenderer(this, 0, 0);
        this.trayBase.setRotationPoint(-6.5F, 17.0F, -3.0F);
        this.trayBase.addBox(0.0F, 0.0F, 0.0F, 13, 1, 9, 0.0F);
        this.cup4 = new ModelRenderer(this, 26, 11);
        this.cup4.setRotationPoint(2.0F, 18.0F, -2.0F);
        this.cup4.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3, 0.0F);
        this.cup6 = new ModelRenderer(this, 0, 11);
        this.cup6.setRotationPoint(-5.0F, 18.0F, -2.0F);
        this.cup6.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3, 0.0F);
        this.cup3 = new ModelRenderer(this, 13, 17);
        this.cup3.setRotationPoint(-1.5F, 18.0F, 2.0F);
        this.cup3.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3, 0.0F);
        this.cup2 = new ModelRenderer(this, 0, 17);
        this.cup2.setRotationPoint(-5.0F, 18.0F, 2.0F);
        this.cup2.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3, 0.0F);
        this.cup1 = new ModelRenderer(this, 26, 17);
        this.cup1.setRotationPoint(2.0F, 18.0F, 2.0F);
        this.cup1.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3, 0.0F);
        this.cup5 = new ModelRenderer(this, 13, 11);
        this.cup5.setRotationPoint(-1.5F, 18.0F, -2.0F);
        this.cup5.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3, 0.0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, boolean filled)
    {
        this.trayBase.render(f5);
        this.cup4.render(f5);
        this.cup6.render(f5);
        this.cup3.render(f5);
        this.cup2.render(f5);
        this.cup1.render(f5);
        this.cup5.render(f5);
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
