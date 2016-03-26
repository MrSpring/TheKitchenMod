package dk.mrspring.kitchen.common.recipe;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * Created on 09-03-2016 for TheKitchenMod.
 */
public class RecipeHandler
{
    List<IRecipe> recipes = Lists.newLinkedList();

    public RecipeHandler(IRecipe... recipes)
    {
        Collections.addAll(this.recipes, recipes);
    }

    public void addRecipe(IRecipe recipe)
    {
        this.recipes.add(0, recipe);
    }

    public IRecipe getRecipe(ItemStack input)
    {
        for (IRecipe recipe : recipes) if (recipe.acceptsInput(input)) return recipe;
        return null;
    }

    public ItemStack[] getOutput(ItemStack input)
    {
        IRecipe recipe = getRecipe(input);
        if (recipe != null) return recipe.getOutput(input);
        else return new ItemStack[0];
    }

    public boolean hasOutput(ItemStack input)
    {
        return getOutput(input).length > 0;
    }
}
