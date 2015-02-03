package dk.mrspring.kitchen.gui.screen;

import dk.mrspring.kitchen.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class GuiScreenBook extends GuiScreen
{
    private interface Page
    {
        public void draw(Minecraft minecraft, int maxWidth, int mouseX, int mouseY);
    }

    private class SimplePage implements Page
    {
        List<Element> elements;

        public SimplePage(Element element)
        {
            this.elements = new ArrayList<Element>();
            this.elements.add(element);
        }

        public SimplePage(List<Element> elements)
        {
            this.elements = new ArrayList<Element>(elements);
        }

        @Override
        public void draw(Minecraft minecraft, int maxWidth, int mouseX, int mouseY)
        {
            GL11.glPushMatrix();

            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glEnable(GL11.GL_LIGHTING);

            int relativeMouseY = mouseY;

            for (Element element : elements)
            {
                element.draw(minecraft, maxWidth, mouseX, relativeMouseY);
                int height = element.getHeight(minecraft, maxWidth);
                relativeMouseY += height;
                GL11.glTranslatef(0, height, 0);
            }
            GL11.glPopMatrix();
        }
    }

    private interface Splittable
    {
        /**
         * Splits the element into to parts, one in the specified height, and the other the remains.
         *
         * @param toHeight The height if which the first element is being capped to.
         * @return Returns an array of 2 elements.
         */
        public Element[] split(int toHeight);
    }

    private interface Element
    {
        public void draw(Minecraft minecraft, int maxWidth, int mouseX, int mouseY);

        public int getHeight(Minecraft minecraft, int maxWidth);
    }

    private class ImageElement implements Element
    {
        ResourceLocation location;
        int u, v;
        int width, height;

        public ImageElement(ResourceLocation image, int u, int v, int width, int height)
        {
            this.location = image;
            this.u = u;
            this.v = v;
            this.width = width;
            this.height = height;
        }

        @Override
        public void draw(Minecraft minecraft, int maxWidth, int mouseX, int mouseY)
        {
            GL11.glPushMatrix();
            GL11.glColor4f(1, 1, 1, 1);
            minecraft.getTextureManager().bindTexture(location);
            int x = (maxWidth / 2) - (width / 2);
            drawTexturedModalRect(x, 2, u, v, width, height);
//            drawRect(0, 0, width, height, 0xFFFFFF);
//            minecraft.fontRenderer.drawString("IMAGE", 0, 0, 0x4C1C06);
            GL11.glPopMatrix();
        }

        @Override
        public int getHeight(Minecraft minecraft, int maxWidth)
        {
            return height + 4;
        }
    }

    private class SpacerElement implements Element, Splittable
    {
        int height = 0;

        public SpacerElement(int height)
        {
            this.height = height;
        }

        @Override
        public void draw(Minecraft minecraft, int maxWidth, int mouseX, int mouseY)
        {
        }

        @Override
        public int getHeight(Minecraft minecraft, int maxWidth)
        {
            return this.height;
        }

        @Override
        public Element[] split(int toHeight)
        {
            return new Element[]{
                    new SpacerElement(Math.min(this.height, toHeight)),
                    new SpacerElement(0)
            };
        }
    }

    private class TextElement implements Element, Splittable
    {
        List<String> lines;
        boolean center = false;

        public TextElement(String text)
        {
            lines = mc.fontRenderer.listFormattedStringToWidth(text, 120);
        }

        public TextElement(List<String> lines)
        {
//            this.lines = new ArrayList<String>(lines);
            this.lines = new ArrayList<String>();
            for (String line : lines)
                this.lines.addAll(mc.fontRenderer.listFormattedStringToWidth(line, 120));
        }

        public TextElement center()
        {
            this.center = true;
            return this;
        }

        public TextElement setCenter(boolean center)
        {
            this.center = center;
            return this;
        }

        @Override
        public void draw(Minecraft minecraft, int maxWidth, int mouseX, int mouseY)
        {
            for (int i = 0; i < this.lines.size(); i++)
            {
                String line = this.lines.get(i);
                int textX = 3;
                if (center)
                    textX += maxWidth / 2 - (minecraft.fontRenderer.getStringWidth(line) / 2);//int lineWidth = minecraft.fontRenderer.getStringWidth(line);

                minecraft.fontRenderer.drawString(line, textX, (i * 9), 0x4C1C06, false);
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
        public Element[] split(int toHeight)
        {
            int linesWithinHeight = 0;
            for (int i = 1; i < lines.size(); i++)
            {
                if (i * 9 > toHeight)
                    break;
                else linesWithinHeight = i;
            }

            List<String> linesInOne = lines.subList(0, linesWithinHeight);
            List<String> linesInTwo = lines.subList(linesWithinHeight, lines.size());

//            System.out.println("Lines in first element: ");

//            for (String line : linesInOne)
//                System.out.println(line);

//            System.out.println("Lines in second element: ");

//            for (String line : linesInTwo)
//                System.out.println(line);

            return new Element[]{
                    new TextElement(linesInOne).setCenter(center),
                    new TextElement(linesInTwo).setCenter(center)
            };
        }
    }

    private class Recipe
    {
        public ItemStack[] input;
        public ItemStack output;

        public Recipe(ItemStack output, ItemStack... input)
        {
            this.input = input;
            this.output = output;
        }
    }

    private class CraftingElement implements Element
    {
        Recipe recipe;

        public CraftingElement(ItemStack output, List<ItemStack> input)
        {
            this(output, input.toArray(new ItemStack[input.size()]));
        }

        public CraftingElement(ItemStack output, ItemStack... input)
        {
            this(new Recipe(output, input));
        }

        public CraftingElement(Recipe recipe)
        {
            this.recipe = recipe;
        }

        @Override
        public void draw(Minecraft minecraft, int maxWidth, int mouseX, int mouseY)
        {
            GL11.glPushMatrix();

            final int X_OFFSET = (maxWidth - 97) / 2;

            GL11.glColor4f(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(new ResourceLocation("kitchen", "textures/gui/cooking_book.png"));
            drawTexturedModalRect(X_OFFSET, 0, 0, 0, 97, 62);

            for (int i = 0; i < recipe.input.length; i++)
            {
                ItemStack stack = recipe.input[i];
                if (stack != null)
                {
                    GL11.glPushMatrix();

                    int x = 0, y = 0;
                    switch (i)
                    {
                        case 0:
                            x = 0;
                            y = 0;
                            break;
                        case 1:
                            x = 1;
                            y = 0;
                            break;
                        case 2:
                            x = 2;
                            y = 0;
                            break;
                        case 3:
                            x = 0;
                            y = 1;
                            break;
                        case 4:
                            x = 1;
                            y = 1;
                            break;
                        case 5:
                            x = 2;
                            y = 1;
                            break;
                        case 6:
                            x = 0;
                            y = 2;
                            break;
                        case 7:
                            x = 1;
                            y = 2;
                            break;
                        case 8:      // TODO: Clean up code
                            x = 2;   // TODO: Support 2x2 and 3x3 recipes
                            y = 2;   // TODO: Fix relative mouse problems
                            break;
                    }

                    itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), stack, x * 16 + 7 + X_OFFSET, y * 16 + 7);

                    GL11.glPopMatrix();
                    GL11.glColor4f(1, 1, 1, 1);
                }
            }

            itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), recipe.output, 74 + X_OFFSET, 23);

            GL11.glColor4f(1, 1, 1, 1);
            GL11.glPopMatrix();
        }

        @Override
        public int getHeight(Minecraft minecraft, int maxWidth)
        {
            return 62;
        }
    }


    int pageSet = 0;
    List<Page> pages;
    int[] pageIndex;

    private void evenOutPages()
    {
        if (pages.size() % 2 != 0)
            pages.add(new SimplePage(new ArrayList<Element>()));
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.pages = new ArrayList<Page>();

        this.addIntroPages();
        if (ModConfig.getKitchenConfig().show_mod_repost_info)
            this.addModRepostInfo();

        this.evenOutPages();

        this.addTableOfContent();

        pageIndex = new int[]{
                this.addChapter("item.cooking_book.pages.sandwiches.title", 0, 99, 0,
                        START_TEXT,
                        "item.cooking_book.pages.sandwiches.text01",
                        STOP_TEXT,
                        START_CRAFTING,
                        new ItemStack(KitchenItems.knife),
                        Items.iron_ingot, null, null,
                        null, Items.stick,
                        STOP_CRAFTING,
                        ADD_SPACE, 6,
                        START_TEXT,
                        "item.cooking_book.pages.sandwiches.text02",
                        STOP_TEXT,
                        ADD_SPACE, 9,
                        START_TEXT,
                        "item.cooking_book.pages.sandwiches.text03",
                        STOP_TEXT,
                        START_CRAFTING,
                        new ItemStack(KitchenBlocks.board),
                        Blocks.wooden_slab,
                        Blocks.wooden_pressure_plate,
                        Blocks.wooden_slab,
                        STOP_CRAFTING,
                        START_TEXT,
                        CENTER_TEXT,
                        "item.cooking_book.pages.sandwiches.text04",
                        STOP_TEXT),

                this.addChapter("item.cooking_book.pages.oven.title", 0, 0, 62,
                        START_TEXT,
                        "item.cooking_book.pages.oven.text01",
                        STOP_TEXT,
                        START_CRAFTING,
                        new ItemStack(KitchenBlocks.oven),
                        Items.iron_ingot, Items.iron_ingot, Items.iron_ingot,
                        Items.iron_ingot, Items.coal, Items.iron_ingot,
                        Items.iron_ingot, Items.flint_and_steel, Items.iron_ingot,
                        STOP_CRAFTING,
                        ADD_SPACE, 10,
                        START_TEXT,
                        "item.cooking_book.pages.oven.text02",
                        STOP_TEXT,
                        START_IMAGE,
                        "kitchen:textures/gui/cooking_book.png",
                        0,
                        161,
                        99,
                        61,
                        STOP_IMAGE,
                        START_TEXT,
                        "item.cooking_book.pages.oven.text03",
                        STOP_TEXT,
                        ADD_SPACE, 5,
                        START_TEXT,
                        "item.cooking_book.pages.oven.text04",
                        STOP_TEXT,
                        START_IMAGE,
                        "kitchen:textures/gui/cooking_book.png",
                        99, 198,
                        99, 58,
                        STOP_IMAGE,
                        START_TEXT,
                        "item.cooking_book.pages.oven.text05",
                        STOP_TEXT,
                        START_IMAGE,
                        "kitchen:textures/gui/cooking_book_1.png",
                        99, 99,
                        99, 61,
                        STOP_IMAGE,
                        START_TEXT,
                        "item.cooking_book.pages.oven.text06",
                        STOP_TEXT),

                this.addChapter("item.cooking_book.pages.pan.title", 0, 99, 99,
                        START_TEXT,
                        "item.cooking_book.pages.pan.text01",
                        STOP_TEXT,
                        START_CRAFTING,
                        new ItemStack(KitchenBlocks.frying_pan),
                        Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, Items.iron_ingot,
                        STOP_CRAFTING),

                this.addChapter("item.cooking_book.pages.waffle.title", 1, 0, 0,
                        START_TEXT,
                        "item.cooking_book.pages.waffle.text01",
                        STOP_TEXT),

                this.addChapter("item.cooking_book.pages.toast.title", 1, 0, 99,
                        START_TEXT,
                        "item.cooking_book.pages.toast.text01",
                        STOP_TEXT),

                this.addChapter("item.cooking_book.pages.timer.title", 1, 99, 0,
                        START_TEXT,
                        "item.cooking_book.pages.timer.text01",
                        STOP_TEXT)};
    }

    private String translate(String text)
    {
        return StatCollector.translateToLocal(text).replace("\\n", "\n");
    }

    private List<String> translateList(List<String> lines)
    {
        List<String> translated = new ArrayList<String>();
        for (String line : lines)
            translated.add(translate(line));
        return translated;
    }

    private static final String START_CRAFTING = "%C_START%";
    private static final String STOP_CRAFTING = "%C_STOP%";

    private static final String START_TEXT = "%T_START%";
    private static final String STOP_TEXT = "%T_STOP%";
    private static final String CENTER_TEXT = "%T_CENTER%";
    private static final String DONT_TRANSLATE_TEXT = "%T_DT%";

    private static final String START_IMAGE = "%I_START%";
    private static final String STOP_IMAGE = "%I_STOP%";

    private static final String ADD_SPACE = "%SPACE%";

    private int addChapter(String title, int textureIndex, int logoU, int logoV, Object... content)
    {
        this.evenOutPages();

        int start = this.pages.size() + 1;

        List<Element> elements = new ArrayList<Element>();
        elements.add(new TextElement(translate(title)).center());
        elements.add(new ImageElement(new ResourceLocation("kitchen", "textures/gui/cooking_book" + (textureIndex != 0 ? "_" + textureIndex : "") + ".png"), logoU, logoV, 99, 99));

        List contentList = Arrays.asList(content);

        Iterator iterator = contentList.listIterator();

        while (iterator.hasNext())
        {
            Object object = iterator.next();

            if (object instanceof String)
            {
                String objectAsString = (String) object;
                if (objectAsString.equals(START_CRAFTING))
                {
                    List<ItemStack> input = new ArrayList<ItemStack>();
                    ItemStack output = (ItemStack) iterator.next();

                    while (iterator.hasNext())
                    {
                        Object inputObject = iterator.next();
                        if (inputObject instanceof ItemStack)
                            input.add((ItemStack) inputObject);
                        else if (inputObject instanceof Item)
                            input.add(new ItemStack((Item) inputObject));
                        else if (inputObject instanceof Block)
                            input.add(new ItemStack((Block) inputObject));
                        else if (inputObject == null)
                            input.add(null);
                        else if (inputObject instanceof String)
                            if (inputObject.equals(STOP_CRAFTING))
                                break;
                    }

                    elements.add(new CraftingElement(output, input));
                } else if (objectAsString.equals(START_TEXT))
                {
                    List<String> lines = new ArrayList<String>();
                    boolean center = false, translate = true;

                    while (iterator.hasNext())
                    {
                        Object lineObject = iterator.next();
                        if (lineObject instanceof String)
                        {
                            if (lineObject.equals(STOP_TEXT))
                                break;
                            else if (lineObject.equals(CENTER_TEXT))
                                center = true;
                            else if (lineObject.equals(DONT_TRANSLATE_TEXT))
                                translate = false;
                            else lines.add((String) lineObject);
                        }
                    }
                    if (translate)
                        lines = translateList(lines);
                    elements.add(new TextElement(lines).setCenter(center));
                } else if (objectAsString.equals(START_IMAGE))
                {
                    ResourceLocation imageLocation = null;
                    Integer u = null, v = null, width = null, height = null;

                    while (iterator.hasNext())
                    {
                        Object imageObject = iterator.next();

                        if (imageObject instanceof Integer)
                        {
                            if (u == null)
                                u = (Integer) imageObject;
                            else if (v == null)
                                v = (Integer) imageObject;
                            else if (width == null)
                                width = (Integer) imageObject;
                            else if (height == null)
                                height = (Integer) imageObject;
                        } else if (imageObject instanceof ResourceLocation)
                            imageLocation = (ResourceLocation) imageObject;
                        else if (imageObject instanceof String)
                        {
                            if (imageObject.equals(STOP_IMAGE))
                                break;
                            else
                            {
                                String imageObjectAsString = (String) imageObject;
                                if (imageObjectAsString.contains(":"))
                                    imageLocation = new ResourceLocation(imageObjectAsString);
                                else imageLocation = new ResourceLocation("kitchen", imageObjectAsString);
                            }
                        }
                    }

                    if (imageLocation != null && u != null && v != null && width != null && height != null)
                        elements.add(new ImageElement(imageLocation, u, v, width, height));
                } else if (objectAsString.equals(ADD_SPACE))
                {
                    Object height = iterator.next();
                    if (height instanceof Integer)
                        elements.add(new SpacerElement((Integer) height));
                }
            }
        }

        this.pages.addAll(this.splitElementsToPages(elements, mc, 120));

        return start;
    }

    private void addTableOfContent()
    {
        final String[] content = new String[]{"sandwich", "oven", "pan", "waffle", "toast", "timer"};

        List<Element> elements = new ArrayList<Element>();
        elements.add(new TextElement(translate("item.cooking_book.pages.contents.title") + "\n").center());
        elements.add(new Element()
        {
            @Override
            public void draw(Minecraft minecraft, int maxWidth, int mouseX, int mouseY)
            {
                final int COLOR = 0x4C1C06;

                for (int i = 0; i < content.length; i++)
                {
                    String name = translate("item.cooking_book.pages.contents." + content[i]), value = String.valueOf(pageIndex[i]);
                    minecraft.fontRenderer.drawString(name, 4, i * 9, COLOR);
                    int valueWidth = minecraft.fontRenderer.getStringWidth(value);
                    minecraft.fontRenderer.drawString(value, maxWidth - valueWidth - 4, i * 9, COLOR);
                }
            }

            @Override
            public int getHeight(Minecraft minecraft, int maxWidth)
            {
                return 0;
            }
        });
        this.pages.add(new SimplePage(elements));
    }

    private void addModRepostInfo()
    {
        List<String> lines = new ArrayList<String>();

        lines.add(translate("item.cooking_book.pages.stop_mod_reposts.title"));
        lines.add("");
        lines.add(translate("item.cooking_book.pages.stop_mod_reposts.text"));

        List<Element> elements = new ArrayList<Element>();
        elements.add(new TextElement(lines).center());

        pages.addAll(splitElementsToPages(elements, mc, 120));
    }

    private void addIntroPages()
    {
        List<String> lines = new ArrayList<String>();
        lines.add(translate("item.cooking_book.pages.introduction").replace("%v", ModInfo.version) + ".");
        if (!Kitchen.proxy.versionHighlights.equals(""))
        {
            lines.add("\n\n" + translate("item.cooking_book.pages.update_highlights"));
            lines.add(Kitchen.proxy.versionHighlights);
        }

        List<Element> introElements = new ArrayList<Element>();

        introElements.add(new Element()
        {
            @Override
            public void draw(Minecraft minecraft, int maxWidth, int mouseX, int mouseY)
            {
                GL11.glPushMatrix();
                GL11.glScalef(1.5F, 1.5F, 1.5F);
                minecraft.fontRenderer.drawString(translate("item.cooking_book.pages.introduction.title"), maxWidth / 3 - (minecraft.fontRenderer.getStringWidth(translate("item.cooking_book.pages.introduction.title")) / 2), 0, 0x4C1C06, false);
                GL11.glPopMatrix();
            }

            @Override
            public int getHeight(Minecraft minecraft, int maxWidth)
            {
                return 15;
            }
        });

        introElements.add(new TextElement(lines).center());

        pages.addAll(splitElementsToPages(introElements, mc, 120));
    }

    private List<Page> splitElementsToPages(List<Element> elements, Minecraft minecraft, int width)
    {
        List<Page> localPages = new ArrayList<Page>();

        List<Element> pageTemp = new ArrayList<Element>();
        int pageHeight = 0;

        final int MAX_PAGE_HEIGHT = 16 * 9;
        for (Element element : elements)
        {
            int elementHeight = element.getHeight(minecraft, width);
            if (pageHeight + elementHeight > MAX_PAGE_HEIGHT)
            {
                if (element instanceof Splittable)
                {
                    Element[] splitElement = ((Splittable) element).split(MAX_PAGE_HEIGHT - pageHeight);
                    pageTemp.add(splitElement[0]);
                    localPages.add(new SimplePage(new ArrayList<Element>(pageTemp)));
                    pageTemp.clear();
                    pageHeight = 0;
                    while (splitElement[1].getHeight(minecraft, width) > MAX_PAGE_HEIGHT)
                    {
                        splitElement = ((Splittable) splitElement[1]).split(MAX_PAGE_HEIGHT);
                        pageTemp.add(splitElement[0]);
                        localPages.add(new SimplePage(new ArrayList<Element>(pageTemp)));
                        pageTemp.clear();
                        pageHeight = 0;
                    }
                    pageTemp.add(splitElement[1]);
                    pageHeight += splitElement[1].getHeight(minecraft, width);
                } else
                {
                    localPages.add(new SimplePage(new ArrayList<Element>(pageTemp)));
                    pageTemp.clear();
                    pageHeight = 0;
                    pageTemp.add(element);
                }
            } else
            {
                pageHeight += elementHeight;
                pageTemp.add(element);
            }
        }

        if (pageTemp.size() > 0)
            localPages.add(new SimplePage(new ArrayList<Element>(pageTemp)));

//        System.out.println("localPages.size() = " + localPages.size());
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
        if (pageSet > 0)
            drawTexturedModalRect((width / 2) - 140 + 14, 190, isMouseHovering(mouseX, mouseY, (width / 2) - 140 + 14, 190, 24, 24) ? 24 : 0, 180, 24, 24);
        mc.getTextureManager().bindTexture(new ResourceLocation("kitchen", "textures/gui/cooking_book_right.png"));
        drawTexturedModalRect(width / 2, 20, 0, 0, 140, 180);
        if ((pageSet + 1) * 2 < pages.size())
            drawTexturedModalRect((width / 2) + 140 - 38, 190, isMouseHovering(mouseX, mouseY, (width / 2) + 140 - 38, 190, 24, 24) ? 24 : 0, 180, 24, 24);


        if (leftPage != null)
        {
            mc.fontRenderer.drawString(String.valueOf(pageSet * 2 + 1), (width / 2) - 70, 182, 0x4C1C06, false);
            GL11.glPushMatrix();
            GL11.glTranslatef((width / 2) - 130, 35, 0);
            int relativeMouseX = mouseX - ((width / 2) - 127);
            int relativeMouseY = mouseY - 35;
            leftPage.draw(mc, 120, relativeMouseX, relativeMouseY);
            GL11.glPopMatrix();
        }
        if (rightPage != null)
        {
            mc.fontRenderer.drawString(String.valueOf(pageSet * 2 + 2), (width / 2) + 70, 182, 0x4C1C06, false);
            GL11.glPushMatrix();
            GL11.glTranslatef((width / 2) + 6, 35, 0);
            int relativeMouseX = mouseX - (width / 2) + 7;
            int relativeMouseY = mouseY - 35;

            rightPage.draw(mc, 120, relativeMouseX, relativeMouseY);
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

        if (ModConfig.getKitchenConfig().show_mod_repost_info)
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
