package dk.mrspring.kitchen.gui.screen;

import dk.mrspring.kitchen.api.book.IChapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class PagedChapter
{
    List<Page> pages = new ArrayList<Page>();

    public void addPage(Page page)
    {
        this.pages.add(page);
        System.out.println("Adding page of size: "+page.elements.size());
    }


}
