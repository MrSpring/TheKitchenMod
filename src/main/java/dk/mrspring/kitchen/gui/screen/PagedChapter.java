package dk.mrspring.kitchen.gui.screen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class PagedChapter
{
    List<Page> pages = new ArrayList<Page>();
    Chapter chapter;

    public PagedChapter(Chapter chapter)
    {
        this.chapter = chapter;
    }

    public void addPage(Page page)
    {
        this.pages.add(page);
    }
}
