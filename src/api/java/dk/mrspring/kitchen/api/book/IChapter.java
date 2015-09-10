package dk.mrspring.kitchen.api.book;

import java.util.List;

/**
 * Created on 09-09-2015 for TheKitchenMod.
 */
public interface IChapter
{
    void forceNewPage();

    void addElement(IPageElement element);

    void addElement(IPageElement element, int index);

    boolean isLocked();

    List<IPageElement> getElements();
}
