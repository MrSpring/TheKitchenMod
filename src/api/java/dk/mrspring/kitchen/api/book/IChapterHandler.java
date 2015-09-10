package dk.mrspring.kitchen.api.book;

/**
 * Created on 09-09-2015 for TheKitchenMod.
 */
public interface IChapterHandler
{
    void addElementsToChapter(IChapter chapter);

    void addLockedElementsToChapter(IChapter chapter);

    String getId();

    String getUnlocalizedTitle();
}
