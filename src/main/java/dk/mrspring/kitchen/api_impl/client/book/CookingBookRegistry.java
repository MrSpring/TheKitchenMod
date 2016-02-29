package dk.mrspring.kitchen.api_impl.client.book;

import dk.mrspring.kitchen.api_impl.client.book.handler.*;

/**
 * Created on 01-02-2016 for TheKitchenMod.
 */
public class CookingBookRegistry extends BasicBookRegistry
{
    private static CookingBookRegistry ourInstance = new CookingBookRegistry();

    public static CookingBookRegistry getInstance()
    {
        return ourInstance;
    }

    public CookingBookRegistry()
    {
        super();

        registerChapterHandler("introduction", new IntroductionHandler());
        registerChapterHandler("tableofcontent", new TableOfContentHandler());
        registerChapterHandler(CuttingBoardHandler.ID, new CuttingBoardHandler());
        registerChapterHandler("oven", new OvenHandler());
        registerChapterHandler("pan", new PanHandler());
    }
}
