package dk.mrspring.kitchen.common.item;

import net.minecraft.item.ItemStack;

/**
 * Created on 09-03-2016 for TheKitchenMod.
 */
public interface IComparable
{
    boolean compareStacks(ItemStack stack1, ItemStack stack2);
}
