package dk.mrspring.kitchen.pan;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrSpring on 19-10-2014 for TheKitchenMod.
 */
public class Ingredient
{
    public static Map<String, Ingredient> ingredients = new HashMap<String, Ingredient>();

    final String name;
    final IIngredientRenderingHandler renderingHandler;
    final boolean isJam;
    final String jResult;
    final ItemStack iResult;

    public Ingredient(String name, IIngredientRenderingHandler handler, boolean jam, String jamResult, ItemStack itemResult)
    {
        this.name = name;
        this.renderingHandler = handler;
        this.isJam = jam;
        this.jResult = jamResult;
        this.iResult = itemResult;
    }

    public Ingredient(String name, IIngredientRenderingHandler handler, ItemStack iResult)
    {
        this(name, handler, false, "empty", iResult);
    }

    public Ingredient(String name, IIngredientRenderingHandler handler, String jResult)
    {
        this(name, handler, true, jResult, null);
    }

    public String getLocalizedName()
    {
        if (this.isJam())
        {
            if (StatCollector.canTranslate("jam." + this.getJamResult().getName() + ".name"))
                return StatCollector.translateToLocal("jam." + this.getJamResult().getName() + ".name");
            else return this.getJamResult().getName();
        } else
            return this.getItemResult().getDisplayName();
    }

    public static void registerIngredient(Ingredient ingredient)
    {
        if (ingredient != null)
            if (!ingredients.containsKey(ingredient.getName()))
                ingredients.put(ingredient.getName(), ingredient);
    }

    public static Ingredient getIngredient(String name)
    {
        if (name != null)
            if (ingredients.containsKey(name))
                return ingredients.get(name);
        return ingredients.get("empty");
    }

    public String getName()
    {
        return name;
    }

    public IIngredientRenderingHandler getRenderingHandler()
    {
        return this.renderingHandler;
    }

    public boolean isJam()
    {
        return isJam;
    }

    public Jam getJamResult()
    {
        return Jam.getJam(this.jResult);
    }

    public ItemStack getItemResult()
    {
        return iResult.copy();
    }

    public boolean canAdd(ItemStack stack)
    {
        return true;
    }

    public void onAdded(ItemStack clickedStack)
    {
        clickedStack.stackSize--;
    }

    public void onIngredientFinished(ItemStack result, EntityPlayer player)
    {

    }
}
