package dk.mrspring.kitchen.api.waffle;

import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 11-06-2015.
 */
public interface IDough
{
    String getName(IWaffleIron waffleIron);

    ItemStack getCookResult(IWaffleIron waffleIron);

    ItemStack getBurntResult(IWaffleIron waffleIron);

    boolean isForItem(IWaffleIron waffleIron, ItemStack stack);
}
