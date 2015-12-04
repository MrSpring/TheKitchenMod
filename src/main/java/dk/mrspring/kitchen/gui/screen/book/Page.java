package dk.mrspring.kitchen.gui.screen.book;

import dk.mrspring.kitchen.api.book.IPageElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class Page
{
    List<IPageElement> elements;
    Chapter chapter;

    public Page(List<IPageElement> elements, Chapter chapter)
    {
        this.elements = elements;
        this.chapter = chapter;
    }

    public Page(Chapter chapter)
    {
        this(new ArrayList<IPageElement>(), chapter);
    }

    public void addElement(int index, IPageElement element)
    {
        if (element != null) this.elements.add(index, element);
    }

    public void addElement(IPageElement element)
    {
        if (element != null) this.elements.add(element);
    }

    public Page copy()
    {
        return new Page(new ArrayList<IPageElement>(elements), this.getChapter());
    }

    public IPageElement[] asArray()
    {
        return elements.toArray(new IPageElement[elements.size()]);
    }

    public Chapter getChapter()
    {
        return chapter;
    }
}
