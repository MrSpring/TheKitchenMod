package dk.mrspring.kitchen.api_impl.client.ingredient;

import dk.mrspring.kitchen.api.ingredient.IFryingPan;
import dk.mrspring.kitchen.api.ingredient.IIngredient;
import dk.mrspring.kitchen.api.ingredient.IIngredientRenderingHandler;

/**
 * Created by Konrad on 20-05-2015.
 */
public class FallbackIngredientRenderingHandler implements IIngredientRenderingHandler
{
    @Override
    public boolean shouldBeUsed(IFryingPan fryingPan, IIngredient ingredient)
    {
        return true;
    }

    @Override
    public void render(IFryingPan fryingPan, IIngredient ingredient)
    {
        //TODO: Render
    }
}
