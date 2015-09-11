package dk.mrspring.kitchen.api_impl.client.book.handler;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.book.IChapter;
import dk.mrspring.kitchen.api.book.IChapterHandler;
import dk.mrspring.kitchen.api_impl.client.book.element.*;
import net.minecraft.item.ItemStack;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class SandwichHandler implements IChapterHandler
{
    @Override
    public void addElementsToChapter(IChapter chapter)
    {
        this.addTitle(chapter);
        chapter.addElement(new TextElement("This is the first paragraph in the chapter that is all about dem delicious sandwiches!"));
        chapter.addElement(new ImageElement(ModInfo.toResource("textures/gui/cooking_book.png"), 99, 0, 99, 99));
//        chapter.addElement(new ImageElement(ModInfo.toResource("textures/gui/cooking_book.png"), 0, 161, 99, 61));
    }

    @Override
    public void addLockedElementsToChapter(IChapter chapter)
    {
        this.addTitle(chapter);
        chapter.addElement(new SpacerElement(10));
        chapter.addElement(new TextElement("Craft a Cutting Board, like so, to unlock this chapter!", true));
        chapter.addElement(new SpacerElement(10));
        chapter.addElement(new RecipeElement(new ItemStack(KitchenBlocks.board)));
        chapter.addElement(new TextElement("Craft a Cutting Board, like so, to unlock this chapter!", true));
        chapter.addElement(new TextElement("Craft a Cutting Board, like so, to unlock this chapter!", true));
        chapter.addElement(new TextElement("Craft a Cutting Board, like so, to unlock this chapter!", true));
        chapter.addElement(new TextElement("Craft a Cutting Board, like so, to unlock this chapter!", true));
        chapter.addElement(new TextElement("Craft a Cutting Board, like so, to unlock this chapter!", true));
        chapter.addElement(new TextElement("Craft a Cutting Board, like so, to unlock this chapter!", true));
        chapter.addElement(new TextElement("Craft a Cutting Board, like so, to unlock this chapter!", true));
        chapter.addElement(new TextElement("Craft a Cutting Board, like so, to unlock this chapter!", true));
        chapter.addElement(new TextElement("Craft a Cutting Board, like so, to unlock this chapter!", true));
//        chapter.addElement(new ImageElement(ModInfo.toResource("textures/gui/cooking_book.png"), 0, 161, 99, 61));
    }

    private void addTitle(IChapter chapter)
    {
        chapter.addElement(new TitleElement("Sandwiches"));
    }

    @Override
    public String getId()
    {
        return "sandwiches";
    }

    @Override
    public String getUnlocalizedTitle()
    {
        return "item.cooking_book.pages.sandwiches.title";
    }
}
