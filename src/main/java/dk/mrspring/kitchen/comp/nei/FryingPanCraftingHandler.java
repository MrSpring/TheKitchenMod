package dk.mrspring.kitchen.comp.nei;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.api_impl.common.IngredientRegistry;
import dk.mrspring.kitchen.api.stack.Stack;
import dk.mrspring.kitchen.recipe.FryingPanJamRecipes;
import dk.mrspring.kitchen.recipe.FryingPanRecipes;
import dk.mrspring.kitchen.recipe.INEIRecipeHelper;
import dk.mrspring.kitchen.recipe.IRecipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
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
    protected void loadAllRecipes() // TODO: Fix
    {
        List<IRecipe> recipeList = new ArrayList<IRecipe>(FryingPanRecipes.instance().getRecipes());
        recipeList.addAll(FryingPanJamRecipes.instance().getRecipes());
        for (IRecipe iRecipe : recipeList)
        {
            if (iRecipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipe = (INEIRecipeHelper) iRecipe;
                ItemStack input = recipe.getExpectedInput();
                ItemStack output = recipe.getExpectedOutput(input);
                RecipePair pair = new RecipePair(input, output);
                arecipes.add(pair);
            }
        }
    }

    @Override
    protected void loadRecipeFor(ItemStack output) // TODO: Fix
    {
        List<IRecipe> recipeList = new ArrayList<IRecipe>(FryingPanRecipes.instance().getRecipes());
        recipeList.addAll(FryingPanJamRecipes.instance().getRecipes());
        for (IRecipe iRecipe : recipeList)
        {
            if (iRecipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipe = (INEIRecipeHelper) iRecipe;
                if (!recipe.doesExpectedOutputMatch(output))
                    continue;
                ItemStack input = recipe.getExpectedInput(output);
                RecipePair pair = new RecipePair(input, output);
                arecipes.add(pair);
            }
        }
        /*Ingredient[] inputs = IngredientRegistry.getInstance().getInputsForItemStack(result);
        for (Ingredient ingredient : inputs)
        {
            Stack[] inputStacks = IngredientRegistry.getInstance().getInputs(ingredient);
            for (Stack stack : inputStacks)
                arecipes.add(new RecipePair(stack.toItemStack(), result));
        }*/
    }

    @Override
    protected void loadRecipesFrom(ItemStack input) // TODO: Fix
    {
        System.out.println("Getting recipes for: " + input.toString());
        List<IRecipe> recipeList = FryingPanRecipes.instance().getRecipes();
        recipeList.addAll(FryingPanJamRecipes.instance().getRecipes());
        for (IRecipe iRecipe : recipeList)
        {
            if (iRecipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipe = (INEIRecipeHelper) iRecipe;
                if (!recipe.doesExpectedInputMatch(input))
                    continue;
                ItemStack output = recipe.getExpectedInput(input);
                RecipePair pair = new RecipePair(input, output);
                arecipes.add(pair);
            }
        }
        /*Ingredient ingredient = IngredientRegistry.getInstance().getOutput(input);
        if (ingredient != null && ingredient.canAdd(input))
        {
            ItemStack result;
            if (ingredient.isJam())
                result = Kitchen.getJamJarItemStack(ingredient.getJamResult(), 3);
            else result = ingredient.getItemResult();
            arecipes.add(new RecipePair(input, result));
        }*/
    }
}
