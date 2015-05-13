package dk.mrspring.kitchen.comp.nei;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.ingredient.Ingredient;
import dk.mrspring.kitchen.api.ingredient.IngredientRegistry;
import dk.mrspring.kitchen.api.stack.Stack;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * Created by Konrad on 14-03-2015.
 */
public class FryingPanCraftingHandler extends NEIKitchenCraftingHandler
{
    @Override
    protected ItemStack getBlockDisplayStack()
    {
        return new ItemStack(KitchenBlocks.frying_pan);
    }

    @Override
    protected String getName()
    {
        return "tile.frying_pan.name";
    }

    @Override
    protected String getID()
    {
        return "kitchen.frying_pan";
    }

    @Override
    protected void loadAllRecipes()
    {
        Map<String, Ingredient> ingredientMap = IngredientRegistry.getInstance().getIngredients();
        for (Map.Entry<String, Ingredient> entry : ingredientMap.entrySet())
        {
            Ingredient ingredient = entry.getValue();
            if (ingredient.isJam())
                this.loadRecipeFor(Kitchen.getJamJarItemStack(ingredient.getJamResult(), 6));
            else
                this.loadRecipeFor(ingredient.getItemResult());
        }
    }

    @Override
    protected void loadRecipeFor(ItemStack result)
    {
        Ingredient[] inputs = IngredientRegistry.getInstance().getInputsForItemStack(result);
        for (Ingredient ingredient : inputs)
        {
            Stack[] inputStacks = IngredientRegistry.getInstance().getInputs(ingredient);
            for (Stack stack : inputStacks)
                arecipes.add(new RecipePair(stack.toItemStack(), result));
        }
    }

    @Override
    protected void loadRecipesFrom(ItemStack input)
    {
        Ingredient ingredient = IngredientRegistry.getInstance().getOutput(input);
        if (ingredient != null && ingredient.canAdd(input))
        {
            ItemStack result;
            if (ingredient.isJam())
                result = Kitchen.getJamJarItemStack(ingredient.getJamResult(), 3);
            else result = ingredient.getItemResult();
            arecipes.add(new RecipePair(input, result));
        }
    }
}
