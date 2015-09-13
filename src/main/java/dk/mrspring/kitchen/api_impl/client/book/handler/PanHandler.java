package dk.mrspring.kitchen.api_impl.client.book.handler;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.api.book.IChapter;
import dk.mrspring.kitchen.api.book.IChapterHandler;
import dk.mrspring.kitchen.api_impl.client.book.element.*;
import net.minecraft.item.ItemStack;

/**
 * Created on 11-09-2015 for TheKitchenMod.
 */
public class PanHandler implements IChapterHandler
{
    @Override
    public void addElementsToChapter(IChapter chapter)
    {

    }

    @Override
    public void addLockedElementsToChapter(IChapter chapter)
    {
        this.addTitle(chapter);
        chapter.addElement(new SpacerElement(3).disableImage());
        chapter.addElement(new TextElement("Craft a Pan, like so, to unlock this chapter!", true));
        chapter.addElement(new SpacerElement(20));
        chapter.addElement(new RecipeElement(new ItemStack(KitchenBlocks.frying_pan)));
//        chapter.addElement(new EndOfPageElement());
//        chapter.addElement(new RecipeElement(new ItemStack(KitchenBlocks.frying_pan)));
    }

    private void addTitle(IChapter chapter)
    {
        chapter.addElement(new TitleElement("Pan"));
    }

    @Override
    public String getId()
    {
        return "pan";
    }

    @Override
    public String getUnlocalizedTitle()
    {
        return "item.cooking_book.pages.pan.title";
    }
}
