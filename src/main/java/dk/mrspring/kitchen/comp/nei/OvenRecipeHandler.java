package dk.mrspring.kitchen.comp.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.Map;

/**
 * Created by MrSpring on 25-11-2014 for TheKitchenMod.
 */
public class OvenRecipeHandler extends TemplateRecipeHandler
{
    public class OvenPair extends CachedRecipe
    {
        public PositionedStack input, output;

        public OvenPair(ItemStack input, ItemStack output)
        {
            this.input = new PositionedStack(input, 51, 24);
            this.output = new PositionedStack(output, 111, 24);
        }

        @Override
        public PositionedStack getIngredient()
        {
            return input;
        }

        @Override
        public PositionedStack getResult()
        {
            return output;
        }
    }

    @Override
    public String getGuiTexture()
    {
        return "textures/gui/oven.png";
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "cooking"));
    }

    /*@Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(50,23,18,18),"fuel"));
    }*/

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiFurnace.class;
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        Map<ItemStack, ItemStack> recipes = OvenRecipes.getCustomOvenRecipes();

        for (Map.Entry<ItemStack, ItemStack> entry : recipes.entrySet())
            if (NEIServerUtils.areStacksSameType(entry.getValue(), result))
                arecipes.add(new OvenPair(entry.getKey(), entry.getValue()));
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        Map<ItemStack, ItemStack> recipes = OvenRecipes.getCustomOvenRecipes();

        for (Map.Entry<ItemStack, ItemStack> entry : recipes.entrySet())
            if (NEIServerUtils.areStacksSameType(entry.getKey(), ingredient))
                arecipes.add(new OvenPair(entry.getKey(), entry.getValue()));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals("cooking"))
        {
            Map<ItemStack, ItemStack> recipes = OvenRecipes.getCustomOvenRecipes();
            for (Map.Entry<ItemStack, ItemStack> entry : recipes.entrySet())
                arecipes.add(new OvenPair(entry.getKey(), entry.getValue()));
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        if (inputId.equals("cooking"))
            loadCraftingRecipes("cooking");
        else super.loadUsageRecipes(inputId, ingredients);
    }

    @Override
    public void drawExtras(int recipe)
    {
//        drawProgressBar(51, 25, 176, 0, 14, 14, 48, 7);
        drawProgressBar(74, 23, 176, 14, 24, 16, 48, 0);
    }

    @Override
    public String getRecipeName()
    {
        return "Oven";
    }

    @Override
    public String getOverlayIdentifier()
    {
        return "cooking";
    }

    @Override
    public TemplateRecipeHandler newInstance()
    {
        return super.newInstance();
    }
}
