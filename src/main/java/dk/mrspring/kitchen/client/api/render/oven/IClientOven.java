package dk.mrspring.kitchen.client.api.render.oven;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created on 02-05-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public interface IClientOven
{
    void translateToSlot(int slot);

    int getSlotCount();
}
