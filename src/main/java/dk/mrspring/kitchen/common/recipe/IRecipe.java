package dk.mrspring.kitchen.common.recipe;

import net.minecraft.item.ItemStack;

/**
 * Created on 09-03-2016 for TheKitchenMod.
 */
public interface IRecipe
{
    boolean acceptsInput(ItemStack stack);

    ItemStack[] getOutput(ItemStack input);

    ItemStack getExpectedInput();

    ItemStack getExpectedInput(ItemStack[] output);

    ItemStack getExpectedOutput();

    ItemStack getExpectedOutput(ItemStack input);
}
