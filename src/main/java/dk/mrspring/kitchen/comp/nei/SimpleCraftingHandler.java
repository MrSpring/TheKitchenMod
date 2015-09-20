package dk.mrspring.kitchen.comp.nei;

import dk.mrspring.kitchen.recipe.IRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * Created on 20-09-2015 for TheKitchenMod.
 */
public class SimpleCraftingHandler extends NEIKitchenCraftingHandler
{
    String name, translatedName;
    ItemStack display;
    List<IRecipe> recipes;
    boolean drawMouse = true;

    public SimpleCraftingHandler(String name, String unlocalizedName, List<IRecipe> recipes, ItemStack displayStack, boolean drawMouse)
    {
        this.name = name;
        this.translatedName = StatCollector.translateToLocal(unlocalizedName);
        this.display = displayStack;
        this.recipes = recipes;
        this.drawMouse = drawMouse;
    }

    public SimpleCraftingHandler(String name, String unlocalizedName, List<IRecipe> recipes, ItemStack displayStack)
    {
        this(name, unlocalizedName, recipes, displayStack, true);
    }

    @Override
    protected boolean drawMouse()
    {
        return drawMouse;
    }

    @Override
    protected ItemStack getBlockDisplayStack()
    {
        return display;
    }

    @Override
    protected String getName()
    {
        return translatedName;
    }

    @Override
    protected String getID()
    {
        return name;
    }

    @Override
    protected List<IRecipe> getRecipes()
    {
        return recipes;
    }
}
