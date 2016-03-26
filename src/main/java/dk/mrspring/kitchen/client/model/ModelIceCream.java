package dk.mrspring.kitchen.client.model;

import dk.mrspring.kitchen.client.util.ClientUtils;

public class ModelIceCream extends ModelBase
{
    public ModelIceCream()
    {
        super(ClientUtils.modelTexture("ice_cream"), 32, 32);

        this.addBox(0, 0, -2.5F, 21F, -1F, 1, 3, 2);
        this.addBox(0, 0, -1F, 19.5F, -1F, 2, 1, 2);
        this.addBox(0, 0, -1F, 21F, 1.5F, 2, 3, 1);
        this.addBox(0, 0, -1F, 21F, -2.5F, 2, 3, 1);
        this.addBox(0, 0, 1.5F, 21F, -1F, 1, 3, 2);
        this.addBox(0, 0, -2F, 20F, -2F, 4, 4, 4);
    }
}
