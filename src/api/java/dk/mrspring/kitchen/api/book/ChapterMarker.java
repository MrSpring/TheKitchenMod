package dk.mrspring.kitchen.api.book;

import net.minecraft.util.StatCollector;

/**
 * Created on 11-09-2015 for TheKitchenMod.
 */
public class ChapterMarker
{
    IChapterHandler handler;
    int pageIndex;

    public ChapterMarker(IChapterHandler handler)
    {
        this.handler = handler;
    }

    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }

    public int getPageIndex()
    {
        return pageIndex;
    }

    public String getId()
    {
        return handler.getId();
    }

    public String getDisplayName()
    {
        return StatCollector.translateToLocal(getUnlocalizedName());
    }

    public String getUnlocalizedName()
    {
        return handler.getUnlocalizedTitle();
    }
}
