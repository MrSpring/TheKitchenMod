package dk.mrspring.kitchen.api_impl.client.oven;

import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.api.oven.IOvenItemRenderingHandler;

/**
 * Created by Konrad on 20-07-2015.
 */
public class FallbackItemRenderingHandler implements IOvenItemRenderingHandler
{
    @Override
    public boolean shouldBeUsed(IOven oven, IOvenItem item, int slot, boolean firstSlot)
    {
        return false;
    }

    @Override
    public void render(IOven oven, IOvenItem item, int slot, boolean firstSlot)
    {

    }
}
