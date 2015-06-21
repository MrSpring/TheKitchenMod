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
    protected List<IRecipe> getRecipes()
    {
        return OvenRecipes.instance().getRecipes();
    }

    @Override
    protected boolean drawMouse()
    {
        return false;
    }
}
