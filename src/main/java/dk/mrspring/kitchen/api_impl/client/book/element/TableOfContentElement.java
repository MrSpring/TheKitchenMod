package dk.mrspring.kitchen.api_impl.client.book.element;

import dk.mrspring.kitchen.api.book.ChapterMarker;
import dk.mrspring.kitchen.api.book.IPageElement;
import dk.mrspring.kitchen.api.book.IPageElementContainer;
import dk.mrspring.kitchen.api.book.ISplittable;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 11-09-2015 for TheKitchenMod.
 */
public class TableOfContentElement implements IPageElement, ISplittable
{
    Map<String, ChapterMarker> markerMap;
    int fromIndex = -1, toIndex = -1;
    final int LINE_HEIGHT = 12;

    @Override
    public void initElement(IPageElementContainer container)
    {
        this.markerMap = container.getGui().getTableOfContent();
        if (fromIndex == -1 || toIndex == -1)
        {
            fromIndex = 1;
            toIndex = markerMap.size();
        }
    }

    @Override
    public int getHeight(IPageElementContainer container)
    {
        return LINE_HEIGHT * markerMap.size();
    }

    @Override
    public void render(IPageElementContainer container, int mouseX, int mouseY)
    {
        FontRenderer renderer = container.getMinecraft().fontRenderer;
        int i = 0;
        for (ChapterMarker marker : markerMap.values())
        {
            if (i >= fromIndex && i < toIndex)
            {
                renderer.drawString((marker.getPageIndex() + 1) + ". " + marker.getDisplayName(), 0, 0, 0x4C1C06);
                GL11.glTranslatef(0, LINE_HEIGHT, 0);
            }
            i++;
        }
    }

    @Override
    public IPageElement createSplitElement(IPageElementContainer container)
    {
        int linesAvailable = container.getAvailableHeight() / LINE_HEIGHT;
        int linesSize = markerMap.size();
        if (linesSize > linesAvailable)
        {
            this.toIndex = linesAvailable;
            TableOfContentElement split = new TableOfContentElement();
            split.fromIndex = this.toIndex;
            split.toIndex = markerMap.size();
            return split;
        } else return null;
    }

    @Override
    public void mouseClicked(IPageElementContainer container, int mouseX, int mouseY, int mouseButton)
    {
        ChapterMarker clicked = getMarkerAt(mouseX, mouseY);
        if (clicked != null) container.getGui().goToPage(clicked.getPageIndex());
    }

    @Override
    public void onUpdate(IPageElementContainer container)
    {

    }

    private ChapterMarker getMarkerAt(int x, int y)
    {
        int index = y / LINE_HEIGHT;
        index += fromIndex;
        List<ChapterMarker> markers = new ArrayList<ChapterMarker>(markerMap.values());
        return index >= 0 && index < toIndex ? markers.get(index) : null;
    }
}
