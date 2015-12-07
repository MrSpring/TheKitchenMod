package dk.mrspring.kitchen.api_impl.client.book.registry;

import dk.mrspring.kitchen.api.book.IBookRegistry;
import dk.mrspring.kitchen.api.book.IChapterHandler;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created on 06-12-2015 for TheKitchenMod.
 */
public class ManualTypeRegistry implements IBookRegistry
{
    private Map<String, IChapterHandler> registeredHandlers;

    public ManualTypeRegistry()
    {
        registeredHandlers = new LinkedHashMap<String, IChapterHandler>();
    }

    @Override
    public void registerChapterHandler(String id, IChapterHandler handler)
    {
        registeredHandlers.put(id, handler);
    }

    @Override
    public IChapterHandler getChapterHandler(String id)
    {
        return registeredHandlers.get(id);
    }
}
