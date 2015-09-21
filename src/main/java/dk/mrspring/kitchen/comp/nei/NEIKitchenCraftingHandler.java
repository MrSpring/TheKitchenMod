package dk.mrspring.kitchen.comp.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.recipe.INEIRecipeHelper;
import dk.mrspring.kitchen.recipe.IRecipe;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.awt.*;
import java.util.List;

/**
 * Created by Konrad on 12-04-2015.
 */
public abstract class NEIKitchenCraftingHandler extends FurnaceRecipeHandler
{
    public int stackX = 51, stackY = 42;
    public int inputX = 51, inputY = 6;
    public int outputX = 111, outputY = 24;
    public boolean drawOtherStack = true;

    protected abstract ItemStack getBlockDisplayStack();

    protected abstract String getName();

    protected abstract String getID();

    protected abstract List<IRecipe> getRecipes();

    protected void loadAllRecipes()
    {
        List<IRecipe> recipes = getRecipes();
        for (IRecipe iRecipe : recipes)
            if (iRecipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipe = (INEIRecipeHelper) iRecipe;
                ItemStack input = recipe.getExpectedInput();
                ItemStack output = recipe.getExpectedOutput(input);
                arecipes.add(new RecipePair(input, output));
            }
    }

    protected void loadRecipeFor(ItemStack output)
    {
        System.out.println("Loading recipe for: "+ItemUtils.name(output));
        List<IRecipe> recipes = getRecipes();
        for (IRecipe iRecipe : recipes)
            if (iRecipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipe = (INEIRecipeHelper) iRecipe;
                if (!recipe.doesExpectedOutputMatch(output))
                    continue;
                ItemStack input = recipe.getExpectedInput(output);
                arecipes.add(new RecipePair(input, recipe.getExpectedOutput(input)));
            }
    }

    protected void loadRecipesFrom(ItemStack input)
    {
        List<IRecipe> recipes = getRecipes();
        for (IRecipe iRecipe : recipes)
        {
            if (iRecipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipe = (INEIRecipeHelper) iRecipe;
                if (!recipe.doesExpectedInputMatch(input))
                    continue;
                ItemStack output = recipe.getExpectedOutput(input);
                arecipes.add(new RecipePair(recipe.getExpectedInput(output), output));
            }
        }
    }

    protected boolean drawMouse()
    {
        return true;
    }

    protected boolean translateName()
    {
        return true;
    }

    @Override
    public String getGuiTexture()
    {
        return ModInfo.toTexture("textures/gui/nei/base.png");
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(getID()))
            this.loadAllRecipes();
        else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        if (inputId.equals(getID()))
            this.loadCraftingRecipes(inputId, ingredients);
        else super.loadUsageRecipes(inputId, ingredients);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        this.loadRecipeFor(result);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        this.loadRecipesFrom(ingredient);
    }

    @Override
    public void drawExtras(int recipe)
    {
        drawProgressBar(74, 23, 176, 14, 24, 16, 48, 0);
        if (drawMouse())
        {
            int drawX = 176;
            int useKey = Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode();
            if (useKey == -100)
                drawX += 16;
            else if (useKey == -99)
                drawX += 32;
            else if (useKey == -98)
                drawX += 48;
            else
            {
                FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
                String rendering = GameSettings.getKeyDisplayString(useKey);
                int renderingWidth = renderer.getStringWidth(rendering);
                int x = 51 + 14;
                drawKeyboardKey(x, renderingWidth);
                renderer.drawString(rendering, Math.min(x - renderingWidth, x - 8), 42 - 14, 0xFFFFFF, true);
                return;
            }
            drawTexturedModalRect(51, 42 - 18, drawX, 31, 16, 16);
        }
    }

    private void drawKeyboardKey(int startX, int w)
    {
        int width = Math.max(16, w + 6);
        int x = startX - width + 3, y = 42 - 17;
        drawTexturedModalRect(x, y, 176, 47, 3, 20);
        int count = (width - 6) / 10;
        int remaining = (width - 6) % 10;
        if (count == 0 || count == 1)
            drawTexturedModalRect(x + 3, y, 176 + 3, 47, 10, 20);
        else
        {
            for (int i = 0; i < count; i++)
                drawTexturedModalRect(x + 3 + (i * 10), y, 176 + 3, 47, 10, 20);
            drawTexturedModalRect(x + width - 3 - remaining, y, 176 + 3, 47, remaining, 20);
        }
        drawTexturedModalRect(x + width - 3, y, 176 + 13, 47, 3, 20);
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), getID()));
    }

    @Override
    public String getRecipeName()
    {
        if (translateName())
            return StatCollector.translateToLocal(getName());
        return getName();
    }

    protected void drawTexturedModalRect(int x, int y, int u, int v, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x), (double) (y + height), 1, (double) ((float) (u) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + height), 1, (double) ((float) (u + width) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y), 1, (double) ((float) (u + width) * f), (double) ((float) (v) * f1));
        tessellator.addVertexWithUV((double) (x), (double) (y), 1, (double) ((float) (u) * f), (double) ((float) (v) * f1));
        tessellator.draw();
    }

    public class RecipePair extends CachedRecipe
    {
        public PositionedStack ingred;
        public PositionedStack result;

        public RecipePair(ItemStack input, ItemStack output)
        {
            super();

            this.ingred = new PositionedStack(input, inputX, inputY);
            this.result = new PositionedStack(output, outputX, outputY);
        }

        @Override
        public PositionedStack getResult()
        {
            return result;
        }

        @Override
        public PositionedStack getIngredient()
        {
            return ingred;
        }

        @Override
        public PositionedStack getOtherStack()
        {
            return drawOtherStack ? new PositionedStack(getBlockDisplayStack(), stackX, stackY, false) : null;
        }
    }
}
