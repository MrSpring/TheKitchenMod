package dk.mrspring.kitchen.api.oven;

/**
 * Created by Konrad on 20-07-2015.
 */
public interface IOvenItemRenderingHandler
{
    boolean shouldBeUsed(IOven oven, IOvenItem item, int slot, boolean firstSlot);

    void renderPreTranslate(IOven oven, IOvenItem item, int slot, boolean firstSlot);

    void render(IOven oven, IOvenItem item, int slot, boolean firstSlot);
}
