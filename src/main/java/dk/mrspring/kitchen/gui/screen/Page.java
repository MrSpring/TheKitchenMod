package dk.mrspring.kitchen.gui.screen;

import dk.mrspring.kitchen.api.book.IPageElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class Page
{
    List<IPageElement> elements;

    public Page(List<IPageElement> elements)
    {
        this.elements = elements;
    }

    public Page()
    {
        this(new ArrayList<IPageElement>());
    }

    public void addElement(int index, IPageElement element)
    {
        this.elements.add(index, element);
    }

    public void addElement(IPageElement element)
    {
        this.elements.add(element);
    }

    public Page copy()
    {
        return new Page(new ArrayList<IPageElement>(elements));
    }

    public IPageElement[] asArray()
    {
        return elements.toArray(new IPageElement[elements.size()]);
    }
}
