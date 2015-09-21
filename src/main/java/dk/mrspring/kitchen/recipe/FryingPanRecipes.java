package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.item.ItemMixingBowl;

import static dk.mrspring.kitchen.KitchenItems.*;

import static net.minecraft.init.Items.*;

/**
 * Created by Konrad on 02-06-2015.
 */
public class FryingPanRecipes extends BasicRecipeHandler
{
    public static FryingPanRecipes instance()
    {
        return Kitchen.instance.fryingPanRecipes;
    }

    @Override
    public void load()
    {
        super.load();

        this.addDefaultRecipes();
    }

    private void addDefaultRecipes()
    {
        addAll(new IRecipe[]{
                new BasicRecipe(raw_bacon, bacon),
                new BasicRecipe(raw_chicken_fillet, chicken_fillet),
                new BasicRecipe(raw_cut_fish, cooked_cut_fish),
                new BasicRecipe(fish, cooked_fished),
                new BasicRecipe(raw_meat_patty, cooked_meat_patty),
                new BasicRecipe(egg, fried_egg),
                new BasicRecipe(beef, cooked_beef),
                new BasicRecipe(raw_roast_beef, roast_beef),
                new BasicRecipe(porkchop, cooked_porkchop), // TODO: Add the rest of the recipes
                new MixingBowlRecipe("pancake_dough", KitchenItems.pancake)
        });
    }
}
