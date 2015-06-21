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
    protected List<IRecipe> getRecipes()
    {
        return ToasterRecipes.instance().getRecipes();
    }
}
