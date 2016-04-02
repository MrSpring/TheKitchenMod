package dk.mrspring.kitchen.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelPancake extends ModelBase
{
    public ModelPancake(boolean cooked)
    {
        super(cooked ? "pancake_cooked" : "pancake_raw", 16, 16);

        addBox(0, 6, -3F, 22.5F, -3F, 6, 1, 6);
    }
}
