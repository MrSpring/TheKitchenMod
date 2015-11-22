package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.item.ItemMixingBowl;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created on 17-11-2015 for TheKitchenMod.
 */
public class GrinderRecipeHandler extends BasicRecipeHandler
{
    @Override
    public void load()
    {
        super.load();

        this.addRecipe(new GrinderRecipe(ItemMixingBowl.getMixingBowlStack("pasta", 1), KitchenItems.plate_mouth, new ItemStack(KitchenItems.fried_egg)));
    }

    @Override
    public void addRecipe(IRecipe recipe)
    {
        if (recipe != null && recipe instanceof GrinderRecipe)
            super.addRecipe(recipe);
    }

    public GrinderRecipe getRecipeFor(ItemStack input, ItemStack mouth)
    {
        for (IRecipe recipe : getRecipes())
        {
            if (recipe instanceof GrinderRecipe)
            {
                GrinderRecipe grind = (GrinderRecipe) recipe;
                if (grind.doesInputAndMouthMatch(input, mouth)) return grind;
            }
        }
        return null;
    }

    public ItemStack getOutput(ItemStack input, ItemStack mouth)
    {
        GrinderRecipe recipe = getRecipeFor(input, mouth);
        return recipe != null ? recipe.getOutput(input, mouth) : null;
    }

    public boolean hasOutput(ItemStack stack, ItemStack mouth)
    {
        return getOutput(stack, mouth) != null;
    }
}
