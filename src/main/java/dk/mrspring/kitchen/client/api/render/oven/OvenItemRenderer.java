package dk.mrspring.kitchen.client.api.render.oven;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.tileentity.TileEntityClientOven;

/**
 * Created on 03-04-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public abstract class OvenItemRenderer
{
    public abstract void render(TileEntityClientOven oven);

    public void translateToSlot(int slot)
    {
        translateInside();
        // TODO: Translate.
    }

    public void translateInside()
    {
        // TODO: Translate.
    }
}
