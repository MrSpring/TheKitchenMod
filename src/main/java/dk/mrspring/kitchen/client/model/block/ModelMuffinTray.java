package dk.mrspring.kitchen.client.model.block;

import dk.mrspring.kitchen.client.model.IRenderParameter;
import dk.mrspring.kitchen.client.model.ModelBase;
import dk.mrspring.kitchen.client.model.ModelPart;
import net.minecraft.entity.Entity;

public class ModelMuffinTray extends ModelBase<ModelMuffinTray.Parameters>
{
    ModelPart[] muffins;

    public ModelMuffinTray()
    {
        super("muffin_tray", 64, 64);

        addBox(0, 0, -6.5F, 17F, -3F, 13, 1, 9);
        addBox(26, 17, 2.5F, 18F, 2F, 3, 2, 3);
        addBox(0, 17, -5.5F, 18F, 2F, 3, 2, 3);
        addBox(13, 17, -1.5F, 18F, 2F, 3, 2, 3);
        addBox(13, 17, 2.5F, 18F, -2F, 3, 2, 3);
        addBox(13, 11, -1.5F, 18F, -2F, 3, 2, 3);
        addBox(0, 11, -5.5F, 18F, -2F, 3, 2, 3);

        muffins = new ModelPart[]{
                addPart().addBox(26, 24, 2.5F, 16.5F, -2F, 3, 1, 3),
                addPart().addBox(13, 24, -1.5F, 16.5F, -2F, 3, 1, 3),
                addPart().addBox(0, 24, -5.5F, 16.5F, -2F, 3, 1, 3),
                addPart().addBox(26, 29, 2.5F, 16.5F, 2F, 3, 1, 3),
                addPart().addBox(13, 29, -1.5F, 16.5F, 2F, 3, 1, 3),
                addPart().addBox(0, 29, -5.5F, 16.5F, 2F, 3, 1, 3)
        };
    }

    @Override
    public void preRender(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, RenderContext context)
    {
        super.preRender(entity, f, f1, f2, f3, f4, f5, context);
        for (int i = 0; i < context.parameters.filled.length; i++)
            muffins[i].isHidden = context.parameters.filled[i];
    }

    @Override
    public Parameters makeDefaultParameter()
    {
        return new Parameters();
    }

    public static class Parameters implements IRenderParameter
    {
        boolean[] filled = new boolean[6];
        float[][] colors = new float[6][6]; // TODO: Apply
    }
}
