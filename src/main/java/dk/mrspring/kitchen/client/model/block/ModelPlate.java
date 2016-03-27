package dk.mrspring.kitchen.client.model.block;

import dk.mrspring.kitchen.client.model.ModelBase;

public class ModelPlate extends ModelBase
{
    public ModelPlate()
    {
        super("plate", 64, 32);

        addBox(44, 11, -3F, 22F, 4F, 6, 1, 3);
        addBox(16, 10, -5F, 22F, 5F, 2, 1, 1);
        addBox(24, 13, -7F, 22F, -3F, 3, 1, 6);
        addBox(44, 16, -3F, 22F, -7F, 6, 1, 3);
        addBox(16, 21, -6F, 22F, -5F, 1, 1, 2);
        addBox(16, 13, 3F, 22F, -6F, 2, 1, 1);
        addBox(16, 17, 5F, 22F, 3F, 1, 1, 2);
        addBox(24, 5, 4F, 22F, -3F, 3, 1, 6);
        addBox(0, 25, -3F, 22.5F, -3F, 6, 1, 6);
        addBox(9, 17, 5F, 22F, -5F, 1, 1, 2);
        addBox(9, 10, 3F, 22F, 5F, 2, 1, 1);
        addBox(9, 21, -6F, 22F, 3F, 1, 1, 2);
        addBox(9, 13, -5F, 22F, -6F, 2, 1, 1);
        addBox(0, 13, -5F, 22F, -5F, 2, 1, 2);
        addBox(0, 9, 3F, 22F, -5F, 2, 1, 2);
        addBox(0, 21, 3F, 22F, 3F, 2, 1, 2);
        addBox(25, 22, -3F, 22.5F, -4F, 6, 1, 1);
        addBox(0, 17, -5F, 22F, 3F, 2, 1, 2);
        addBox(25, 25, -5F, 22.5F, -3F, 1, 1, 6);
        addBox(40, 25, 3F, 22.5F, -3F, 1, 1, 6);
        addBox(40, 22, -3F, 22.5F, 3F, 6, 1, 1);
    }
}
