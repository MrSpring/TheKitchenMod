package dk.mrspring.kitchen.comp.nei;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.recipe.FryingPanJamRecipes;
import dk.mrspring.kitchen.recipe.FryingPanRecipes;
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
    protected List<IRecipe> getRecipes()
    {
        List<IRecipe> recipes = new ArrayList<IRecipe>(FryingPanRecipes.instance().getRecipes());
        recipes.addAll(FryingPanJamRecipes.instance().getRecipes());
        return recipes;
    }
}
