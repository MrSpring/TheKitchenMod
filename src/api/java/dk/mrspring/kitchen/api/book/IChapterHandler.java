package dk.mrspring.kitchen.api.book;

import net.minecraft.util.StatCollector;

/**
 * Created on 09-09-2015 for TheKitchenMod.
 */
public interface IChapterHandler // TODO: showInToc()
{
    void addElementsToChapter(IChapter chapter);

    void addLockedElementsToChapter(IChapter chapter);

    String getId();

    String getUnlocalizedTitle();
}
