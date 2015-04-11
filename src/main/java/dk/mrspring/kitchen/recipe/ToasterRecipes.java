package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModConfig;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class ToasterRecipes extends BasicRecipeHandler
{
    public static ToasterRecipes instance()
    {
        return Kitchen.instance.toasterRecipes;
    }

    @Override
    public void load()
    {
        super.load();

        this.addDefaultRecipes();
        this.addAll(ModConfig.getToasterConfig().custom_toaster_recipes);
    }

    private void addDefaultRecipes()
    {
        BasicRecipe[] defaultRecipes = new BasicRecipe[]{
                new BasicRecipe(new ItemStack(KitchenItems.toast), new ItemStack(KitchenItems.toasted_toast)),
                new BasicRecipe(new ItemStack(KitchenItems.raw_bacon), new ItemStack(KitchenItems.bacon))
        };
        addAll(defaultRecipes);
    }
}
