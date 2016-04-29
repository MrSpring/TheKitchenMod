package dk.mrspring.kitchen.client.model.jam;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.model.ModelBase;

@SideOnly(Side.CLIENT)
public class ModelJam3 extends ModelBase
{
    public ModelJam3()
    {
        super("jam.png", 32, 32);

        this.addBox(0, 6, -3F, 22.5F, -3F, 6, 1, 6);
    }
}
