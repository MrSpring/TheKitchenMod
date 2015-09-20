package dk.mrspring.kitchen.comp.nei;

import codechicken.nei.recipe.ShapedRecipeHandler;
import dk.mrspring.kitchen.KitchenItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.awt.*;

import static dk.mrspring.kitchen.item.ItemMuffinCup.colorNames;

/**
 * Created on 27-08-2015.
 */
public class MuffinCupCraftingHandler extends ShapedRecipeHandler
{
    private static final ItemStack[] DYES = new ItemStack[16];
    private static final ItemStack[] CUPS = new ItemStack[16];

    static
    {
        for (int i = 0; i < DYES.length; i++)
        {
            DYES[i] = new ItemStack(Items.dye, 1, i);
            CUPS[i] = new ItemStack(KitchenItems.empty_muffin_cup, 1, i);
        }
    }

    public void loadTransferRects()
    {
        this.transferRects.add(new RecipeTransferRect(new Rectangle(84, 23, 24, 18), "kitchen.muffin_cup"));
    }

    @Override
    public String getRecipeName()
    {
        return StatCollector.translateToLocal("item.muffin_cup.name");
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals("muffin_cup"))
            for (int i = 0; i < colorNames.length; i++)
            {
                arecipes.add(new CachedShapedRecipe(2, 2, new ItemStack[]{
                        new ItemStack(Items.paper),
                        new ItemStack(Items.paper),
                        new ItemStack(Items.dye, 1, i), // TODO: Use OreDictionary
                        null
                }, new ItemStack(KitchenItems.empty_muffin_cup, 6, i)));

                /*arecipes.add(forgeShapedRecipe(new ShapedOreRecipe(*//*new ItemStack[]{
                        new ItemStack(KitchenItems.empty_muffin_cup, 1,3),
                        new ItemStack(Items.dye, 1, i),
                }, new ItemStack(KitchenItems.empty_muffin_cup, 6, i)*//*
                        new ItemStack(KitchenItems.empty_muffin_cup, 6, i), "CD", 'C', new ItemStack(KitchenItems.empty_muffin_cup, 1, OreDictionary.WILDCARD_VALUE), 'D', "dye"*//*new ItemStack(Items.dye, 1, i)*//*)))
                ;*/

                arecipes.add(new CachedShapedRecipe(1, 2, new Object[]{
                        CUPS,
                        new ItemStack(Items.dye, 1, i) // TODO: Use OreDictionary
                }, new ItemStack(KitchenItems.empty_muffin_cup, 1, i)));

            }
        else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        if (result.getItem() == KitchenItems.empty_muffin_cup)
        {
            arecipes.add(new CachedShapedRecipe(2, 2, new Object[]{
                    new ItemStack(Items.paper),
                    new ItemStack(Items.paper),
                    new ItemStack(Items.dye, 1, result.getItemDamage()), // TODO: Use OreDictionary
                    null
            }, result));

            arecipes.add(new CachedShapedRecipe(1, 2, new Object[]{
                    CUPS,
                    new ItemStack(Items.dye, 1, result.getItemDamage()) // TODO: Use OreDictionary
            }, result));
        }
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
