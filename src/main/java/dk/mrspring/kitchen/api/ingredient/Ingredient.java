package dk.mrspring.kitchen.api.ingredient;

import dk.mrspring.kitchen.pan.Jam;
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
