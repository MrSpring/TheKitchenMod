package dk.mrspring.kitchen.api.book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;

import java.util.List;

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

    RenderItem getRenderItem();

    IBook getGui();

    void drawHoverTextAtMouse(List text, FontRenderer renderer);

    void drawHoverText(List text, int x, int y, FontRenderer renderer);
}
