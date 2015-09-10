package dk.mrspring.kitchen.api.book;

import net.minecraft.client.Minecraft;

/**
 * Created on 09-09-2015 for TheKitchenMod.
 */
public interface IPageElementContainer
{
    int getAvailableWidth();

    int getAvailableHeight();

    int getCurrentElementId();

    IChapter getChapter();

    Minecraft getMinecraft();
}
