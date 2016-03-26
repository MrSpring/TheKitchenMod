package dk.mrspring.kitchen.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelOven extends ModelBase
{
    //fields
    ModelRenderer BottomBase;
    ModelRenderer Top;
    ModelRenderer RightSide;
    ModelRenderer LeftSide;
    ModelRenderer Bottom;
    ModelRenderer Back;
    ModelRenderer LidBottom;
    ModelRenderer LidTop;
    ModelRenderer LidRight;
    ModelRenderer LidLeft;
    ModelRenderer LidWindow;
    ModelRenderer LidHandle;
    ModelRenderer Top1;
    ModelRenderer Top2;
    ModelRenderer Top3;
    ModelRenderer Top4;
    ModelRenderer TopBase;

    public ModelOven()
    {
        textureWidth = 64;
        textureHeight = 64;

        BottomBase = new ModelRenderer(this, 0, 32);
        BottomBase.addBox(0F, 0F, 0F, 16, 4, 13);
        BottomBase.setRotationPoint(-8F, 20F, -5F);
        BottomBase.setTextureSize(64, 64);
        BottomBase.mirror = true;
        setRotation(BottomBase, 0F, 0F, 0F);
        Top = new ModelRenderer(this, 0, 32);
        Top.addBox(0F, 0F, 0F, 16, 1, 13);
        Top.setRotationPoint(-8F, 9F, -5F);
        Top.setTextureSize(64, 64);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
        RightSide = new ModelRenderer(this, 32, 10);
        RightSide.addBox(0F, 0F, 0F, 2, 8, 14);
        RightSide.setRotationPoint(6F, 11F, -6F);
        RightSide.setTextureSize(64, 64);
        RightSide.mirror = true;
        setRotation(RightSide, 0F, 0F, 0F);
        LeftSide = new ModelRenderer(this, 0, 10);
        LeftSide.addBox(0F, 0F, 0F, 2, 8, 14);
        LeftSide.setRotationPoint(-8F, 11F, -6F);
        LeftSide.setTextureSize(64, 64);
        LeftSide.mirror = true;
        setRotation(LeftSide, 0F, 0F, 0F);
        Bottom = new ModelRenderer(this, 0, 49);
        Bottom.addBox(0F, 0F, 0F, 16, 1, 14);
        Bottom.setRotationPoint(-8F, 19F, -6F);
        Bottom.setTextureSize(64, 64);
        Bottom.mirror = true;
        setRotation(Bottom, 0F, 0F, 0F);
        Back = new ModelRenderer(this, 0, 0);
        Back.addBox(0F, 0F, 0F, 12, 8, 1);
        Back.setRotationPoint(-6F, 11F, 7F);
        Back.setTextureSize(64, 64);
        Back.mirror = true;
        setRotation(Back, 0F, 0F, 0F);


        LidBottom = new ModelRenderer(this, 33, 6);
        LidBottom.addBox(0F, 0F, 0F, 12, 2, 1);
        LidBottom.setRotationPoint(-6F, 17F, -6.5F);
        LidBottom.setTextureSize(64, 64);
        LidBottom.mirror = true;
        setRotation(LidBottom, 0F, 0F, 0F);

        LidTop = new ModelRenderer(this, 33, 6);
        LidTop.addBox(0F, -6F, 0F, 12, 2, 1);
        LidTop.setRotationPoint(-6F, 17F, -6.5F);
        LidTop.setTextureSize(64, 64);
        LidTop.mirror = true;
        setRotation(LidTop, 0F, 0F, 0F);

        LidRight = new ModelRenderer(this, 33, 0);
        LidRight.addBox(10F, -4F, 0F, 2, 4, 1);
        LidRight.setRotationPoint(-6F, 17F, -6.5F);
        LidRight.setTextureSize(64, 64);
        LidRight.mirror = true;
        setRotation(LidRight, 0F, 0F, 0F);

        LidLeft = new ModelRenderer(this, 33, 0);
        LidLeft.addBox(0F, -4F, 0F, 2, 4, 1);
        LidLeft.setRotationPoint(-6F, 17F, -6.5F);
        LidLeft.setTextureSize(64, 64);
        LidLeft.mirror = true;
        setRotation(LidLeft, 0F, 0F, 0F);

        LidWindow = new ModelRenderer(this, 23, 13);
        LidWindow.addBox(2F, -4F, 0.5F, 8, 4, 0);
        LidWindow.setRotationPoint(-6F, 17F, -6.5F);
        LidWindow.setTextureSize(64, 64);
        LidWindow.mirror = true;
        setRotation(LidWindow, 0F, 0F, 0F);

        LidHandle = new ModelRenderer(this, 40, 3);
        LidHandle.addBox(4F, -5.5F, -1F, 4, 1, 1);
        LidHandle.setRotationPoint(-6F, 17F, -6.5F);
        LidHandle.setTextureSize(64, 64);
        LidHandle.mirror = true;
        setRotation(LidHandle, 0F, 0F, 0F);


        Top1 = new ModelRenderer(this, 48, 50);
        Top1.addBox(0F, 0F, 0F, 4, 1, 4);
        Top1.setRotationPoint(2F, 8F, -4F);
        Top1.setTextureSize(64, 64);
        Top1.mirror = true;
        setRotation(Top1, 0F, 0F, 0F);
        Top2 = new ModelRenderer(this, 48, 57);
        Top2.addBox(0F, 0F, 0F, 4, 1, 4);
        Top2.setRotationPoint(2F, 8F, 3F);
        Top2.setTextureSize(64, 64);
        Top2.mirror = true;
        setRotation(Top2, 0F, 0F, 0F);
        Top3 = new ModelRenderer(this, 48, 50);
        Top3.addBox(0F, 0F, 0F, 4, 1, 4);
        Top3.setRotationPoint(-6F, 8F, 3F);
        Top3.setTextureSize(64, 64);
        Top3.mirror = true;
        setRotation(Top3, 0F, 0F, 0F);
        Top4 = new ModelRenderer(this, 48, 57);
        Top4.addBox(0F, 0F, 0F, 4, 1, 4);
        Top4.setRotationPoint(-6F, 8F, -4F);
        Top4.setTextureSize(64, 64);
        Top4.mirror = true;
        setRotation(Top4, 0F, 0F, 0F);
        TopBase = new ModelRenderer(this, 0, 49);
        TopBase.addBox(0F, 0F, 0F, 16, 1, 14);
        TopBase.setRotationPoint(-8F, 10F, -6F);
        TopBase.setTextureSize(64, 64);
        TopBase.mirror = true;
        setRotation(TopBase, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int lidAngle,
                       int lidDirection, float partial)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        BottomBase.render(f5);
        Top.render(f5);
        RightSide.render(f5);
        LeftSide.render(f5);
        Bottom.render(f5);
        Back.render(f5);

        float rotation = lidAngle;
        rotation += (float) lidDirection * partial;
        rotation = Math.max(0, Math.min(12, rotation));
        rotation *= 0.1F;

        LidBottom.rotateAngleX = rotation;
        LidBottom.render(f5);

        LidTop.rotateAngleX = rotation;
        LidTop.render(f5);

        LidRight.rotateAngleX = rotation;
        LidRight.render(f5);

        LidLeft.rotateAngleX = rotation;
        LidLeft.render(f5);

        LidWindow.rotateAngleX = rotation;
        LidWindow.render(f5);

        LidHandle.rotateAngleX = rotation;
        LidHandle.render(f5);

        Top1.render(f5);
        Top2.render(f5);
        Top3.render(f5);
        Top4.render(f5);
        TopBase.render(f5);
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
