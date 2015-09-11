package dk.mrspring.kitchen.api_impl.client.book.element;

import dk.mrspring.kitchen.api.book.IPageElement;
import dk.mrspring.kitchen.api.book.IPageElementContainer;
import dk.mrspring.kitchen.api.book.ISplittable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class TextElement implements IPageElement, ISplittable
{
    String rawText;
    List<String> lines;
    float height;
    public int color = 0x4C1C06;
    boolean center = false;

    public TextElement(String rawText, boolean center)
    {
        this.rawText = rawText;
        this.center = center;
    }

    public TextElement(String rawText)
    {
        this(rawText, false);
    }

    public TextElement(List<String> lines, boolean center)
    {
        this.lines = lines;
        this.center = center;
    }

    public TextElement(List<String> lines)
    {
        this(lines, false);
    }

    @Override
    public void initElement(IPageElementContainer container)
    {
        FontRenderer renderer = container.getMinecraft().fontRenderer;
        float scale = getScaleFactor(container);
        if (lines == null)
        {
            float w = container.getAvailableWidth() / getScaleFactor(container);
            lines = renderer.listFormattedStringToWidth(this.rawText, (int) w);
        }
        height = lines.size() * renderer.FONT_HEIGHT * scale;
    }

    public float getScaleFactor(IPageElementContainer container)
    {
        return 1F;
    }

    @Override
    public int getHeight(IPageElementContainer container)
    {
        return (int) this.height;
    }

    @Override
    public void render(IPageElementContainer container, int mouseX, int mouseY)
    {
        FontRenderer renderer = container.getMinecraft().fontRenderer;
        GL11.glPushMatrix();
        float s = getScaleFactor(container);
        GL11.glScalef(s, s, s);
        for (int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);
            if (center)
                drawCenteredText(line, 0, i * renderer.FONT_HEIGHT, container.getAvailableWidth(), color, renderer, container);
            else renderer.drawString(line, 0, i * renderer.FONT_HEIGHT, color);
        }
        GL11.glPopMatrix();
    }

    @Override
    public IPageElement createSplitElement(IPageElementContainer container)
    {
        FontRenderer renderer = container.getMinecraft().fontRenderer;
        int linesAvailable = container.getAvailableHeight() / renderer.FONT_HEIGHT;
        int linesSize = lines.size();
        System.out.println(linesAvailable + ", " + linesSize);
        if (linesSize > linesAvailable)
        {
            List<String> newElementLines = this.lines.subList(linesAvailable, linesSize);
            this.lines = this.lines.subList(0, linesAvailable);
            return new TextElement(newElementLines, center);
        } else return null;
    }

    private void drawCenteredText(String text, int x, int y, int width, int color, FontRenderer renderer, IPageElementContainer container)
    {
        x += (width / getScaleFactor(container)) / 2;
        x -= renderer.getStringWidth(text) / 2;
        renderer.drawString(text, x, y, color);
    }
}
