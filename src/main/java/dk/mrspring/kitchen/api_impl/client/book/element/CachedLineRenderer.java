package dk.mrspring.kitchen.api_impl.client.book.element;

import net.minecraft.client.gui.FontRenderer;

/**
 * Created on 13-02-2016 for TheKitchenMod.
 */
public class CachedLineRenderer
{
    String line;
    int x, y;
    int color;
    boolean shadow;

    public CachedLineRenderer(String text, int x, int y, int color, boolean shadow)
    {
        this.line = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.shadow = shadow;
    }

    public CachedLineRenderer(String text, int x, int y, int color)
    {
        this(text, x, y, color, true);
    }

    public CachedLineRenderer(String text, int x, int y)
    {
        this(text, x, y, 0xFFFFFF);
    }

    public void render(FontRenderer renderer)
    {
        renderer.drawString(line, x, y, color, shadow);
    }
}
