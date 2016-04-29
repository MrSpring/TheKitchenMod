package dk.mrspring.kitchen.client.model.jam;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.model.ModelBase;

@SideOnly(Side.CLIENT)
public class ModelJam1 extends ModelBase
{
    public ModelJam1()
    {
        super("jam.png", 32, 32);

        this.addBox(0, 0, -2.5F, 22.5F, 1F, 3, 1, 1);
        this.addBox(5, 0, 1F, 22.5F, 0F, 1, 1, 2);
        this.addBox(0, 9, 0F, 22.5F, 0F, 1, 1, 1);
        this.addBox(0, 6, 1F, 22.5F, -2.5F, 1, 1, 2);
        this.addBox(0, 6, -2F, 22.5F, -2.5F, 2, 1, 2);
    }
}
