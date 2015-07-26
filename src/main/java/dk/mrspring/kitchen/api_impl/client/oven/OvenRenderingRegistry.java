package dk.mrspring.kitchen.api_impl.client.oven;

import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.api.oven.IOvenItemRenderingHandler;
import dk.mrspring.kitchen.api.oven.IOvenItemRenderingRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 20-07-2015.
 */
public class OvenRenderingRegistry implements IOvenItemRenderingRegistry
{
    private static OvenRenderingRegistry ourInstance = new OvenRenderingRegistry();
    private static IOvenItemRenderingHandler defaultHandler = new FallbackItemRenderingHandler();
    List<IOvenItemRenderingHandler> registeredHandlers = new ArrayList<IOvenItemRenderingHandler>();

    private OvenRenderingRegistry()
    {
        registerRenderingHandler(new RecipeItemRenderingHandler());
    }

    public static OvenRenderingRegistry getInstance()
    {
        return ourInstance;
    }

    @Override
    public void registerRenderingHandler(IOvenItemRenderingHandler handler)
    {
        registeredHandlers.add(handler);
    }

    @Override
    public IOvenItemRenderingHandler getHandlerFor(IOven oven, IOvenItem item, int slot, boolean first)
    {
        for (IOvenItemRenderingHandler handler : registeredHandlers)
            if (handler.shouldBeUsed(oven, item, slot, first))
                return handler;
        return defaultHandler;
    }
}
