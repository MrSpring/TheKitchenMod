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
public class CuttingBoardHandler implements IChapterHandler
{
    public static String ID = "cuttingboard";

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
        chapter.addElement(new SpacerElement(3).disableImage());
        chapter.addElement(new TextElement("Craft a Cutting Board, like so, to unlock this chapter!", true));
        chapter.addElement(new SpacerElement(20));
        chapter.addElement(new RecipeElement(new ItemStack(KitchenBlocks.board)));
//        chapter.addElement(new TextElement("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin molestie libero non arcu semper mollis. In elementum lectus in dapibus elementum. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Pellentesque venenatis ante eu tincidunt pulvinar. Ut neque odio, mattis ut tortor vel, bibendum pellentesque ipsum. Etiam euismod ipsum augue, quis viverra tellus dignissim id. Duis euismod, mi eget feugiat aliquet, purus orci tempus nisi, sed sagittis nunc dolor vel sapien. Donec gravida commodo dictum. Curabitur ac tortor pellentesque, pretium magna sed, tincidunt risus. In non orci mollis, consequat velit ut, blandit leo.", true));
//        chapter.addElement(new SpacerElement(15));
//        chapter.addElement(new TextElement("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin molestie libero non arcu semper mollis. In elementum lectus in dapibus elementum. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Pellentesque venenatis ante eu tincidunt pulvinar.", true));
//        chapter.addElement(new ImageElement(ModInfo.toResource("textures/gui/cooking_book.png"), 0, 161, 99, 61));
    }

    private void addTitle(IChapter chapter)
    {
        chapter.addElement(new TitleElement("Sandwiches"));
    }

    @Override
    public String getId()
    {
        return ID;
    }

    @Override
    public String getUnlocalizedTitle()
    {
        return "item.cooking_book.pages.sandwiches.title";
    }
}
