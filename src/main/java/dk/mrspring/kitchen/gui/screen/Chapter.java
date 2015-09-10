package dk.mrspring.kitchen.gui.screen;

import dk.mrspring.kitchen.api.book.IChapter;
import dk.mrspring.kitchen.api.book.IPageElement;
import dk.mrspring.kitchen.api_impl.client.book.element.EndOfPageElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class Chapter implements IChapter
{
    List<IPageElement> elements = new ArrayList<IPageElement>();

    boolean locked;

    public Chapter(boolean isLocked)
    {
        this.locked = isLocked;
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
