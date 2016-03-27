package dk.mrspring.kitchen.client.model.block;

import dk.mrspring.kitchen.client.model.IRenderParameter;
import dk.mrspring.kitchen.client.model.ModelBase;
import dk.mrspring.kitchen.client.model.ModelPart;
import dk.mrspring.kitchen.client.util.ClientUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelWaffleIron extends ModelBase<ModelWaffleIron.Parameters>
{
    ModelPart top, base, waffle;
    ResourceLocation on, off;

    public ModelWaffleIron()
    {
        super("waffle_iron", 64, 32);

        this.off = super.texture;
        this.on = ClientUtils.modelTexture("waffle_iron_on");

        top = addPart(0F, 19F, 5F)
                .addBox(13, 12, -6F, 19F, 5F, 12, 2, 8)
                .addBox(0, 18, 4.5F, -0.5F, 8.5F, 1, 1, 7)
                .addBox(0, 4, -3F, -1F, -1F, 2, 1, 1)
                .addBox(0, 15, -4.5F, -0.5F, -8.5F, 9, 1, 1)
                .addBox(0, 4, 2F, -1F, -1F, 2, 1, 1)
                .addBox(0, 15, -4.5F, -0.5F, -2.5F, 9, 1, 1)
                .addBox(0, 18, -5.5F, -0.5F, -8.5F, 1, 1, 7)
                .addBox(2, 20, -0.5F, -0.5F, -7.5F, 1, 1, 5);
        base = addPart()
                .addBox(0, 0, -5F, 23F, 2F, 1, 1, 1)
                .addBox(0, 0, 4F, 23F, -3F, 1, 1, 1)
                .addBox(0, 0, 4F, 23F, 2F, 1, 1, 1)
                .addBox(0, 0, -5F, 23F, -3F, 1, 1, 1)
                .addBox(0, 4, -3F, 19F, 4F, 2, 2, 1)
                .addBox(0, 4, 2F, 19F, 4F, 2, 2, 1)
                .addBox(0, 0, -6F, 20F, -4F, 12, 3, 8)
                .addBox(0, 18, -5.5F, 19.5F, -3.5F, 1, 1, 7)
                .addBox(0, 15, -4.5F, 19.5F, -3.5F, 9, 1, 1)
                .addBox(0, 18, 4.5F, 19.5F, -3.5F, 1, 1, 7)
                .addBox(0, 18, 4.5F, 19.5F, -3.5F, 1, 1, 7)
                .addBox(0, 15, -4.5F, 19.5F, 2.5F, 9, 1, 1)
                .addBox(2, 20, -0.5F, 19.5F, 2.5F, 1, 1, 5);
        waffle = addPart().addBox(0, 0, -5F, -19.75F, -3F, 10, 1, 6);
    }

    @Override
    public void preRender(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, RenderContext context)
    {
        super.preRender(entity, f, f1, f2, f3, f4, f5, context);
        if (context.parameters == null) context.parameters = new Parameters();
        top.setRotation(context.parameters.lidAngle + context.parameters.lidDirection * context.partial, 0F, 0F);
    }

    @Override
    public Parameters makeDefaultParameter()
    {
        return new Parameters();
    }

    @Override
    public ResourceLocation getTexture(RenderContext context)
    {
        if (context.parameters.on) return on;
        else return off;
    }

    public static class Parameters implements IRenderParameter
    {
        float lidAngle = 0F, lidDirection = 0F;
        boolean on = false;
        float[] waffleColor = new float[]{0F, 0F, 0F};
    }

/*
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int lidAngle, int lidDirection, int waffleState, float[] doughColor, float partial)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        bottomBase1.render(f5);
        bottomBase2.render(f5);
        bottomBase3.render(f5);
        bottomBase4.render(f5);
        bottomBase5.render(f5);
        bottomBase6.render(f5);
        bottomBase7.render(f5);
        bottomBase8.render(f5);
        bottomBase9.render(f5);
        bottomBase10.render(f5);
        bottomBase11.render(f5);
        bottomBase12.render(f5);

//        System.out.println(f + ", " + f1 + ", " + f2 + ", " + f3 + ", " + f4 + ", " + f5);

        float rotation = -lidAngle;
        rotation += (float) -lidDirection * partial;
        rotation = Math.max(-15, Math.min(0, rotation));
//        System.out.println(lidDirection);
        rotation *= 0.12F;
        topBase1.rotateAngleX = rotation;
        topBase1.render(f5);

        topBase2.rotateAngleX = rotation;
        topBase2.render(f5);

        topBase3.rotateAngleX = rotation;
        topBase3.render(f5);

        topBase4.rotateAngleX = rotation;
        topBase4.render(f5);

        topBase5.rotateAngleX = rotation;
        topBase5.render(f5);

        topBase6.rotateAngleX = rotation;
        topBase6.render(f5);

        topBase7.rotateAngleX = rotation;
        topBase7.render(f5);

        topBase8.rotateAngleX = rotation;
        topBase8.render(f5);

        if (waffleState > 0)
        {
            if (waffleState == 1)
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.toTexture("textures/models/raw_waffle.png")));
            else if (waffleState == 2)
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.toTexture("textures/models/cooked_waffle.png")));
            else
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.toTexture("textures/models/burnt_waffle.png")));
            GL11.glPushMatrix();
            GL11.glColor4f(doughColor[0] / 255, doughColor[1] / 255, doughColor[2] / 255, 1);
            waffleMesh.render(f5);
            GL11.glPopMatrix();
        }
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
*/

}
