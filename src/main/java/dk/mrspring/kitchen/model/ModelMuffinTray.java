package dk.mrspring.kitchen.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelMuffinTray extends ModelBase
{
    public ModelRenderer trayBase;
    public ModelRenderer cup1;
    public ModelRenderer cup2;
    public ModelRenderer cup3;
    public ModelRenderer cup4;
    public ModelRenderer cup5;
    public ModelRenderer cup6;
    public ModelRenderer[] rawMuffins = new ModelRenderer[6];

    public ModelMuffinTray()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.trayBase = make(0, 0, -6.5F, 17F, -3F, 0F, 0F, 0F, 13, 1, 9);

        cup1 = make(26, 17, 2.5F, 18F, 2F, 0F, 0F, 0F, 3, 2, 3);
        cup2 = make(0, 17, -5.5F, 18F, 2F, 0F, 0F, 0F, 3, 2, 3);
        cup3 = make(13, 17, -1.5F, 18F, 2F, 0F, 0F, 0F, 3, 2, 3);
        cup4 = make(26, 11, 2.5F, 18F, -2F, 0F, 0F, 0F, 3, 2, 3);
        cup5 = make(13, 11, -1.5F, 18F, -2F, 0F, 0F, 0F, 3, 2, 3);
        cup6 = make(0, 11, -5.5F, 18F, -2F, 0F, 0F, 0F, 3, 2, 3);

        rawMuffins[0] = make(26, 24, 2.5F, 16.5F, -2F, 0F, 0F, 0F, 3, 1, 3);
        rawMuffins[1] = make(13, 24, -1.5F, 16.5F, -2F, 0F, 0F, 0F, 3, 1, 3);
        rawMuffins[2] = make(0, 24, -5.5F, 16.5F, -2F, 0F, 0F, 0F, 3, 1, 3);
        rawMuffins[3] = make(26, 29, 2.5F, 16.5F, 2F, 0F, 0F, 0F, 3, 1, 3);
        rawMuffins[4] = make(13, 29, -1.5F, 16.5F, 2F, 0F, 0F, 0F, 3, 1, 3);
        rawMuffins[5] = make(0, 29, -5.5F, 16.5F, 2F, 0F, 0F, 0F, 3, 1, 3);
    }

    private ModelRenderer make(int texX, int texY, float rotPntX, float rotPntY, float rotPntZ, float boxX, float boxY, float boxZ, int boxW, int boxH, int boxL)
    {
        ModelRenderer newRenderer = new ModelRenderer(this, texX, texY);
        newRenderer.setRotationPoint(rotPntX, rotPntY, rotPntZ);
        newRenderer.addBox(boxX, boxY, boxZ, boxW, boxH, boxL);
        return newRenderer;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, boolean[] filled, float[][] colors)
    {
        this.trayBase.render(f5);
        this.cup1.render(f5);
        this.cup2.render(f5);
        this.cup3.render(f5);
        this.cup4.render(f5);
        this.cup5.render(f5);
        this.cup6.render(f5);
        for (int i = 0; i < rawMuffins.length && i < filled.length; i++)
            if (filled[i])
            {
                float[] color = colors[i];
                GL11.glColor4f(color[0] / 255, color[1] / 255, color[2] / 255, 1F);
                rawMuffins[i].render(f5);
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
