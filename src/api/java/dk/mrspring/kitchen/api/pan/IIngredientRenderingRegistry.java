package dk.mrspring.kitchen.api.pan;

/**
 * Created by Konrad on 20-05-2015.
 */
public interface IIngredientRenderingRegistry
{
    void registerRenderingHandler(IIngredientRenderingHandler handler);

    IIngredientRenderingHandler getHandlerFor(IFryingPan fryingPan, IIngredient ingredient);
}
