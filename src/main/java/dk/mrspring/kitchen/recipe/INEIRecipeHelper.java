package dk.mrspring.kitchen.recipe;

import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 20-05-2015.
 */
public interface INEIRecipeHelper
{
    ItemStack getExpectedInput(ItemStack output);

    ItemStack getExpectedInput();

    ItemStack getExpectedOutput(ItemStack input);

    ItemStack getExpectedOutput();

    boolean doesExpectedInputMatch(ItemStack otherInput);

    boolean doesExpectedOutputMatch(ItemStack otherOutput);
}
