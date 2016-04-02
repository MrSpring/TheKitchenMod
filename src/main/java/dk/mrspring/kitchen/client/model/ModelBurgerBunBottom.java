package dk.mrspring.kitchen.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBurgerBunBottom extends ModelBase
{
    public ModelBurgerBunBottom()
    {
        super("burger_bun_bottom", 64, 32);

        this.addBox(0, 0, -5F, 22F, -5F, 10, 1, 10);
        this.addBox(0, 0, -5F, 23F, -5F, 10, 1, 10);
        this.addBox(0, 12, -5F, 22F, 5F, 10, 1, 1);
        this.addBox(19, 12, -6F, 22F, -5F, 1, 1, 10);
        this.addBox(0, 18, 5F, 22F, -5F, 1, 1, 10);
        this.addBox(0, 15, -5F, 22F, -6F, 10, 1, 1);
    }
}
