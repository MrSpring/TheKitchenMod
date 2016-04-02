package dk.mrspring.kitchen.client.model.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.model.ModelBase;

@SideOnly(Side.CLIENT)
public class ModelCraftingCabinet extends ModelBase
{
    public ModelCraftingCabinet()
    {
        super("crafting_cabinet", 64, 64);

        addBox(0, 48, -7F, 11F, -7F, 14, 13, 1);
        addBox(1, 0, -8F, 11F, -6F, 16, 13, 14);
        addBox(0, 28, -8F, 8F, -8F, 16, 3, 16);
    }
}
