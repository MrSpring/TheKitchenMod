package dk.mrspring.kitchen.api_impl.client.book.element;

import dk.mrspring.kitchen.api.book.IPageElementContainer;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class TitleElement extends TextElement
{
    public TitleElement(String title)
    {
        super(title, true);
    }

    @Override
    public float getScaleFactor(IPageElementContainer container)
    {
        return 2F;
    }
}
