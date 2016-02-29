package dk.mrspring.kitchen.api_impl.client.book.handler;

import dk.mrspring.kitchen.api.book.IChapter;
import dk.mrspring.kitchen.api.book.IChapterHandler;
import dk.mrspring.kitchen.api_impl.client.book.element.*;
import net.minecraft.util.StatCollector;

/**
 * Created on 13-09-2015 for TheKitchenMod.
 */
public class IntroductionHandler implements IChapterHandler
{
    @Override
    public void addElementsToChapter(IChapter chapter)
    {
        chapter.addElement(new TitleElement("Welcome"));
        chapter.addElement(new SpacerElement(20));
        chapter.addElement(new TextElement(t("item.cooking_book.pages.introduction.text01"), Alignment.CENTER));
        chapter.addElement(new SpacerElement(5).disableImage());
        chapter.addElement(new TextElement(t("item.cooking_book.pages.introduction.text02"), Alignment.CENTER));
//        chapter.addElement(new SpacerElement(33).disableImage()/*new ImageElement(ModInfo.toResource("textures/gui/cooking_book_1.png"), 99, 160, 99, 33)*/);
        chapter.addElement(new EndOfPageElement(11));
        chapter.addElement(new TextElement("- MrSpring", Alignment.CENTER));
    }

    private String t(String s)
    {
        return StatCollector.translateToLocal(s).replaceAll("\\\\n", "\n");
    }

    @Override
    public void addLockedElementsToChapter(IChapter chapter)
    {
        this.addElementsToChapter(chapter);
    }

    @Override
    public String getId()
    {
        return null;
    }

    @Override
    public String getUnlocalizedTitle()
    {
        return null;
    }
}
