package dk.mrspring.kitchen.api_impl.common.oven;

import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 11-07-2015.
 */
public class RecipeOvenItem implements IOvenItem
{
    public static final String RECIPE_INPUT = "RecipeInput";
    public static final String RECIPE_OUTPUT = "RecipeOutput";

    @Override
    public String getName()
    {
        return "basic_recipe";
    }

    @Override
    public String getDisplayName()
    {
        return "Recipe";
    }

    @Override
    public boolean isForItem(IOven oven, ItemStack item, EntityPlayer player, boolean[] freeSlots)
    {
        return OvenRecipes.instance().hasOutput(item);
    }

    @Override
    public boolean canAdd(IOven oven, ItemStack adding, EntityPlayer player, boolean[] freeSlots)
    {
        return true;
    }

    @Override
    public void onAdded(IOven oven, ItemStack added, EntityPlayer player, int[] slots)
    {

    }

    @Override
    public boolean readyToCook(IOven oven, int slot)
    {
        return false;
    }

    @Override
    public int getSize(IOven oven)
    {
        return 1;
    }

    @Override
    public boolean consecutive(IOven oven)
    {
        return false;
    }

    @Override
    public int getCookTime(IOven oven)
    {
        return 200;
    }

    @Override
    public boolean onRightClicked(IOven oven, ItemStack clicked, EntityPlayer player)
    {
        return false;
    }

    @Override
    public boolean canBeRemoved(IOven oven, ItemStack clicked, EntityPlayer player)
    {
        return false;
    }

    @Override
    public ItemStack onRemoved(IOven oven, ItemStack clicked, EntityPlayer player)
    {
        return null;
    }
}
