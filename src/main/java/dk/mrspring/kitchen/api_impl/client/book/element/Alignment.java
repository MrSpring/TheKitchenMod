package dk.mrspring.kitchen.api_impl.client.book.element;

/**
 * Created on 13-02-2016 for TheKitchenMod.
 */
public enum Alignment
{
    LEFT(0F),
    CENTER(0.5F),
    RIGHT(1F);

    public final float RIGHT_FACTOR;

    Alignment(float rightAlign)
    {
        this.RIGHT_FACTOR = rightAlign;
    }

    public float transform(float x)
    {
        return x * RIGHT_FACTOR;
    }
}
