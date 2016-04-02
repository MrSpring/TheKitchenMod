package dk.mrspring.kitchen.client.model.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.model.IRenderParameter;
import dk.mrspring.kitchen.client.model.ModelBase;
import dk.mrspring.kitchen.client.model.ModelPart;
import dk.mrspring.kitchen.client.util.ClientUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
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
    public void preRender(Entity entity, float f, float f1, float f2, float f3, float f4, float f5,
                          RenderContext context)
    {
        super.preRender(entity, f, f1, f2, f3, f4, f5, context);
        top.rotateAngleX = context.parameters.lidAngle + context.parameters.lidDirection * context.partial;
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
}
