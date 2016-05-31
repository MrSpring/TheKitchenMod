package dk.mrspring.kitchen.pan;

/**
 * Created by MrSpring on 27-10-2014 for TheKitchenMod.
 */
public interface IIngredientRenderingHandler
{
    boolean translateModel(int cookTime, Ingredient ingredient);

    void render(int cookTime, Ingredient ingredient);
}
