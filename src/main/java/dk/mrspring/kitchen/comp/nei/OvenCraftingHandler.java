package dk.mrspring.kitchen.comp.nei;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.recipe.BasicRecipe;
import dk.mrspring.kitchen.recipe.INEIRecipeHelper;
import dk.mrspring.kitchen.recipe.IRecipe;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Konrad on 12-04-2015.
 */
public class OvenCraftingHandler extends NEIKitchenCraftingHandler
{
    @Override
    protected ItemStack getBlockDisplayStack()
    {
        return new ItemStack(KitchenBlocks.oven);
    }

    @Override
    protected String getName()
    {
        return "tile.oven.name";
    }

    @Override
    protected String getID()
    {
        return "kitchen.oven";
    }

    @Override
    protected void loadAllRecipes()
    {
        List<IRecipe> recipes = OvenRecipes.instance().getRecipes();
        for (IRecipe iRecipe : recipes)
            if (iRecipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipe = (INEIRecipeHelper) iRecipe;
                ItemStack input = recipe.getExpectedInput();
                ItemStack output = recipe.getExpectedOutput(input);
                arecipes.add(new RecipePair(input, output));
            }
    }

    @Override
    protected void loadRecipeFor(ItemStack output)
    {
        List<IRecipe> recipes = OvenRecipes.instance().getRecipes();
        for (IRecipe iRecipe : recipes)
            if (iRecipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipe = (INEIRecipeHelper) iRecipe;
                if (!recipe.doesExpectedOutputMatch(output))
                    continue;
                ItemStack input = recipe.getExpectedInput(output);
                arecipes.add(new RecipePair(input, output));
            }
    }

    @Override
    protected void loadRecipesFrom(ItemStack input)
    {
        List<IRecipe> recipes = OvenRecipes.instance().getRecipes();
        for (IRecipe iRecipe : recipes)
        {
            if (iRecipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipe = (INEIRecipeHelper) iRecipe;
                if (!recipe.doesExpectedInputMatch(input))
                    continue;
                ItemStack output = recipe.getExpectedOutput(input);
                arecipes.add(new RecipePair(input, output));
            }
        }
    }

    @Override
    protected boolean drawMouse()
    {
        return false;
    }
}
