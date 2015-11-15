package dk.mrspring.kitchen.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * CraftingCabinet - MrSpring
 * Created using Tabula 5.1.0
 */
public class ModelCraftingCabinet extends ModelBase {
    public ModelRenderer base;
    public ModelRenderer front;
    public ModelRenderer quartz_top;

    public ModelCraftingCabinet() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.front = new ModelRenderer(this, 0, 48);
        this.front.setRotationPoint(-7.0F, 11.0F, -7.0F);
        this.front.addBox(0.0F, 0.0F, 0.0F, 14, 13, 1, 0.0F);
        this.base = new ModelRenderer(this, 1, 0);
        this.base.setRotationPoint(-8.0F, 11.0F, -6.0F);
        this.base.addBox(0.0F, 0.0F, 0.0F, 16, 13, 14, 0.0F);
        this.quartz_top = new ModelRenderer(this, 0, 28);
        this.quartz_top.setRotationPoint(-8.0F, 8.0F, -8.0F);
        this.quartz_top.addBox(0.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.front.render(f5);
        this.base.render(f5);
        this.quartz_top.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
