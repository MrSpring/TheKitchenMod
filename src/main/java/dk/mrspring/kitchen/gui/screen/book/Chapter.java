package dk.mrspring.kitchen.gui.screen.book;

import dk.mrspring.kitchen.api.book.IChapter;
import dk.mrspring.kitchen.api.book.IPageElement;
import dk.mrspring.kitchen.api_impl.client.book.element.EndOfPageElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class Chapter implements IChapter
{
    List<IPageElement> elements = new ArrayList<IPageElement>();

    boolean locked;
    String id;

    public Chapter(boolean isLocked, String id)
    {
        this.locked = isLocked;
        this.id = id;
    }

    @Override
    public void forceNewPage()
    {
        this.addElement(new EndOfPageElement());
    }

    @Override
    public void addElement(IPageElement element)
    {
        this.elements.add(element);
    }

    @Override
    public void addElement(IPageElement element, int index)
    {
        this.elements.add(index, element);
    }

    @Override
    public void addAllElements(Collection<IPageElement> elements)
    {
        this.elements.addAll(elements);
    }

    @Override
    public boolean isLocked()
    {
        return locked;
    }

    @Override
    public List<IPageElement> getElements()
    {
        return elements;
    }
}
