package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.Kitchen;
import net.minecraft.item.ItemStack;

import static dk.mrspring.kitchen.KitchenItems.*;
import static net.minecraft.init.Items.*;

/**
 * Created by Konrad on 19-06-2015.
 */
public class FryingPanJamRecipes extends BasicRecipeHandler
{
    public static FryingPanJamRecipes instance()
    {
        return Kitchen.instance.fryingPanJamRecipes;
    }

    @Override
    public void load()
    {
        super.load();

        this.addDefaultRecipes();
    }

    private void addDefaultRecipes()
    {
        this.addAll(new IRecipe[]{
                new JamRecipe(peanut, "peanut"),
                new JamRecipe(jammable_strawberry, "strawberry"),
                new JamRecipe(cut_apple, "apple"),
                new JamRecipe(new ItemStack(dye, 3), "cocoa"),
                new JamRecipe(tomato_slice, "ketchup")
        });
    }

    public String getJamOutputFor(ItemStack input)
    {
        IRecipe forInput = this.getRecipeFor(input);
        if (forInput instanceof JamRecipe)
        {
            JamRecipe recipe = (JamRecipe) forInput;
            return recipe.getJamOutput(input);
        }
        return null;
    }
}
