package dk.mrspring.kitchen.comp.nei;

import codechicken.nei.recipe.ShapedRecipeHandler;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.item.ItemMuffinCup;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.awt.*;

/**
 * Created on 27-08-2015.
 */
public class MuffinCupCraftingHandler extends ShapedRecipeHandler
{
    public void loadTransferRects()
    {
        this.transferRects.add(new RecipeTransferRect(new Rectangle(84, 23, 24, 18), "muffin_cup"));
    }

    @Override
    public String getRecipeName()
    {
        return StatCollector.translateToLocal("item.muffin_cup.name");
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        System.out.println("stuffs");
        if (outputId.equals("muffin_cup"))
            for (int i = 0; i < ItemMuffinCup.colorNames.length; i++)
            {
                arecipes.add(new CachedShapedRecipe(2, 2, new ItemStack[]{
                        new ItemStack(Items.paper),
                        new ItemStack(Items.paper),
                        new ItemStack(Items.dye, 1, i),
                        null
                }, new ItemStack(KitchenItems.empty_muffin_cup, 6, i)));

                arecipes.add(forgeShapedRecipe(new ShapedOreRecipe(/*new ItemStack[]{
                        new ItemStack(KitchenItems.empty_muffin_cup, 1,3),
                        new ItemStack(Items.dye, 1, i),
                }, new ItemStack(KitchenItems.empty_muffin_cup, 6, i)*/
                    new ItemStack(KitchenItems.empty_muffin_cup, 6, i), "CD", 'C', new ItemStack(KitchenItems.empty_muffin_cup, 1, OreDictionary.WILDCARD_VALUE), 'D', "dye"/*new ItemStack(Items.dye, 1, i)*/)))
                ;

            }
        else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        System.out.println("stuff");
        arecipes.add(new CachedShapedRecipe(2, 2, new ItemStack[]{
                new ItemStack(Items.paper),
                new ItemStack(Items.paper),
                new ItemStack(Items.dye, 1, result.getItemDamage()),
                null
        }, result));
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        super.loadUsageRecipes(inputId, ingredients);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        super.loadUsageRecipes(ingredient);
    }
}
