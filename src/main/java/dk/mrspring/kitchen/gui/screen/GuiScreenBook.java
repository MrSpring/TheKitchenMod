package dk.mrspring.kitchen.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class GuiScreenBook extends GuiScreen
{
    private interface Page
    {
        public void draw(Minecraft minecraft, int maxWidth);
    }

    private class SimplePage implements Page
    {
        List<Element> elements;

        public SimplePage(List<Element> elements)
        {
            this.elements = elements;
        }

        @Override
        public void draw(Minecraft minecraft, int maxWidth)
        {
            GL11.glPushMatrix();
            for (Element element : elements)
            {
                element.draw(minecraft, maxWidth);
                int height = element.getHeight(minecraft, maxWidth);
                GL11.glTranslatef(0, height, 0);
            }
            GL11.glPopMatrix();
        }
    }

    private interface Splittable
    {
        public List<Element> split(int toHeight);
    }

    private interface Element
    {
        public void draw(Minecraft minecraft, int maxWidth);

        public int getHeight(Minecraft minecraft, int maxWidth);
    }

    private class TextElement implements Element, Splittable
    {
        List<String> lines;

        public TextElement(String text)
        {
            lines = Arrays.asList(text.split("\n"));
        }

        public TextElement(List<String> lines)
        {
            this.lines = lines;
        }

        @Override
        public void draw(Minecraft minecraft, int maxWidth)
        {
            for (int i = 0; i < this.lines.size(); i++)
            {
                String line = this.lines.get(i);
                int lineWidth = minecraft.fontRenderer.getStringWidth(line);
                minecraft.fontRenderer.drawString(line, width / 2 - (lineWidth / 2), (i * 9), 0x4C1C06, false);
                // TODO: Center String
            }
        }

        @Override
        public int getHeight(Minecraft minecraft, int maxWidth)
        {
            int height = 0;
            for (String line : lines)
            {
                int wrappedLines = minecraft.fontRenderer.listFormattedStringToWidth(line, maxWidth).size();
                height += (wrappedLines * 9);
            }
            return height;
        }

        @Override
        public List<Element> split(int toHeight)
        {
            return null;
        }
    }


    int pageSet = 0;
    List<Page> pages;

    @Override
    public void initGui()
    {
        super.initGui();
        this.pages = new ArrayList<Page>();

        this.addIntroPages();
    }

    private void addIntroPages()
    {
        String introduction = StatCollector.translateToLocal("item.cooking_book.pages.introduction").replace("\\n", "\n").replace("%v", ModInfo.version) + ".";

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
            this.pages.add(new SimplePage(new TextElement(introLines.subList(14, introLines.size()))).center());
    }

    private List<Page> splitElementsToPages(List<Element> elements, Minecraft minecraft, int width)
    {
        List<Page> localPages = new ArrayList<Page>();

        List<Element> pageTemp = new ArrayList<Element>();
        int pageHeight = 0;

        final int MAX_PAGE_HEIGHT = 15 * 9;
        for (Iterator<Element> iterator = elements.iterator(); iterator.hasNext(); )
        {
            Element element = iterator.next();
            int elementHeight = element.getHeight(minecraft, width);
            if (pageHeight + elementHeight > MAX_PAGE_HEIGHT)
            {
                if (element instanceof Splittable)
                {
                    List<Element> splitElement = ((Splittable) element).split(MAX_PAGE_HEIGHT);
                } else
                {

                }
            } else pageHeight += elementHeight;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float p_73863_3_)
    {
        super.drawScreen(mouseX, mouseY, p_73863_3_);

        Page leftPage = null, rightPage = null;

        leftPage = pages.get(pageSet * 2);
        if (pages.size() > (pageSet * 2) + 1)
            rightPage = pages.get((pageSet * 2) + 1);

        mc.getTextureManager().bindTexture(new ResourceLocation("kitchen", "textures/gui/cooking_book_left.png"));
        drawTexturedModalRect((width / 2) - 140, 20, 0, 0, 140, 180);
        drawTexturedModalRect((width / 2) - 140 + 14, 190, isMouseHovering(mouseX, mouseY, (width / 2) - 140 + 14, 190, 24, 24) ? 24 : 0, 180, 24, 24);
        mc.getTextureManager().bindTexture(new ResourceLocation("kitchen", "textures/gui/cooking_book_right.png"));
        drawTexturedModalRect(width / 2, 20, 0, 0, 140, 180);
        drawTexturedModalRect((width / 2) + 140 - 38, 190, isMouseHovering(mouseX, mouseY, (width / 2) + 140 - 38, 190, 24, 24) ? 24 : 0, 180, 24, 24);


        if (leftPage != null)
        {
            mc.fontRenderer.drawString(String.valueOf(pageSet * 2 + 1), (width / 2) - 70, 182, 0x4C1C06, false);
            GL11.glPushMatrix();
            GL11.glTranslatef((width / 2) - 127, 35, 0);
            leftPage.draw(mc, 120);
            GL11.glPopMatrix();
        }
        if (rightPage != null)
        {
            mc.fontRenderer.drawString(String.valueOf(pageSet * 2 + 2), (width / 2) + 70, 182, 0x4C1C06, false);
            GL11.glPushMatrix();
            GL11.glTranslatef((width / 2) + 70, 35, 0);
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
    
    /*public static int drawSplitCenteredString(FontRenderer renderer, String s, int x, int y, int maxLength, int color, boolean shadow)
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

        if (ModConfig.getKitchenConfig().show_mod_repost_into)
            this.addModRepostPages();


    }

    private void addOtherPages()
    {
        Category sandwiches, oven, plate, pan, jam, toaster, waffleIron;


    }

    private Category getSandwichCategory()
    {
        Category category = new Category();

        StringBuilder builder = new StringBuilder();
        builder.append("§lSandwiches:§r\n");
        builder.append(StatCollector.translateToLocal(""));

        return category;
    }

    private void addModRepostPages()
    {
        List<String> stopModRepostsInfo = new ArrayList<String>();
        stopModRepostsInfo.add(StatCollector.translateToLocal("item.cooking_book.pages.stop_mod_reposts.title"));
        stopModRepostsInfo.addAll(mc.fontRenderer.listFormattedStringToWidth(StatCollector.translateToLocal("item.cooking_book.pages.stop_mod_reposts.text").replace("\\n", "\n"), 120));
        this.pages.addAll(getPagesFromLines(stopModRepostsInfo));
    }

    private void addIntroPages()
    {
        String introduction = StatCollector.translateToLocal("item.cooking_book.pages.introduction").replace("\\n", "\n").replace("%v", ModInfo.version) + ".";

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
            this.pages.add(new SimplePage(new TextPageElement(introLines.subList(14, introLines.size()))).center());
    }

    private List<Page> getPagesFromLines(List<String> lines)
    {
        List<Page> localPages = new ArrayList<Page>();
        List<String> pageTemp = new ArrayList<String>();

        for (String line : lines)
        {
            System.out.println("Going through line: " + line + ", current pages: " + pages.size() + ", current page size: " + pageTemp.size());
            pageTemp.add(line);
            if (pageTemp.size() > 15)
            {
                localPages.add(new SimplePage(new TextPageElement(new ArrayList<String>(pageTemp))));
                System.out.println("Added a new Page!");
                pageTemp.clear();
            }
        }
        if (pageTemp.size() > 0)
            localPages.add(new SimplePage(new TextPageElement(pageTemp)));
        return localPages;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float p_73863_3_)
    {
        super.drawScreen(mouseX, mouseY, p_73863_3_);

        Page leftPage = null, rightPage = null;

        leftPage = pages.get(pageSet * 2);
        if (pages.size() > (pageSet * 2) + 1)
            rightPage = pages.get((pageSet * 2) + 1);

        mc.getTextureManager().bindTexture(new ResourceLocation("kitchen", "textures/gui/cooking_book_left.png"));
        drawTexturedModalRect((width / 2) - 140, 20, 0, 0, 140, 180);
        drawTexturedModalRect((width / 2) - 140 + 14, 190, isMouseHovering(mouseX, mouseY, (width / 2) - 140 + 14, 190, 24, 24) ? 24 : 0, 180, 24, 24);
        mc.getTextureManager().bindTexture(new ResourceLocation("kitchen", "textures/gui/cooking_book_right.png"));
        drawTexturedModalRect(width / 2, 20, 0, 0, 140, 180);
        drawTexturedModalRect((width / 2) + 140 - 38, 190, isMouseHovering(mouseX, mouseY, (width / 2) + 140 - 38, 190, 24, 24) ? 24 : 0, 180, 24, 24);


        if (leftPage != null)
        {
            mc.fontRenderer.drawString(String.valueOf(pageSet * 2 + 1), (width / 2) - 70, 182, 0x4C1C06, false);
            GL11.glPushMatrix();
            GL11.glTranslatef((width / 2) - 127, 35, 0);
            leftPage.draw(mc, 120);
            GL11.glPopMatrix();
        }
        if (rightPage != null)
        {
            mc.fontRenderer.drawString(String.valueOf(pageSet * 2 + 2), (width / 2) + 70, 182, 0x4C1C06, false);
            GL11.glPushMatrix();
            GL11.glTranslatef(*//*(width / 2) + 7*//*0, 35, 0);
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

    private class Category
    {
        int startPage;
        List<Page> pages;

        public Category()
        {
            this(0, new ArrayList<Page>());
        }

        public Category(int start, List<Page> pages)
        {
            this.startPage = start;
            this.pages = pages;
        }

        public int getStartPage()
        {
            return this.startPage;
        }

        public int getEndPage()
        {
            return this.startPage + pages.size();
        }

        public List<Page> getPages()
        {
            return this.pages;
        }

        public void setStartPage(int startPage)
        {
            this.startPage = startPage;
        }

        public void setPages(List<Page> pages)
        {
            this.pages = pages;
        }

        public void addPage(Page page)
        {
            this.pages.add(page);
        }
    }

    private interface PageElement
    {
        public void draw(Minecraft minecraft, int maxWidth);

        public int getHeight(Minecraft minecraft, int maxWidth);
    }

    private class TextPageElement implements PageElement
    {
        List<String> lines;
        public boolean center;

        public TextPageElement(List<String> lines)
        {
            this.lines = lines;
        }

        @Override
        public void draw(Minecraft minecraft, int maxWidth)
        {
            for (int i = 0; i < this.lines.size(); i++)
            {
                String line = this.lines.get(i);
                int lineWidth = minecraft.fontRenderer.getStringWidth(line);
                minecraft.fontRenderer.drawString(line, width / 2 - (lineWidth / 2), (i * 9), 0x4C1C06, false);
                // TODO: Center String if (center);
            }
        }

        @Override
        public int getHeight(Minecraft minecraft, int maxWidth)
        {
            return lines.size() * 9;
        }
    }

    private interface Page
    {
        public void draw(Minecraft minecraft, int width);
    }

    private class SimplePage implements Page
    {
        List<PageElement> lines;

        public SimplePage(PageElement element)
        {
            List<PageElement> elements = new ArrayList<PageElement>();
            elements.add(element);
            this.lines = elements;
        }

        public SimplePage(List<PageElement> lines)
        {
            this.lines = lines;

//            System.out.println("Creating new Text Page containing these lines: ");
//            for (String line : lines)
//                System.out.println(line);
        }

        public SimplePage center()
        {
            for (PageElement element : lines)
                if (element instanceof TextPageElement)
                    ((TextPageElement) element).center = true;
            return this;
        }

        @Override
        public void draw(Minecraft minecraft, int width)
        {
            GL11.glPushMatrix();
            for (PageElement element : lines)
            {
                element.draw(minecraft, width);
                int height = element.getHeight(minecraft, width);
//                GL11.glTranslatef(0, height, 0);
            }
            GL11.glPopMatrix();
        }
    }*/
}
