package dk.mrspring.kitchen.pot;

import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 19-10-2014 for TheKitchenMod.
 */
public enum Ingredient
{
    STRAWBERRY(new JamBaseRenderingHandler(new float[]{255F, 60, 53}), Jam.STRAWBERRY),
    APPLE(new JamBaseRenderingHandler(new float[]{224, 255, 163}), Jam.APPLE),
    PEANUT(new JamBaseRenderingHandler(new float[]{147, 101, 41}), Jam.PEANUT);

    final IIngredientRenderingHandler renderingHandler;
    final boolean isJam;
    final Jam jResult;
    final ItemStack iResult;

    private Ingredient(IIngredientRenderingHandler handler, boolean jam, Jam jamResult, ItemStack itemResult)
    {
        this.renderingHandler = handler;
        this.isJam = jam;
        this.jResult = jamResult;
        this.iResult = itemResult;
    }

    private Ingredient(IIngredientRenderingHandler handler, ItemStack iResult)
    {
        this(handler, false, Jam.EMPTY, iResult);
    }

    private Ingredient(IIngredientRenderingHandler handler, Jam jResult)
    {
        this(handler, true, jResult, null);
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
        return jResult;
    }

    public ItemStack getItemResult()
    {
        return iResult;
    }
}
