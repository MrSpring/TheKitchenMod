package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.config.wrapper.JsonBasicRecipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class BasicRecipeHandler
{
    List<IRecipe> recipes = new ArrayList<IRecipe>();

    public void load()
    {

    }

    protected void addAll(JsonBasicRecipe[] array)
    {
        if (array != null)
        {
            BasicRecipe[] recipes = new BasicRecipe[array.length];
            for (int i = 0; i < array.length; i++)
            {
                JsonBasicRecipe recipe = array[i];
                recipes[i] = recipe.toBasicRecipe();
            }
            addAll(recipes);
        }
    }

    protected void addAll(IRecipe[] array)
    {
        for (IRecipe recipe : array)
            if (recipe.isValid())
                this.recipes.add(recipe);
    }

    public List<IRecipe> getRecipes()
    {
        return recipes;
    }

    /**
     * @param input The ItemStack to get result of.
     * @return Returns the result of the item stack when cooked in the Oven. Returns null when nothing was found
     */
    public ItemStack getOutputFor(ItemStack input)
    {
        for (IRecipe recipe : recipes)
            if (recipe.doesInputMatch(input))
                return recipe.getOutput(input);

        return null;
    }

    public void addRecipe(ItemStack input, ItemStack output)
    {
        if (input != null && output != null)
            recipes.add(new BasicRecipe(input, output));
    }

    public boolean hasOutput(ItemStack toAdd)
    {
        return getOutputFor(toAdd) != null;
    }
}
