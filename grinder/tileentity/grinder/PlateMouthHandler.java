package dk.mrspring.kitchen.tileentity.grinder;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.tileentity.TileEntityGrinder;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 22-11-2015.
 */
public class PlateMouthHandler implements TileEntityGrinder.IMouthHandler
{
    @Override
    public boolean isAcceptableMouth(ItemStack stack)
    {
        return ItemUtils.item(stack, KitchenItems.plate_mouth);
    }
}
