package dk.mrspring.kitchen.util;

import dk.mrspring.kitchen.ModConfig;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Konrad on 10-05-2015.
 */
public class SandwichUtils
{
    public static ItemStack makeSandwich(ItemStack[] layers)
    {
        return null; // TODO: Make Sandwich from layers
    }

    public static boolean isAllSandwichable(ItemStack[] layers)
    {
        for (ItemStack stack : layers)
            if (!isSandwichable(stack))
                return false;
        return true;
    }

    public static boolean isAllSandwichable(List<ItemStack> layers)
    {
        return isAllSandwichable(layers.toArray(new ItemStack[layers.size()]));
    }

    public static boolean isSandwichable(ItemStack stack)
    {
        return ModConfig.getSandwichConfig().canAdd(stack);
    }
}
