package dk.mrspring.kitchen.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

/**
 * Grinder - MrSpring
 * Created using Tabula 4.1.1
 */
public class ModelGrinder extends ModelBase
{
    public ModelRenderer base1;
    public ModelRenderer base2;
    public ModelRenderer stand;
    public ModelRenderer mainBase;
    public ModelRenderer top1;
    public ModelRenderer top2;
    public ModelRenderer top3;
    public ModelRenderer top4;
    public ModelRenderer mouthBase;
    public ModelRenderer mouth1;
    public ModelRenderer mouth2;
    public ModelRenderer mouth3;
    public ModelRenderer mouth4;
    public ModelRenderer handleConnector;
    public ModelRenderer handleMiddle;
    public ModelRenderer handleHandle;

    public ModelGrinder()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.base1 = new ModelRenderer(this, 18, 6);
        this.base1.setRotationPoint(-4.0F, 23.0F, -4.0F);
        this.base1.addBox(0.0F, 0.0F, 0.0F, 8, 1, 8, 0.0F);
        this.stand = new ModelRenderer(this, 35, 22);
        this.stand.setRotationPoint(-1.0F, 18.0F, -1.0F);
        this.stand.addBox(0.0F, 0.0F, 0.0F, 2, 4, 3, 0.0F);
        this.mouth2 = new ModelRenderer(this, 0, 25);
        this.mouth2.setRotationPoint(-3.0F, 20.0F, -6.0F);
        this.mouth2.addBox(0.0F, 0.0F, 0.0F, 6, 1, 4, 0.0F);
        this.mainBase = new ModelRenderer(this, 18, 16);
        this.mainBase.setRotationPoint(-2.0F, 15.0F, -1.0F);
        this.mainBase.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.base2 = new ModelRenderer(this, 15, 25);
        this.base2.setRotationPoint(-3.0F, 22.0F, -3.0F);
        this.base2.addBox(0.0F, 0.0F, 0.0F, 6, 1, 6, 0.0F);
        this.mouth4 = new ModelRenderer(this, 0, 25);
        this.mouth4.setRotationPoint(-3.0F, 16.0F, -6.0F);
        this.mouth4.addBox(0.0F, 0.0F, 0.0F, 6, 1, 4, 0.0F);
        this.top2 = new ModelRenderer(this, 0, 20);
        this.top2.setRotationPoint(-2.0F, 12.0F, -2.0F);
        this.top2.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1, 0.0F);
        this.top3 = new ModelRenderer(this, 0, 12);
        this.top3.setRotationPoint(-3.0F, 12.0F, -1.0F);
        this.top3.addBox(0.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
        this.handleMiddle = new ModelRenderer(this, 8, 9);
        this.handleMiddle.setRotationPoint(4.5F, 17.0F, 1.0F);
        this.handleMiddle.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
        this.top4 = new ModelRenderer(this, 0, 20);
        this.top4.setRotationPoint(-2.0F, 12.0F, 3.0F);
        this.top4.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1, 0.0F);
        this.top1 = new ModelRenderer(this, 0, 12);
        this.top1.setRotationPoint(2.0F, 12.0F, -1.0F);
        this.top1.addBox(0.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
        this.mouthBase = new ModelRenderer(this, 0, 0);
        this.mouthBase.setRotationPoint(-3.0F, 17.0F, -2.0F);
        this.mouthBase.addBox(0.0F, 0.0F, 0.0F, 6, 3, 2, 0.0F);
        this.handleConnector = new ModelRenderer(this, 40, 30);
        this.handleConnector.setRotationPoint(1.5F, 17.5F, 1.0F);
        this.handleConnector.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.mouth3 = new ModelRenderer(this, 11, 12);
        this.mouth3.setRotationPoint(-4.0F, 17.0F, -6.0F);
        this.mouth3.addBox(0.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
        this.mouth1 = new ModelRenderer(this, 11, 12);
        this.mouth1.setRotationPoint(3.0F, 17.0F, -6.0F);
        this.mouth1.addBox(0.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
        this.setRotateAngle(mouth1, 0.0F, 0.0F, -0.0017453292519943296F);
        this.handleHandle = new ModelRenderer(this, 35, 16);
        this.handleHandle.setRotationPoint(5.0F, 20.5F, 0.5F);
        this.handleHandle.addBox(0.0F, 0.0F, 0.0F, 3, 2, 2, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(0,0,0.125F);
        this.base1.render(f5);
        this.stand.render(f5);
        this.mouth2.render(f5);
        this.mainBase.render(f5);
        this.base2.render(f5);
        this.mouth4.render(f5);
        this.top2.render(f5);
        this.top3.render(f5);
        this.handleMiddle.render(f5);
        this.top4.render(f5);
        this.top1.render(f5);
        this.mouthBase.render(f5);
        this.handleConnector.render(f5);
        this.mouth3.render(f5);
        this.mouth1.render(f5);
        this.handleHandle.render(f5);
        GL11.glPopMatrix();
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