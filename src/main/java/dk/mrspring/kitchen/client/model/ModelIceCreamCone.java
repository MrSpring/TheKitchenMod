package dk.mrspring.kitchen.client.model;

import dk.mrspring.kitchen.client.util.ClientUtils;

public class ModelIceCreamCone extends ModelBase
{
    public ModelIceCreamCone()
    {
        super(ClientUtils.modelTexture("ice_cream_cone"), 32, 16);

        this.addBox(0, 8, 1.5F, 13F, -1.5F, 1, 3, 3);
        this.addBox(0, 0, -0.5F, 22F, -0.5F, 1, 2, 1);
        this.addBox(1, 4, -1.5F, 10F, -3.5F, 3, 3, 1);
        this.addBox(0, 0, -1F, 19F, -1F, 2, 3, 2);
        this.addBox(0, 0, -1.5F, 13F, -2.5F, 3, 3, 1);
        this.addBox(16, 6, 1.5F, 10F, 1.5F, 1, 3, 1);
        this.addBox(10, 0, -1.5F, 13F, 1.5F, 3, 3, 1);
        this.addBox(9, 2, -2.5F, 13F, -1.5F, 1, 3, 3);
        this.addBox(1, 1, -1.5F, 10F, 2.5F, 3, 3, 1);
        this.addBox(13, 10, -3.5F, 10F, -1.5F, 1, 3, 3);
        this.addBox(6, 9, 2.5F, 10F, -1.5F, 1, 3, 3);
        this.addBox(10, 8, 1.5F, 10F, -2.5F, 1, 3, 1);
        this.addBox(13, 9, -2.5F, 10F, -2.5F, 1, 3, 1);
        this.addBox(3, 11, -2.5F, 10F, 1.5F, 1, 3, 1);
        this.addBox(0, 4, -1.5F, 16F, -1.5F, 3, 3, 3);
    }
}
