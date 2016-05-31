package dk.mrspring.kitchen.model.jam;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.model.ModelBase;

@SideOnly(Side.CLIENT)
public class ModelJam2 extends ModelBase
{
    public ModelJam2()
    {
        super("jam", 32, 32);

        this.addBox(0, 0, -3F, 22.5F, 1F, 4, 1, 1);
        this.addBox(5, 0, 1F, 22.5F, 0F, 1, 1, 2);
        this.addBox(0, 9, 0F, 22.5F, 0F, 1, 1, 1);
        this.addBox(0, 6, -2F, 22.5F, -2.5F, 4, 1, 2);
    }
}