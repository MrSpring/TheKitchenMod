package dk.mrspring.kitchen.client.model.block;

import dk.mrspring.kitchen.client.model.IRenderParameter;
import dk.mrspring.kitchen.client.model.ModelBase;
import dk.mrspring.kitchen.client.model.ModelPart;
import net.minecraft.entity.Entity;

public class ModelKitchenCabinet extends ModelBase<ModelKitchenCabinet.Parameters>
{
    ModelPart corner;

    public ModelKitchenCabinet()
    {
        super("kitchen_cabinet", 64, 64);

        corner = addPart().addBox(15, 0, -6F, 11F, -8F, 14, 13, 2);

        addBox(1, 0, -8F, 11F, -6F, 16, 13, 14);
        addBox(0, 28, -8F, 8F, -8F, 16, 3, 16);
        addBox(0, 0, 7F, 12F, -6F, 6, 11, 1);
        addBox(48, 0, -7F, 12F, -6F, 6, 11, 1);
        addBox(1, 29, -4F, 15F, -8F, 2, 2, 1);
        addBox(8, 29, 2F, 15F, -8F, 2, 2, 1);
    }

    @Override
    public void preRender(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, RenderContext context)
    {
        corner.isHidden = !context.parameters.corner;
    }

    @Override
    public Parameters makeDefaultParameter()
    {
        return new Parameters();
    }

    public static class Parameters implements IRenderParameter
    {
        boolean corner = false;
    }
}