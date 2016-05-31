package dk.mrspring.kitchen.model.butter;

import dk.mrspring.kitchen.model.ModelBase;

public class ModelButter1 extends ModelBase
{
    public ModelButter1()
    {
        super("butter", 32, 32);

        addBox(0, 0, -2F, 22F, -3F, 3, 2, 6);
        addBox(0, 0, -2F, 23F, 3F, 1, 1, 1);
        addBox(0, 0, -2F, 23F, -4F, 1, 1, 1);
        addBox(0, 0, 1F, 23F, 3F, 1, 1, 1);
        addBox(0, 0, -1F, 23F, 3F, 2, 1, 2);
        addBox(0, 0, 1F, 23F, 0F, 2, 1, 3);
        addBox(0, 0, 1F, 23F, 0F, 1, 1, 3);
        addBox(0, 0, 1F, 23F, -3F, 3, 1, 3);
        addBox(0, 0, -3F, 22F, -3F, 1, 2, 1);
        addBox(0, 0, -4F, 23F, -1F, 1, 1, 3);
        addBox(0, 0, -1F, 23F, -5F, 2, 1, 2);
        addBox(0, 0, -3F, 23F, 0F, 1, 1, 3);
        addBox(0, 0, -3F, 23F, -1F, 1, 1, 4);
    }
}