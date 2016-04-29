package dk.mrspring.kitchen.client.model.jam;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.model.ModelBase;

@SideOnly(Side.CLIENT)
public class ModelJam0 extends ModelBase
{
    public ModelJam0()
    {
        super("jam.png", 32, 32);

        this.addBox(0, 3, -1F, 22.5F, 0.5F, 1, 1, 1);
        this.addBox(5, 3, -2.5F, 22.5F, -0.5F, 1, 1, 1);
        this.addBox(5, 9, -0.5F, 22.5F, -2F, 1, 1, 1);
        this.addBox(0, 0, -2.5F, 22.5F, 1F, 1, 1, 1);
        this.addBox(5, 0, 1F, 22.5F, 1F, 1, 1, 1);
        this.addBox(5, 6, 1F, 22.5F, -2.5F, 1, 1, 1);
        this.addBox(0, 9, 0.5F, 22.5F, -0.5F, 1, 1, 1);
        this.addBox(0, 6, -2F, 22.5F, -2.5F, 1, 1, 1);
    }
}
