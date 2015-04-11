package dk.mrspring.kitchen.comp.nei;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.recipe.BasicRecipe;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import dk.mrspring.kitchen.recipe.ToasterRecipes;
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
        List<BasicRecipe> recipes = ToasterRecipes.instance().getRecipes();
        for (BasicRecipe recipe : recipes)
            arecipes.add(new RecipePair(recipe.getInput().copy(), recipe.getOutput().copy()));
    }

    @Override
    protected void loadRecipeFor(ItemStack result)
    {
        List<BasicRecipe> recipes = ToasterRecipes.instance().getRecipes();
        for (BasicRecipe recipe : recipes)
            if (recipe.getOutput().isItemEqual(result) && recipe.getOutput().getItemDamage() == result.getItemDamage())
                arecipes.add(new RecipePair(recipe.getInput().copy(), recipe.getOutput().copy()));
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
