package dk.mrspring.kitchen.comp.nei;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.item.ItemMixingBowl;
import dk.mrspring.kitchen.recipe.BasicRecipe;
import dk.mrspring.kitchen.recipe.IRecipe;
import dk.mrspring.kitchen.tileentity.TileEntityWaffleIron;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 20-09-2015 for TheKitchenMod.
 */
public class WaffleIronCraftingHandler extends NEIKitchenCraftingHandler
{
    @Override
    protected ItemStack getBlockDisplayStack()
    {
        return new ItemStack(KitchenBlocks.waffle_iron);
    }

    @Override
    protected String getName()
    {
        return StatCollector.translateToLocal("tile.waffle_iron.name");
    }

    @Override
    protected String getID()
    {
        return "waffleiron";
    }

    @Override
    protected List<IRecipe> getRecipes()
    {
        List<IRecipe> recipes = new ArrayList<IRecipe>();
        for (Map.Entry<String, ItemStack[]> entry : TileEntityWaffleIron.recipes.entrySet())
            recipes.add(new BasicRecipe(ItemMixingBowl.getMixingBowlStack(entry.getKey(), 3), entry.getValue()[0]));
        return recipes;
    }

    @Override
    protected boolean drawMouse()
    {
        return false;
    }
}
