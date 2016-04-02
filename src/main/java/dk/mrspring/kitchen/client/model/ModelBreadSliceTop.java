package dk.mrspring.kitchen.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBreadSliceTop extends ModelBase
{
    public ModelBreadSliceTop()
    {
        super("bread_slice_top", 64, 32);

        this.addBox(0, 0, -4F, 21F, -5F, 8, 1, 10);
        this.addBox(0, 0, -4F, 22F, -5F, 8, 2, 10);
        this.addBox(0, 17, -4F, 22F, 5F, 8, 2, 1);
        this.addBox(0, 13, -4F, 22F, -6F, 8, 2, 1);
        this.addBox(37, 13, 4F, 22F, -5F, 1, 2, 10);
        this.addBox(37, 0, -5F, 22F, -5F, 1, 2, 10);
    }
}
