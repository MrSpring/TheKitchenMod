package dk.mrspring.kitchen.client.model.block;

import dk.mrspring.kitchen.client.model.IRenderParameter;
import dk.mrspring.kitchen.client.model.ModelBase;
import dk.mrspring.kitchen.client.model.ModelPart;
import net.minecraft.entity.Entity;

public class ModelTimer extends ModelBase<ModelTimer.Parameters>
{
    ModelPart arm;

    public ModelTimer()
    {
        super("timer", 32, 32);

        arm = addPart(2F, 21F, -0.5F).addBox(11, 0, -0.5F, -2.5F, 0F, 1, 3, 1);
        addBox(0, 15, 0F, 23F, 0F, 4, 1, 1);
        addBox(0, 12, 0F, 18F, 0F, 4, 1, 1);
        addBox(0, 0, 0F, 19F, 0F, 4, 4, 1);
        addBox(0, 6, -1F, 19F, 0F, 1, 4, 1);
        addBox(5, 6, 4F, 19F, 0F, 1, 4, 1);
    }

    @Override
    public void preRender(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, RenderContext context)
    {
        super.preRender(entity, f, f1, f2, f3, f4, f5, context);
        arm.rotateAngleZ = context.parameters.progress * (float) Math.PI * 2F;
    }

    @Override
    public ModelTimer.Parameters makeDefaultParameter()
    {
        return new ModelTimer.Parameters();
    }

    public static class Parameters implements IRenderParameter
    {
        float progress = 0F;
    }
}
