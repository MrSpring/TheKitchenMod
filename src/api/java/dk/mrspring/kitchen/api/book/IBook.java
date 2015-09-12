package dk.mrspring.kitchen.api.book;

import java.util.Map;

/**
 * Created on 11-09-2015 for TheKitchenMod.
 */
public interface IBook
{
    Map<String, ChapterMarker> getTableOfContent();

    void goToPage(int page);
}
