package dk.mrspring.kitchen.api.book;

/**
 * Created on 09-09-2015 for TheKitchenMod.
 */
public interface IPageElement
{
    void initElement(IPageElementContainer container);

    int getHeight(IPageElementContainer container);

    void render(IPageElementContainer container);
}
