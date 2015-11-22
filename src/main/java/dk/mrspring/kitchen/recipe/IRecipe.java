package dk.mrspring.kitchen.recipe;

import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 20-05-2015.
 */
public interface IRecipe
{
    boolean doesInputMatch(ItemStack stack);

    ItemStack getOutput(ItemStack input);

    void reduceInputSize(ItemStack input);

    boolean isValid();
}
