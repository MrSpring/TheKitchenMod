package dk.mrspring.kitchen.client.model.block;

import dk.mrspring.kitchen.client.model.IRenderParameter;
import dk.mrspring.kitchen.client.model.ModelBase;
import dk.mrspring.kitchen.client.model.ModelPart;
import net.minecraft.entity.Entity;

public class ModelToaster extends ModelBase<ModelToaster.Parameters>
{
    ModelPart button;

    public ModelToaster()
    {
        super("toaster", 64, 32);

        addBox(0, 18, -3F, 18F, 2F, 6, 4, 1);
        addBox(0, 0, 3F, 18F, -3F, 2, 4, 6);
        addBox(0, 24, -3F, 18F, -3F, 6, 4, 1);
        addBox(17, 0, -4F, 18F, -3F, 1, 4, 6);
        addBox(0, 11, -3F, 18F, -1F, 6, 4, 2);
        addBox(32, 0, -4F, 22F, -3F, 9, 1, 6);
        addBox(0, 0, -3.5F, 23F, 1.5F, 1, 1, 1);
        addBox(0, 0, -3.5F, 23F, -2.5F, 1, 1, 1);
        addBox(0, 0, 3.5F, 23F, -2.5F, 1, 1, 1);
        addBox(0, 0, 3.5F, 23F, 1.5F, 1, 1, 1);

        button = addPart().addBox(17, 11, 5F, 19F, -1F, 1, 1, 2);
    }

    @Override
    public void preRender(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, RenderContext context)
    {
        if (context.parameters.down) button.rotationPointY = 20F;
        else button.rotationPointY = 19F;
    }

    @Override
    public Parameters makeDefaultParameter()
    {
        return new Parameters();
    }

    public static class Parameters implements IRenderParameter
    {
        boolean down = false;
    }
}
