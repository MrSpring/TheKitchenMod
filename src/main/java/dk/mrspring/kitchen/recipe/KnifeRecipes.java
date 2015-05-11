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
                new BasicRecipe(lettuce, new ItemStack(lettuce_leaf, 2)),
                new BasicRecipe(carrot, new ItemStack(carrot_slice, 2)),
                new BasicRecipe(potato, new ItemStack(potato_slice, 3)),
                new BasicRecipe(bread, new ItemStack(bread_slice, 2)),
                new BasicRecipe(beef, new ItemStack(raw_roast_beef, 2)),
                new BasicRecipe(chicken, new ItemStack(raw_chicken_fillet, 3)),
                new BasicRecipe(cooked_chicken, new ItemStack(chicken_leg, 2)),
                new BasicRecipe(cheese, new ItemStack(cheese_slice, 2)),
                new BasicRecipe(strawberry, new ItemStack(jammable_strawberry,2)),
                new BasicRecipe(apple, new ItemStack(cut_apple, 2)),
                new BasicRecipe(burger_bun, new ItemStack(sliced_burger_bun, 2)),
                new BasicRecipe(fish, new ItemStack(raw_cut_fish, 2)),
                new BasicRecipe(cooked_fished, new ItemStack(cooked_cut_fish, 2)),
                new BasicRecipe(cooked_ham, new ItemStack(ham_slice, 4))
        };
        addAll(defaultRecipes);
    }
}
