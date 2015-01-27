package dk.mrspring.kitchen.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

/**
 * Casserole - MrSpring
 * Created using Tabula 4.1.1
 */
public class ModelLasagnaPlates extends ModelBase
{
    public ModelRenderer Plate1;
    public ModelRenderer Plate2;
    public ModelRenderer Plate3;

    public ModelLasagnaPlates()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Plate1 = new ModelRenderer(this, 0, 0);
        this.Plate1.setRotationPoint(1.5F, 24F, -3.0F);
        this.Plate1.addBox(0.0F, 0.0F, 0.0F, 4, 1, 6, 0.0F);
        this.Plate2 = new ModelRenderer(this, 0, 0);
        this.Plate2.setRotationPoint(-1.6F, 23.8F, -3.0F);
        this.Plate2.addBox(0.0F, 0.0F, 0.0F, 4, 1, 6, 0.0F);
        this.Plate3 = new ModelRenderer(this, 0, 0);
        this.Plate3.setRotationPoint(-5.3F, 23.9F, -3.0F);
        this.Plate3.addBox(0.0F, 0.0F, 0.0F, 4, 1, 6, 0.0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int plates)
    {
        if (plates >= 1)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(this.Plate1.offsetX, this.Plate1.offsetY, this.Plate1.offsetZ);
            GL11.glTranslatef(this.Plate1.rotationPointX * f5, this.Plate1.rotationPointY * f5, this.Plate1.rotationPointZ * f5);
            GL11.glScaled(1.0D, 0.1D, 1.0D);
            GL11.glTranslatef(-this.Plate1.offsetX, -this.Plate1.offsetY, -this.Plate1.offsetZ);
            GL11.glTranslatef(-this.Plate1.rotationPointX * f5, -this.Plate1.rotationPointY * f5, -this.Plate1.rotationPointZ * f5);
            this.Plate1.render(f5);
            GL11.glPopMatrix();
        }
        if (plates >= 2)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(this.Plate2.offsetX, this.Plate2.offsetY, this.Plate2.offsetZ);
            GL11.glTranslatef(this.Plate2.rotationPointX * f5, this.Plate2.rotationPointY * f5, this.Plate2.rotationPointZ * f5);
            GL11.glScaled(1.0D, 0.1D, 1.0D);
            GL11.glTranslatef(-this.Plate2.offsetX, -this.Plate2.offsetY, -this.Plate2.offsetZ);
            GL11.glTranslatef(-this.Plate2.rotationPointX * f5, -this.Plate2.rotationPointY * f5, -this.Plate2.rotationPointZ * f5);
            this.Plate2.render(f5);
            GL11.glPopMatrix();
        }
        if (plates >= 3)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(this.Plate3.offsetX, this.Plate3.offsetY, this.Plate3.offsetZ);
            GL11.glTranslatef(this.Plate3.rotationPointX * f5, this.Plate3.rotationPointY * f5, this.Plate3.rotationPointZ * f5);
            GL11.glScaled(1.0D, 0.1D, 1.0D);
            GL11.glTranslatef(-this.Plate3.offsetX, -this.Plate3.offsetY, -this.Plate3.offsetZ);
            GL11.glTranslatef(-this.Plate3.rotationPointX * f5, -this.Plate3.rotationPointY * f5, -this.Plate3.rotationPointZ * f5);
            this.Plate3.render(f5);
            GL11.glPopMatrix();
        }
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
