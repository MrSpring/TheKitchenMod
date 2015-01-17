package dk.mrspring.kitchen.gui.screen;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
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

    int pageSet = 0;

    @Override
    public void initGui()
    {
        super.initGui();

        if (pages != null)
            pages.clear();
        else pages = new ArrayList<Page>();

        this.addIntroPages();

        List<String> tempList = new ArrayList<String>();
        String tempString = StatCollector.translateToLocal("item.cooking_book.pages.stop_mod_reposts.text");
        tempList.add(StatCollector.translateToLocal("item.cooking_book.pages.stop_mod_reposts.title"));
        tempList.addAll(mc.fontRenderer.listFormattedStringToWidth(tempString, 120));
        List<Page> tempPageList = this.getPagesFromLines(tempList);
        this.pages.addAll(tempPageList);
        tempList.clear();
        tempList.add("Thanks for reading!");
        tempList.add("Mod made by Mr. Spring");
        this.pages.add(new TextPage(tempList));
    }

    private List<Page> getPagesFromLines(List<String> lines)
    {
        List<Page> pages = new ArrayList<Page>();
        List<String> pageTemp = new ArrayList<String>();

        for (String line : lines)
        {
            System.out.println("Going through line: " + line + ", current pages: " + pages.size() + ", current page size: " + pageTemp.size());
            pageTemp.add(line);
            if (pageTemp.size() > 17)
            {
                pages.add(new TextPage(pageTemp));
                pageTemp.clear();
            }
        }
        return pages;
    }

    private void addIntroPages()
    {
        String introduction = StatCollector.translateToLocal("item.cooking_book.pages.introduction").replace("###", "\n").replace("%v", ModInfo.version) + ".";

        if (!Kitchen.proxy.versionHighlights.equals(""))
        {
            introduction += "\n\n" + StatCollector.translateToLocal("item.cooking_book.pages.update_highlights") + "\n\n";
            introduction += Kitchen.proxy.versionHighlights;
        }

        List<String> introLines = mc.fontRenderer.listFormattedStringToWidth(introduction, 120);

        final List<String> pageOneLines = introLines;

        pages.add(new Page()
        {
            List<String> lines;

            {
                if (pageOneLines.size() > 13)
                {
                    this.lines = pageOneLines.subList(0, 13);
                } else this.lines = pageOneLines;
            }

            @Override
            public void draw(Minecraft minecraft, int width)
            {
                GL11.glPushMatrix();
                GL11.glScalef(1.5F, 1.5F, 1.5F);
                minecraft.fontRenderer.drawString("Introduction", width / 3 - (minecraft.fontRenderer.getStringWidth("Introduction") / 2), 0, 0x4C1C06, false);
                GL11.glPopMatrix();
                for (int i = 0; i < lines.size(); i++)
                {
                    String line = lines.get(i);
                    int lineWidth = minecraft.fontRenderer.getStringWidth(line);
                    minecraft.fontRenderer.drawString(line, width / 2 - (lineWidth / 2), 20 + (i * 9), 0x4C1C06, false);
                }
            }
        });

        if (introLines.size() > 13)
            this.pages.add(new TextPage(introLines.subList(14, introLines.size())).center());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float p_73863_3_)
    {
        super.drawScreen(mouseX, mouseY, p_73863_3_);

        Page leftPage = null, rightPage = null;

        leftPage = pages.get(pageSet * 2);
        if (pages.size() > pageSet * 2 + 1)
            rightPage = pages.get(pageSet * 2 + 1);

        mc.getTextureManager().bindTexture(new ResourceLocation("kitchen", "textures/gui/cooking_book_left.png"));
        drawTexturedModalRect((width / 2) - 140, 20, 0, 0, 140, 180);
        drawTexturedModalRect((width / 2) - 140 + 14, 190, isMouseHovering(mouseX, mouseY, (width / 2) - 140 + 14, 190, 24, 24) ? 24 : 0, 180, 24, 24);
        mc.getTextureManager().bindTexture(new ResourceLocation("kitchen", "textures/gui/cooking_book_right.png"));
        drawTexturedModalRect(width / 2, 20, 0, 0, 140, 180);
        drawTexturedModalRect((width / 2) + 140 - 38, 190, isMouseHovering(mouseX, mouseY, (width / 2) + 140 - 38, 190, 24, 24) ? 24 : 0, 180, 24, 24);


        if (leftPage != null)
        {
            mc.fontRenderer.drawString(String.valueOf(pageSet * 2 + 1), (width / 2) - 70, 181, 0x4C1C06, false);
            GL11.glPushMatrix();
            GL11.glTranslatef((width / 2) - 127, 35, 0);
            leftPage.draw(mc, 120);
            GL11.glPopMatrix();
        }
        if (rightPage != null)
        {
            mc.fontRenderer.drawString(String.valueOf(pageSet * 2 + 2), (width / 2) + 70, 181, 0x4C1C06, false);
            GL11.glPushMatrix();
            GL11.glTranslatef((width / 2) + 7, 35, 0);
            rightPage.draw(mc, 120);
            GL11.glPopMatrix();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        if (isMouseHovering(mouseX, mouseY, (width / 2) + 140 - 38, 190, 24, 24) && (pageSet + 1) * 2 < pages.size())
            this.pageSet++;
        else if (isMouseHovering(mouseX, mouseY, (width / 2) - 140 + 14, 190, 24, 24) && pageSet > 0)
            this.pageSet--;
    }

    static boolean isMouseHovering(int mouseX, int mouseY, int posX, int posY, int width, int height)
    {
        return mouseX >= posX && mouseY >= posY && mouseX < posX + width && mouseY < posY + height;
    }

    private interface Page
    {
        public void draw(Minecraft minecraft, int width);
    }

    private class TextPage implements Page
    {
        List<String> lines;
        boolean centered = false;

        public TextPage(List<String> lines)
        {
            this.lines = lines;
        }

        public TextPage center()
        {
            this.centered = true;
            return this;
        }

        @Override
        public void draw(Minecraft minecraft, int width)
        {
            for (int i = 0; i < lines.size(); i++)
            {
                String line = lines.get(i);
                int lineWidth = minecraft.fontRenderer.getStringWidth(line);
                minecraft.fontRenderer.drawString(line, width / 2 - (lineWidth / 2), (i * 9), 0x4C1C06, false);
            }
        }
    }
}
