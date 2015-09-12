package dk.mrspring.kitchen.api_impl.client.book.handler;

import dk.mrspring.kitchen.api.book.IChapter;
import dk.mrspring.kitchen.api.book.IChapterHandler;
import dk.mrspring.kitchen.api_impl.client.book.element.TableOfContentElement;
import dk.mrspring.kitchen.api_impl.client.book.element.TitleElement;

/**
 * Created on 11-09-2015 for TheKitchenMod.
 */
public class TableOfContentHandler implements IChapterHandler
{
    @Override
    public void addElementsToChapter(IChapter chapter)
    {
        chapter.addElement(new TitleElement("Table of Content"));
        chapter.addElement(new TableOfContentElement());
    }

    @Override
    public void addLockedElementsToChapter(IChapter chapter)
    {
        this.addElementsToChapter(chapter);
    }

    @Override
    public String getId()
    {
        return "tableofcontent";
    }

    @Override
    public String getUnlocalizedTitle()
    {
        return "item.cooking_book.pages.contents.title";
    }
}
