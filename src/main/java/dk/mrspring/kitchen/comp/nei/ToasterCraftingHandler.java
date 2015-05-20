package dk.mrspring.kitchen.comp.nei;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.recipe.*;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Konrad on 12-04-2015.
 */
public class ToasterCraftingHandler extends NEIKitchenCraftingHandler
{
    @Override
    protected ItemStack getBlockDisplayStack()
    {
        return new ItemStack(KitchenBlocks.toaster);
    }

    @Override
    protected String getName()
    {
        return "tile.toaster.name";
    }

    @Override
    protected String getID()
    {
        return "kitchen.toaster";
    }

    @Override
    protected void loadAllRecipes()
    {
        List<IRecipe> recipes = ToasterRecipes.instance().getRecipes();
        for (IRecipe recipe : recipes)
            if (recipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipeHelper = (INEIRecipeHelper) recipe;
                arecipes.add(new RecipePair(recipeHelper.getExpectedInput(), recipeHelper.getExpectedOutput()));
            }
    }

    @Override
    protected void loadRecipeFor(ItemStack result)
    {
        List<IRecipe> recipes = ToasterRecipes.instance().getRecipes();
        for (IRecipe recipe : recipes)
        {
            INEIRecipeHelper recipeHelper = (INEIRecipeHelper) recipe;
            arecipes.add(new RecipePair(recipeHelper.getExpectedInput(result), result));
        }
    }

    @Override
    protected void loadRecipesFrom(ItemStack input)
    {
        ItemStack output = ToasterRecipes.instance().getOutputFor(input);
        if (output != null)
            arecipes.add(new RecipePair(input, output));
    }

    @Override
    protected boolean drawMouse()
    {
        return false;
    }
}
