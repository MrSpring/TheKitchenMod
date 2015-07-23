package dk.mrspring.kitchen.api.oven;

/**
 * Created by Konrad on 20-07-2015.
 */
public interface IOvenItemRenderingRegistry
{
    void registerRenderingHandler(IOvenItemRenderingHandler handler);

    IOvenItemRenderingHandler getHandlerFor(IOven oven, IOvenItem item, int slot, boolean first);
}
