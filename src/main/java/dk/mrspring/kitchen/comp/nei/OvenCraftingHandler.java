package dk.mrspring.kitchen.comp.nei;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.recipe.BasicRecipe;
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
        List<BasicRecipe> recipes = OvenRecipes.instance().getRecipes();
        for (BasicRecipe recipe : recipes)
            arecipes.add(new RecipePair(recipe.getInput().copy(), recipe.getOutput().copy()));
    }

    @Override
    protected void loadRecipeFor(ItemStack result)
    {
        List<BasicRecipe> recipes = OvenRecipes.instance().getRecipes();
        for (BasicRecipe recipe : recipes)
            if (recipe.getOutput().isItemEqual(result) && recipe.getOutput().getItemDamage() == result.getItemDamage())
                arecipes.add(new RecipePair(recipe.getInput().copy(), recipe.getOutput().copy()));
    }

    @Override
    protected void loadRecipesFrom(ItemStack input)
    {
        ItemStack output = OvenRecipes.instance().getOutputFor(input);
        if (output != null)
            arecipes.add(new RecipePair(input, output));
    }

    @Override
    protected boolean drawMouse()
    {
        return false;
    }
}
