package dk.mrspring.kitchen.comp.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.ingredient.Ingredient;
import dk.mrspring.kitchen.api.ingredient.IngredientRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.awt.*;
import java.util.Map;

/**
 * Created by Konrad on 14-03-2015.
 */
public class FryingPanCraftingHandler extends FurnaceRecipeHandler
{
    public class PanPair extends SmeltingPair
    {
        public PanPair(ItemStack ingred, ItemStack result)
        {
            super(ingred, result);
        }

        @Override
        public PositionedStack getOtherStack()
        {
            return null;
        }
    }

    @Override
    public String getRecipeName()
    {
        return StatCollector.translateToLocal("tile.frying_pan.name");
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals("frying_pan"))
        {
            Map<String, Ingredient> ingredientMap = IngredientRegistry.getInstance().getIngredients();
            for (Map.Entry<String, Ingredient> entry : ingredientMap.entrySet())
            {
                Ingredient ingredient = entry.getValue();
                if (ingredient.isJam())
                    this.loadCraftingRecipes(Kitchen.getJamJarItemStack(ingredient.getJamResult(), 3));
                else
                    this.loadCraftingRecipes(ingredient.getItemResult());
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        Ingredient[] inputs = IngredientRegistry.getInstance().getInputsForItemStack(result);
        for (Ingredient ingredient : inputs)
        {
            IngredientRegistry.Stack[] inputStacks = IngredientRegistry.getInstance().getInputs(ingredient);
            for (IngredientRegistry.Stack stack : inputStacks)
                arecipes.add(new PanPair(stack.toItemStack(), result));
        }
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        if (inputId.equals("frying_pan"))
            loadCraftingRecipes(inputId);
        else super.loadUsageRecipes(inputId, ingredients);
    }

    @Override
    public void loadUsageRecipes(ItemStack used)
    {
        Ingredient ingredient = IngredientRegistry.getInstance().getOutput(used);
        if (ingredient != null && ingredient.canAdd(used))
        {
            ItemStack result;
            if (ingredient.isJam())
                result = Kitchen.getJamJarItemStack(ingredient.getJamResult(), 3);
            else result = ingredient.getItemResult();
            arecipes.add(new PanPair(used, result));
        }
    }

    @Override
    public String getGuiTexture()
    {
        return ModInfo.toTexture("textures/gui/nei/pan.png");
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "frying_pan"));
    }

    @Override
    public void drawExtras(int recipe)
    {
        drawProgressBar(74, 23, 176, 14, 24, 16, 48, 0);
    }
}
