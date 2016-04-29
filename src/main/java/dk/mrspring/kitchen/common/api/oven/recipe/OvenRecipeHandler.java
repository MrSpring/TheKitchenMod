package dk.mrspring.kitchen.common.api.oven.recipe;

import dk.mrspring.kitchen.common.api.recipe.RecipeHandler;
import dk.mrspring.kitchen.common.api.recipe.SimpleRecipe;

import static dk.mrspring.kitchen.Kitchen.items;
import static dk.mrspring.kitchen.common.util.ItemUtils.newStack;

/**
 * Created on 26-04-2016 for TheKitchenMod.
 */
public class OvenRecipeHandler extends RecipeHandler
{
    public void register()
    {
        addRecipe(new SimpleRecipe(newStack(items.raw_burger_bun),

                newStack(items.cooked_burger_bun),
                newStack(items.burnt_bread))
        );
    }
}
