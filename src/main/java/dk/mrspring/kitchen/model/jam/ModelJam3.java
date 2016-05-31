package dk.mrspring.kitchen.model.jam;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.model.ModelBase;

@SideOnly(Side.CLIENT)
public class ModelJam3 extends ModelBase
{
    public ModelJam3()
    {
        super("jam", 32, 32);

        this.addBox(0, 6, -3F, 22.5F, -3F, 6, 1, 6);
    }
}