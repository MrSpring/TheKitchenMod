package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.Kitchen;
import net.minecraft.item.ItemStack;

import static net.minecraft.init.Items.*;
import static net.minecraft.init.Blocks.*;
import static dk.mrspring.kitchen.KitchenItems.*;

/**
 * Created by Konrad on 10-05-2015.
 */
public class KnifeRecipes extends BasicRecipeHandler
{
    public static KnifeRecipes instance()
    {
        return Kitchen.instance.knifeRecipes;
    }

    @Override
    public void load()
    {
        super.load();

        this.addDefaultRecipes();
    }

    private void addDefaultRecipes()
    {
        BasicRecipe[] defaultRecipes = new BasicRecipe[]{
                new BasicRecipe(porkchop, new ItemStack(raw_bacon, 3)),
                new BasicRecipe(tomato, new ItemStack(tomato_slice, 4)),
                new BasicRecipe(lettuce, new ItemStack(lettuce_leaf, 2))
        };
        addAll(defaultRecipes);
    }
}
