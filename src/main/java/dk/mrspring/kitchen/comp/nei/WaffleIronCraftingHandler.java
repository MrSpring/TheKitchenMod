package dk.mrspring.kitchen.comp.nei;

import codechicken.nei.recipe.TemplateRecipeHandler;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.recipe.IRecipe;
import dk.mrspring.kitchen.recipe.MixingBowlRecipe;
import dk.mrspring.kitchen.tileentity.TileEntityWaffleIron;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 20-09-2015 for TheKitchenMod.
 */
public class WaffleIronCraftingHandler extends SimpleCraftingHandler
{
    public WaffleIronCraftingHandler()
    {
        super("kitchen.waffle_iron", "tile.waffle_iron.name", null, new ItemStack(KitchenBlocks.waffle_iron), false);
    }

    @Override
    public TemplateRecipeHandler newInstance()
    {
        return new WaffleIronCraftingHandler();
    }

    @Override
    protected List<IRecipe> getRecipes()
    {
        List<IRecipe> recipes = new ArrayList<IRecipe>();
        for (Map.Entry<String, ItemStack[]> entry : TileEntityWaffleIron.recipes.entrySet())
            recipes.add(new MixingBowlRecipe(entry.getKey(), entry.getValue()[0]));
        return recipes;
    }
}
