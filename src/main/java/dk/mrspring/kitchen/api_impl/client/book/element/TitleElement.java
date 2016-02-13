package dk.mrspring.kitchen.api_impl.client.book.element;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class TitleElement extends TextElement
{
    public TitleElement(String title)
    {
        super(title, Alignment.CENTER);
        scaleFactor = 2F;
    }
}
