package dk.mrspring.kitchen.gui.screen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class PagedChapter
{
    List<Page> pages = new ArrayList<Page>();
    String id;

    public PagedChapter(String id)
    {
        this.id = id;
    }

    public void addPage(Page page)
    {
        this.pages.add(page);
        System.out.println("Adding page of size: " + page.elements.size());
    }


}
