package dk.mrspring.kitchen.client.model.block;

import dk.mrspring.kitchen.client.model.ModelBase;

public class ModelPan extends ModelBase
{
    public ModelPan()
    {
        super("pan", 64, 32);

        addBox(0, 0, -3F, 23F, -3F, 6, 1, 6);
        addBox(15, 8, 3F, 22F, -3F, 1, 1, 6);
        addBox(25, 0, -3F, 22F, 3F, 6, 1, 1);
        addBox(0, 8, -4F, 22F, -3F, 1, 1, 6);
        addBox(25, 3, -3F, 22F, -4F, 6, 1, 1);
        addBox(25, 6, 3.5F, 22.5F, -0.5F, 4, 1, 1);
    }
}
