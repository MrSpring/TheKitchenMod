package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.item.ItemMixingBowl;
import dk.mrspring.kitchen.item.render.ItemMixingBowlRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 10-04-2015.
 */
public class WaffleIronRecipes
{
    private static final WaffleIronRecipes instance = new WaffleIronRecipes();

    private List<IWaffleIronRecipe> recipes;

    private WaffleIronRecipes()
    {
        this.recipes = new ArrayList<IWaffleIronRecipe>();
    }

    public void register(IWaffleIronRecipe recipe)
    {
        if (recipe != null)
            this.recipes.add(recipe);
    }

    public IWaffleIronRecipe recipeFor(ItemStack stack)
    {
        for (IWaffleIronRecipe recipe : recipes)
            if (recipe.add(stack))
                return recipe;
        return null;
    }

    public static WaffleIronRecipes getInstance()
    {
        return instance;
    }

    public static void registerRecipe(IWaffleIronRecipe recipe)
    {
        getInstance().register(recipe);
    }

    public static class WaffleIronResult
    {
        public ItemStack stack;
        public boolean finished;

        public WaffleIronResult(ItemStack stack, boolean done)
        {
            this.stack = stack;
            this.finished = done;
        }
    }

    public static interface IWaffleIronRecipe
    {
        public boolean add(ItemStack input);

        public void afterAdd(ItemStack input);

        public WaffleIronResult getResult(int cookTime);

        public void randomDisplayTick(int cookTime, boolean open);

        public void handleWaila(List toolTip, int cookTime, boolean open);
    }

    public static abstract class WaffleIronRecipe implements IWaffleIronRecipe
    {
        public final WaffleIronResult cookedResult, burntResult;
        public final int rawColor, cookedColor, burntColor;

        public WaffleIronRecipe(int rawColor, int cookedColor, int burntColor, ItemStack cookedResult, ItemStack burntResult)
        {
            this.cookedResult = new WaffleIronResult(cookedResult, true);
            this.burntResult = new WaffleIronResult(burntResult, true);

            this.rawColor = rawColor;
            this.cookedColor = cookedColor;
            this.burntColor = burntColor;
        }

        @Override
        public WaffleIronResult getResult(int cookTime)
        {
            if (cookTime > 600)
                return burntResult;
            else if (cookTime > 400)
                return cookedResult;
            else return new WaffleIronResult(null, false);
        }

        @Override
        public void randomDisplayTick(int cookTime, boolean open)
        {

        }

        @Override
        public void handleWaila(List toolTip, int cookTime, boolean open)
        {
            if (cookTime < 400)
                if (open)
                    toolTip.add(StatCollector.translateToLocal("waila.waffle_iron_ready"));
                else
                    toolTip.add(StatCollector.translateToLocal("waila.waffle_iron_cooking") + "...");
            else
                toolTip.add(StatCollector.translateToLocal("waila.done"));
        }
    }

    public static class MixingBowlRecipe extends WaffleIronRecipe
    {
        String mixType = "";

        public MixingBowlRecipe(int rawColor, int cookedColor, int burntColor, ItemStack cookedResult, ItemStack burntResult, String mixingBowlType)
        {
            super(rawColor, cookedColor, burntColor, cookedResult, burntResult);

            this.mixType = mixingBowlType;
        }

        public MixingBowlRecipe(int rawColor, int cookedColor, int burntColor, Item cookedResult, ItemStack burntResult, String mixingBowlType)
        {
            this(rawColor, cookedColor, burntColor, new ItemStack(cookedResult, 2), burntResult, mixingBowlType);
        }

        public MixingBowlRecipe(int rawColor, int cookedColor, int burntColor, Item cookedResult, Item burntResult, String mixingBowlType)
        {
            this(rawColor, cookedColor, burntColor, new ItemStack(cookedResult, 2), new ItemStack(burntResult, 2), mixingBowlType);
        }

        public MixingBowlRecipe(int rawColor, int cookedColor, int burntColor, ItemStack cookedResult, Item burntResult, String mixingBowlType)
        {
            this(rawColor, cookedColor, burntColor, cookedResult, new ItemStack(burntResult, 2), mixingBowlType);
        }

        @Override
        public boolean add(ItemStack input)
        {
            String inputMixType = ItemMixingBowl.getMixType(input);
            if (inputMixType != null)
                if (!inputMixType.equals(""))
                    return inputMixType.equals(this.mixType);
            return false;
        }

        @Override
        public void afterAdd(ItemStack input)
        {
            ItemMixingBowl.reduceUsesLeft(input, 1);
        }
    }
}
