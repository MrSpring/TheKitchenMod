package dk.mrspring.kitchen.api_impl.client.book.element;

import dk.mrspring.kitchen.api.book.IPageElement;
import dk.mrspring.kitchen.api.book.IPageElementContainer;

/**
 * Created on 11-09-2015 for TheKitchenMod.
 */
public class SpacerElement implements IPageElement
{
    int height;

    public SpacerElement(int height)
    {
        this.height = height;
    }

    @Override
    public void initElement(IPageElementContainer container)
    {
    }

    @Override
    public int getHeight(IPageElementContainer container)
    {
        return height;
    }

    @Override
    public void render(IPageElementContainer container)
    {
    }
}
