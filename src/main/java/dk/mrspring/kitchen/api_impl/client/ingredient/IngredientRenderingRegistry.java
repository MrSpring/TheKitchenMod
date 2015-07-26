package dk.mrspring.kitchen.api_impl.client.ingredient;

import dk.mrspring.kitchen.api.pan.IFryingPan;
import dk.mrspring.kitchen.api.pan.IIngredient;
import dk.mrspring.kitchen.api.pan.IIngredientRenderingHandler;
import dk.mrspring.kitchen.api.pan.IIngredientRenderingRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 20-05-2015.
 */
public class IngredientRenderingRegistry implements IIngredientRenderingRegistry
{
    private static IngredientRenderingRegistry ourInstance = new IngredientRenderingRegistry();
    private static IIngredientRenderingHandler defaultHandler = new FallbackIngredientRenderingHandler();
    List<IIngredientRenderingHandler> registeredHandlers = new ArrayList<IIngredientRenderingHandler>();

    private IngredientRenderingRegistry()
    {
        registerRenderingHandler(new RecipeIngredientRenderingHandler());
    }

    public static IngredientRenderingRegistry getInstance()
    {
        return ourInstance;
    }

    @Override
    public void registerRenderingHandler(IIngredientRenderingHandler handler)
    {
        registeredHandlers.add(handler);
    }

    @Override
    public IIngredientRenderingHandler getHandlerFor(IFryingPan fryingPan, IIngredient ingredient)
    {
        for (IIngredientRenderingHandler handler : registeredHandlers)
            if (handler.shouldBeUsed(fryingPan, ingredient))
                return handler;
        return defaultHandler;
    }
}
