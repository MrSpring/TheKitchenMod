package dk.mrspring.kitchen.api_impl.client.book.element;

import dk.mrspring.kitchen.api.book.IPageElement;
import dk.mrspring.kitchen.api.book.IPageElementContainer;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class EndOfPageElement implements IPageElement
{
    @Override
    public void initElement(IPageElementContainer container)
    {
    }

    @Override
    public int getHeight(IPageElementContainer container)
    {
        return container.getAvailableHeight() + 1;
    }

    @Override
    public void render(IPageElementContainer container)
    {
    }
}
