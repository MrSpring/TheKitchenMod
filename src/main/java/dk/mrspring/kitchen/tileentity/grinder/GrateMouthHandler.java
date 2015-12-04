package dk.mrspring.kitchen.tileentity.grinder;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.tileentity.TileEntityGrinder;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.item.ItemStack;

/**
 * Created on 04-12-2015 for TheKitchenMod.
 */
public class GrateMouthHandler implements TileEntityGrinder.IMouthHandler
{
    @Override
    public boolean isAcceptableMouth(ItemStack stack)
    {
        return ItemUtils.item(stack, KitchenItems.grate_mouth);
    }
}
