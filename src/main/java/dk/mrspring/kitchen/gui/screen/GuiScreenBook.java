package dk.mrspring.kitchen.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class GuiScreenBook extends GuiScreen
{
    public static int drawSplitCenteredString(FontRenderer renderer, String s, int x, int y, int maxLength, int color, boolean shadow)
    {
        List<String> lines = renderer.listFormattedStringToWidth(s, maxLength);

        for (int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);
            int lineWidth = renderer.getStringWidth(line);
            renderer.drawString(line, x - (lineWidth / 2), y + (i * 9), color, shadow);
        }

        return lines.size();
    }

    List<Page> pages = new ArrayList<Page>();

    public static void initPages()
    {

    }

    private interface Page
    {
        public void draw(Minecraft minecraft, int width);
    }

    @Override
    public void initGui()
    {
        super.initGui();

        final String introduction = StatCollector.translateToLocal("item.cooking_book.pages.introduction").replace("###", "\n");
        final List introLines = mc.fontRenderer.listFormattedStringToWidth(introduction, 120);

        pages.add(new Page()
        {
            List<String> lines = new ArrayList<String>();

            {
                for (int i = 0; i < introLines.size(); i++)
                {
                    if (i >= 14)
                        break;
                    Object lineFromIntro = introLines.get(i);
                    if (lineFromIntro instanceof String)
                        this.lines.add((String) lineFromIntro);
                }
            }

            @Override
            public void draw(Minecraft minecraft, int width)
            {
                GL11.glPushMatrix();
                GL11.glScalef(1.5F, 1.5F, 1.5F);
                minecraft.fontRenderer.drawString("Introduction", width / 3 - (minecraft.fontRenderer.getStringWidth("Introduction") / 2), 10, 0x4C1C06, false);
                GL11.glPopMatrix();
                for (int i = 0; i < lines.size(); i++)
                {
                    String line = lines.get(i);
                    int lineWidth = minecraft.fontRenderer.getStringWidth(line);
                    minecraft.fontRenderer.drawString(line, width / 2 - (lineWidth / 2), 40 + (i * 9), 0x4C1C06, false);
                }
            }
        });
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float p_73863_3_)
    {
        super.drawScreen(mouseX, mouseY, p_73863_3_);

        mc.getTextureManager().bindTexture(new ResourceLocation("kitchen:textures/gui/cooking_book.png"));
        drawTexturedModalRect((width / 2) - 140, 20, 0, 0, 140, 180);
        drawTexturedModalRect((width / 2) - 140 + 14, 190, isMouseHovering(mouseX, mouseY, (width / 2) - 140 + 14, 190, 24, 24) ? 24 : 0, 180, 24, 24);
        mc.getTextureManager().bindTexture(new ResourceLocation("kitchen:textures/gui/cooking_book_right.png"));
        drawTexturedModalRect(width / 2, 20, 0, 0, 140, 180);
        drawTexturedModalRect((width / 2) + 140 - 38, 190, isMouseHovering(mouseX, mouseY, (width / 2) + 140 - 38, 190, 24, 24) ? 24 : 0, 180, 24, 24);
        mc.getTextureManager().bindTexture(new ResourceLocation("kitchen:textures/gui/cooking_book.png"));

        GL11.glPushMatrix();

        GL11.glTranslatef((width / 2) - 127, 20, 0);

        pages.get(0).draw(mc, 120);

        GL11.glPopMatrix();

//        System.out.println("Done Rendering!");
    }

    static boolean isMouseHovering(int mouseX, int mouseY, int posX, int posY, int width, int height)
    {
        return mouseX >= posX && mouseY >= posY && mouseX < posX + width && mouseY < posY + height;
    }
}
