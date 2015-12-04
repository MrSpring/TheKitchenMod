package dk.mrspring.kitchen.gui.screen.book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

/**
 * Created on 11-09-2015 for TheKitchenMod.
 */
public class GuiNoRenderButton extends GuiButton
{
    public GuiNoRenderButton(int id, int x, int y, int width, int height, String text)
    {
        super(id, x, y, width, height, text);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
    }
}
