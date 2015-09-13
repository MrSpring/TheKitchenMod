package dk.mrspring.kitchen.api_impl.client.book.element;

import dk.mrspring.kitchen.api.book.IPageElement;
import dk.mrspring.kitchen.api.book.IPageElementContainer;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class EndOfPageElement implements IPageElement
{
    int offset;

    public EndOfPageElement(int offset)
    {
        this.offset = offset;
    }

    public EndOfPageElement()
    {
        this(1);
    }

    @Override

    public void initElement(IPageElementContainer container)
    {
    }

    @Override
    public int getHeight(IPageElementContainer container)
    {
        return container.getAvailableHeight() - offset;
    }

    @Override
    public void render(IPageElementContainer container, int mouseX, int mouseY)
    {
    }

    @Override
    public void mouseClicked(IPageElementContainer container, int mouseX, int mouseY, int mouseButton)
    {
    }

    @Override
    public void onUpdate(IPageElementContainer container)
    {
    }
}
