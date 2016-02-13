package dk.mrspring.kitchen.api_impl.client.book;

import dk.mrspring.kitchen.api.book.IChapterHandler;
import dk.mrspring.kitchen.api.book.IBookRegistry;
import dk.mrspring.kitchen.api_impl.client.book.handler.*;

import java.util.*;

/**
 * Created on 09-09-2015 for TheKitchenMod.
 */
public class BasicBookRegistry implements IBookRegistry
{
    private Map<String, IChapterHandler> registeredHandlers;

    public BasicBookRegistry()
    {
        registeredHandlers = new LinkedHashMap<String, IChapterHandler>();
    }

    @Override
    public void registerChapterHandler(String id, IChapterHandler handler)
    {
        if (!registeredHandlers.containsKey(id))
            registeredHandlers.put(id, handler);
    }

    @Override
    public IChapterHandler getChapterHandler(String id)
    {
        return registeredHandlers.get(id);
    }

    public List<IChapterHandler> getRegisteredHandlers()
    {
        return new LinkedList<IChapterHandler>(registeredHandlers.values());
    }
}
