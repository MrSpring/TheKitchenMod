package dk.mrspring.kitchen.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBreadSliceBottom extends ModelBase
{
    public ModelBreadSliceBottom()
    {
        super("bread_slice_bottom", 64, 32);

        this.addBox(0, 0, -4F, 22F, -5F, 8, 1, 10);
        this.addBox(0, 0, -4F, 23F, -5F, 8, 1, 10);
        this.addBox(0, 12, -4F, 22F, 5F, 8, 1, 1);
        this.addBox(19, 12, -5F, 22F, -5F, 1, 1, 10);
        this.addBox(0, 18, 4F, 22F, -5F, 1, 1, 10);
        this.addBox(0, 15, -4F, 22F, -6F, 8, 1, 1);
    }
}