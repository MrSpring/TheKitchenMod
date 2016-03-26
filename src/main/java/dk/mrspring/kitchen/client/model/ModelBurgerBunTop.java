package dk.mrspring.kitchen.client.model;

import dk.mrspring.kitchen.ModInfo;

public class ModelBurgerBunTop extends ModelBase
{
    public ModelBurgerBunTop()
    {
        super(ModInfo.toResource("textures/models/burger_bun_top.png"), 64, 32);

        this.addBox(0, 0, -5F, 12F, -5F, 10, 1, 10);
        this.addBox(0, 17, -5F, 22F, 5F, 10, 2, 1);
        this.addBox(0, 13, -5F, 22F, -6F, 10, 2, 1);
        this.addBox(41, 13, 5F, 22F, -5F, 1, 2, 10);
        this.addBox(41, 0, -6F, 22F, -5F, 1, 2, 10);
        this.addBox(0, 0, -5F, 22F, -5F, 10, 2, 10);
    }
}
