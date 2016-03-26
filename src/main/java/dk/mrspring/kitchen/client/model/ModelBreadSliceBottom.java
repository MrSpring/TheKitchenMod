package dk.mrspring.kitchen.client.model;

import dk.mrspring.kitchen.ModInfo;

public class ModelBreadSliceBottom extends ModelBase
{
    public ModelBreadSliceBottom()
    {
        super(ModInfo.toResource("textures/models/bread_slice_bottom.png"), 64, 32);

        this.addBox(0, 0, -4F, 22F, -5F, 8, 1, 10);
        this.addBox(0, 0, -4F, 23F, -5F, 8, 1, 10);
        this.addBox(0, 12, -4F, 22F, 5F, 8, 1, 1);
        this.addBox(19, 12, -5F, 22F, -5F, 1, 1, 10);
        this.addBox(0, 18, 4F, 22F, -5F, 1, 1, 10);
        this.addBox(0, 15, -4F, 22F, -6F, 8, 1, 1);
    }
}
