package dk.mrspring.kitchen.api_impl.client.book.element;

import dk.mrspring.kitchen.api.book.IPageElement;
import dk.mrspring.kitchen.api.book.IPageElementContainer;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

import java.util.LinkedList;
import java.util.List;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class TextElement implements IPageElement
{
    String raw;
    List<CachedLineRenderer> lines;
    Alignment align;
    float scaleFactor;
    int textColor = 0x4C1C06;
    boolean textShadow = false;

    public TextElement(List<CachedLineRenderer> lines, Alignment align, float scaleFactor)
    {
        this.lines = lines;
        this.align = align;
        this.scaleFactor = scaleFactor;
    }

    public TextElement(String text, Alignment align, float scaleFactor, int textColor, boolean textShadow)
    {
        this.raw = text;
        this.align = align;
        this.scaleFactor = scaleFactor;
        this.lines = new LinkedList<CachedLineRenderer>();
        this.textColor = textColor;
        this.textShadow = textShadow;
    }

    public TextElement(String text, Alignment align, float scaleFactor, int textColor)
    {
        this(text, align, scaleFactor, textColor, false);
    }

    public TextElement(String text, Alignment align, float scaleFactor)
    {
        this(text, align, scaleFactor, 0x4C1C06);
    }

    public TextElement(String text, Alignment align)
    {
        this(text, align, 1F);
    }

    public TextElement(String text)
    {
        this(text, Alignment.LEFT); // TODO: Language default?
    }

    private static final char NEW_LINE = 10, SPACE = 32;

    @Override
    public void initElement(IPageElementContainer container)
    {
        String remaining = raw;
        int i = 0, r;
        FontRenderer font = container.getMinecraft().fontRenderer;
        while ((r = remaining.length()) > 0)
        {
            int y = i * font.FONT_HEIGHT, ay = (int) (y * getScaleFactor(container));
            int width = (int) (getWidthAtHeight(y, container) / getScaleFactor(container));
            int x = getXOffsetAtHeight(y, container) + (int) (align.transform(width));
            int fits = font.sizeStringToWidth(remaining, width);
            if (fits >= r)
            {
                lines.add(new CachedLineRenderer(remaining,
                        x - (int) align.transform(font.getStringWidth(remaining)),
                        y, textColor, textShadow));
                break;
            } else
            {
                String newLine = remaining.substring(0, fits);
                char f = remaining.charAt(fits);
                boolean isEmpty = f == SPACE || f == NEW_LINE;
                lines.add(new CachedLineRenderer(newLine,
                        x - (int) align.transform(font.getStringWidth(newLine)),
                        y, textColor, textShadow));
                remaining = remaining.substring(fits + (isEmpty ? 1 : 0));
                i++;
            }
        }
    }

    @Override
    public IPageElement createSplitElement(IPageElementContainer container)
    {
        int fh = container.getMinecraft().fontRenderer.FONT_HEIGHT, afh = (int) (fh * scaleFactor);
        int height = (int) (container.getAvailableHeight() / getScaleFactor(container));
        List<CachedLineRenderer> split = new LinkedList<CachedLineRenderer>();
        int i = 0, j = 0;
        while (i < lines.size())
        {
            CachedLineRenderer line = lines.get(i);
            if (line.y + fh > height)
            {
                lines.remove(line);
                split.add(line);
                line.y = j * fh;
                j++;
            } else i++;
        }
        return new TextElement(split, align, scaleFactor);
    }

    @Override
    public boolean canSplit(IPageElementContainer container)
    {
        return true;
    }

    public int getWidthAtHeight(int y, IPageElementContainer container)
    {
        return container.getAvailableWidth();
    }

    public int getXOffsetAtHeight(int y, IPageElementContainer container)
    {
        return 0;
    }

    public float getScaleFactor(IPageElementContainer container)
    {
        return scaleFactor;
    }

    @Override
    public int getHeight(IPageElementContainer container)
    {
        return (int) ((lines.size() * container.getMinecraft().fontRenderer.FONT_HEIGHT) * scaleFactor);
    }

    @Override
    public void render(IPageElementContainer container, int mouseX, int mouseY)
    {
        GL11.glPushMatrix();
        if (scaleFactor != 1F) GL11.glScalef(scaleFactor, scaleFactor, 1F);
        FontRenderer font = container.getMinecraft().fontRenderer;
        for (CachedLineRenderer renderer : lines)
            renderer.render(font);
        GL11.glPopMatrix();
//        font.drawSplitString(raw, 0,0,container.getAvailableWidth(), textColor);
//        List<String> lines = font.listFormattedStringToWidth(raw, container.getAvailableWidth());
//        for (int i = 0; i < lines.size(); i++)
//        {
//            String s = lines.get(i);
//            font.drawString(s, 0, i * font.FONT_HEIGHT, textColor, textShadow);
//        }
    }

    @Override
    public void mouseClicked(IPageElementContainer container, int mouseX, int mouseY, int mouseButton)
    {
    }

    @Override
    public void onUpdate(IPageElementContainer container)
    {
    }

    /*@Override // TODO: Re-implement
    public IPageElement createSplitElement(IPageElementContainer container)
    {
        return null;
    }*/
    /*String rawText;
    List<String> lines;
    float height;
    public int color = 0x4C1C06;
    boolean center = false;
    float scaleFactor = 1F;

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
        return scaleFactor;
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

    @Override
    public void mouseClicked(IPageElementContainer container, int mouseX, int mouseY, int mouseButton)
    {
    }

    @Override
    public void onUpdate(IPageElementContainer container)
    {
    }

    public TextElement setScaleFactor(float scale)
    {
        this.scaleFactor = scale;
        return this;
    }*/
}
