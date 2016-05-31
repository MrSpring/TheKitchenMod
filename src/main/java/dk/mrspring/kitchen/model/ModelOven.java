package dk.mrspring.kitchen.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.tileentity.TileEntityOven;
import dk.mrspring.kitchen.tileentity.renderer.OpeningAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import static dk.mrspring.kitchen.ClientUtils.modelTexture;

@SideOnly(Side.CLIENT)
public class ModelOven extends ModelBase<ModelOven.Parameters>
{
    ModelPart base, hatch;
    ResourceLocation on, off;

    public ModelOven()
    {
        super("oven", 64, 64);

        off = super.texture;
        on = modelTexture("oven_active");

        base = addPart()
                .addBox(0, 32, -8F, 20F, -5F, 16, 4, 13)
                .addBox(0, 32, -8F, 9F, -5F, 16, 1, 13)
                .addBox(32, 10, 6F, 11F, -6F, 2, 8, 14)
                .addBox(0, 10, -8F, 11F, -6F, 2, 8, 14)
                .addBox(0, 49, -8F, 19F, -6F, 16, 1, 14)
                .addBox(0, 0, -6F, 11F, 7F, 12, 8, 1)
                .addBox(48, 50, 2F, 8F, -4F, 4, 1, 4)
                .addBox(48, 57, 2F, 8F, 3F, 4, 1, 4)
                .addBox(48, 50, -6F, 8F, 3F, 4, 1, 4)
                .addBox(48, 57, -6F, 8F, -4F, 4, 1, 4)
                .addBox(0, 49, -8F, 10F, -6F, 16, 1, 14);
        hatch = addPart(-6F, 17F, -6.5F)
                .addBox(33, 6, 0F, 0F, 0F, 12, 2, 1)
                .addBox(33, 6, 0F, -6F, 0F, 12, 2, 1)
                .addBox(33, 0, 10F, -4F, 0F, 2, 4, 1)
                .addBox(33, 0, 0F, -4F, 0F, 2, 4, 1)
                .addBox(23, 13, 2F, -4F, 0.5F, 8, 4, 0)
                .addBox(40, 3, 4F, -5.5F, -1F, 4, 1, 1);
    }

    @Override
    public void preRender(Entity entity, float f, float f1, float f2, float f3, float f4, float f5,
                          RenderContext context)
    {
        super.preRender(entity, f, f1, f2, f3, f4, f5, context);
        hatch.rotateAngleX = context.parameters.opening.getRadians(context.partial);
    }

    @Override
    public ResourceLocation getTexture(RenderContext context)
    {
        if (context.parameters.on) return on;
        else return off;
    }

    @Override
    public Parameters makeDefaultParameter()
    {
        return new Parameters();
    }

    public static class Parameters implements IRenderParameter
    {
        public boolean on;
        public OpeningAnimation opening;

        public Parameters(TileEntityOven oven)
        {
            this(oven.isCooking(), oven.getOpeningAnimation());
        }

        public Parameters(boolean state, OpeningAnimation opening)
        {
            this.on = state;
            this.opening = opening;
        }

        public Parameters()
        {
            this(false, new OpeningAnimation(0, 65, 0, false));
        }
    }
}