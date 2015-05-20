package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.api.stack.Stack;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 20-05-2015.
 */
public interface IRecipe
{
    boolean doesInputMatch(Stack stack);

    boolean doesInputMatch(ItemStack stack);

    ItemStack getOutput(ItemStack stack);

    Stack getOutput(Stack stack);

    boolean isValid();
}
