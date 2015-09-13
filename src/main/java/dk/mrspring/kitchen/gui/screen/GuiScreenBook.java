
package dk.mrspring.kitchen.gui.screen;

import dk.mrspring.kitchen.api.book.*;
import dk.mrspring.kitchen.api_impl.client.book.CookingBookRegistry;
import dk.mrspring.kitchen.entity.CookingBookUnlocksProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.*;


/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */

public class GuiScreenBook extends GuiScreen implements IBook
{
    final int PAGE_WIDTH = 140, BOOK_WIDTH = PAGE_WIDTH * 2, BOOK_HEIGHT = 180;
    final int BUTTON_SIZE = 24;
    final int LEFT_PADDING = 16, RIGHT_PADDING = 12, TOP_PADDING = 13, BOTTOM_PADDING = 20;
    final ResourceLocation LEFT = new ResourceLocation("kitchen", "textures/gui/cooking_book_left.png");
    final ResourceLocation RIGHT = new ResourceLocation("kitchen", "textures/gui/cooking_book_right.png");

    Page[] pages;
    List<HoverDraw> hovers = new ArrayList<HoverDraw>();
    int currentRenderMouseX = 0, currentRenderMouseY = 0;
    Map<String, ChapterMarker> tableOfContent = new LinkedHashMap<String, ChapterMarker>();

    private int leftPageIndex = 0, rightPageIndex = leftPageIndex + 1;

    static boolean isMouseHovering(int mouseX, int mouseY, int posX, int posY, int width, int height)
    {
        return mouseX >= posX && mouseY >= posY && mouseX < posX + width && mouseY < posY + height;
    }

    private ChapterMarker getTableOfContentMarker()
    {
        return getTableOfContent().get("tableofcontent");
    }

    @Override
    public void initGui()
    {
        super.initGui();

        this.buttonList.add(new GuiNoRenderButton(0, width / 2 - PAGE_WIDTH, (height + BOOK_HEIGHT) / 2, BUTTON_SIZE, BUTTON_SIZE, ""));
        this.buttonList.add(new GuiNoRenderButton(1, width / 2 + PAGE_WIDTH - BUTTON_SIZE, (height + BOOK_HEIGHT) / 2, BUTTON_SIZE, BUTTON_SIZE, ""));
        this.buttonList.add(new GuiNoRenderButton(2, width / 2 + PAGE_WIDTH - BUTTON_SIZE - 14, (height - BOOK_HEIGHT) / 2 - 24, BUTTON_SIZE, BUTTON_SIZE, ""));
        this.buttonList.add(new GuiNoRenderButton(3, width / 2 - PAGE_WIDTH + 14, (height - BOOK_HEIGHT) / 2 - 24, BUTTON_SIZE, BUTTON_SIZE, ""));
        //BOOK_WIDTH - 14 - 24, -24, 23, 24

        try
        {
            IChapterHandler[] handlers = CookingBookRegistry.getInstance().getRegisteredHandlers();
            Chapter[] chapters = makeChapters(handlers);
            PagedChapter[] pagedChapters = initFromChapters(chapters);
            this.pages = createPages(pagedChapters);
        } catch (Exception e)
        {
            e.printStackTrace();
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public void updateScreen()
    {
        Page[] pages = new Page[]{getPage(leftPageIndex), getPage(rightPageIndex)};
        for (Page page : pages)
        {
            Container container = new Container(page.getChapter());
            for (IPageElement element : page.elements)
            {
                element.onUpdate(container);
                container.onLoop(element.getHeight(container));
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);
        switch (button.id)
        {
            case 0:
                decreasePage();
                break;
            case 1:
                increasePage();
                break;
            case 2:
                mc.displayGuiScreen(null);
                break;
            case 3:
                goToPage(getTableOfContentMarker().getPageIndex());
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partial)
    {
        super.drawScreen(mouseX, mouseY, partial);

        this.currentRenderMouseX = mouseX;
        this.currentRenderMouseY = mouseY;

        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        int x = (width - BOOK_WIDTH) / 2, y = (height - BOOK_HEIGHT) / 2;
        GL11.glTranslatef(x, y, 0);
        int relMouseX = mouseX - x, relMouseY = mouseY - y;
        this.drawBook(relMouseX, relMouseY, partial);
        Page leftPage = getPage(leftPageIndex), rightPage = getPage(rightPageIndex);
        GL11.glPushMatrix();
        GL11.glTranslated(LEFT_PADDING, TOP_PADDING, 0);
        this.drawPage(leftPage, relMouseX - LEFT_PADDING, relMouseY - TOP_PADDING);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(PAGE_WIDTH + RIGHT_PADDING, TOP_PADDING, 0);
        this.drawPage(rightPage, relMouseX - RIGHT_PADDING, relMouseY - TOP_PADDING);
        GL11.glPopMatrix();
        drawCenteredString(mc.fontRenderer, String.valueOf(leftPageIndex + 1), PAGE_WIDTH / 2, BOOK_HEIGHT - 20, 0x4C1C06);
        drawCenteredString(mc.fontRenderer, String.valueOf(rightPageIndex + 1), (PAGE_WIDTH / 2) + PAGE_WIDTH, BOOK_HEIGHT - 20, 0x4C1C06);
        GL11.glPopMatrix();

        for (HoverDraw draw : hovers)
            draw.draw();
        hovers.clear();
    }

    @Override
    public void drawCenteredString(FontRenderer renderer, String s, int x, int y, int color)
    {
        renderer.drawString(s, x - renderer.getStringWidth(s) / 2, y, color);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        int relMouseX = mouseX - LEFT_PADDING - (width / 2 - PAGE_WIDTH);
        int relMouseY = mouseY - TOP_PADDING - ((height - BOOK_HEIGHT) / 2);

        if (relMouseY >= 0 && relMouseY < BOOK_HEIGHT - TOP_PADDING - BOTTOM_PADDING)
            if (relMouseX >= 0 && relMouseX < PAGE_WIDTH - LEFT_PADDING - RIGHT_PADDING)
            {
                Page page = getPage(leftPageIndex);
                handlePageClick(page, relMouseX, relMouseY, mouseButton);
            } else if (relMouseX >= PAGE_WIDTH && relMouseX < BOOK_WIDTH - LEFT_PADDING - RIGHT_PADDING)
            {
                relMouseX -= PAGE_WIDTH;
                Page page = getPage(rightPageIndex);
                handlePageClick(page, relMouseX, relMouseY, mouseButton);
            }
    }

    private void handlePageClick(Page page, int mouseX, int mouseY, int mouseButton)
    {
        Container container = new Container(page.getChapter());
        for (IPageElement element : page.elements)
        {
            element.mouseClicked(container, mouseX, mouseY, mouseButton);
            int height = element.getHeight(container);
            mouseY -= height;
            container.decreaseHeight(height);
            container.increaseElementIndex();
        }
    }

    private void drawBook(int mouseX, int mouseY, float partial)
    {
        mc.getTextureManager().bindTexture(LEFT);
        boolean hover = isMouseHovering(mouseX, mouseY, 0, BOOK_HEIGHT, BUTTON_SIZE, BUTTON_SIZE);
        drawTexturedModalRect(0, 0, 0, 0, PAGE_WIDTH, BOOK_HEIGHT);
        ChapterMarker tocMarker = getTableOfContentMarker();
        int x = leftPageIndex != tocMarker.getPageIndex() && rightPageIndex != tocMarker.getPageIndex() ? 48 : 48 + 24;
        drawTexturedModalRect(15, -24, x, 180, 23, 24);
        if (canGoLeft()) drawTexturedModalRect(0, BOOK_HEIGHT, hover ? 24 : 0, 180, BUTTON_SIZE, BUTTON_SIZE);
        mc.getTextureManager().bindTexture(RIGHT);
        hover = isMouseHovering(mouseX, mouseY, BOOK_WIDTH - BUTTON_SIZE, BOOK_HEIGHT, BUTTON_SIZE, BUTTON_SIZE);
        drawTexturedModalRect(PAGE_WIDTH, 0, 0, 0, PAGE_WIDTH, BOOK_HEIGHT);
        drawTexturedModalRect(BOOK_WIDTH - 14 - 24, -24, 48, 180, 23, 24);
        if (canGoRight())
            drawTexturedModalRect(BOOK_WIDTH - BUTTON_SIZE, BOOK_HEIGHT, hover ? 24 : 0, 180, BUTTON_SIZE, BUTTON_SIZE);
        if (isMouseHovering(mouseX, mouseY, 15, -24, 23, 24) && !(leftPageIndex == getTableOfContentMarker().getPageIndex()))
            hovers.add(new HoverDraw(Collections.singletonList("Home"), mc.fontRenderer));
        else if (isMouseHovering(mouseX, mouseY, BOOK_WIDTH - 14 - 24, -24, 23, 24))
            hovers.add(new HoverDraw(Collections.singletonList("Exit"), mc.fontRenderer));
    }

    private void drawPage(Page page, int mouseX, int mouseY)
    {
        Container container = new Container(page.getChapter());
        GL11.glPushMatrix();
        for (IPageElement element : page.elements)
        {
            GL11.glPushMatrix();
            element.render(container, mouseX, mouseY);
            GL11.glPopMatrix();
            int height = element.getHeight(container);
            mouseY -= height;
            GL11.glTranslatef(0, height, 0);
            container.decreaseHeight(height);
        }
        GL11.glPopMatrix();
    }

    public void increasePage()
    {
        if (canGoRight())
        {
            leftPageIndex += 2;
            rightPageIndex = leftPageIndex + 1;
        }
    }

    public void decreasePage()
    {
        if (canGoLeft())
        {
            leftPageIndex -= 2;
            rightPageIndex = leftPageIndex + 1;
        }
    }

    private Page getPage(int pageIndex)
    {
        if (pageIndex >= 0 && pageIndex < pages.length)
        {
            return pages[pageIndex];
        } else
        {
            return new Page(null);
        }
    }

    private void evenOutPages(List<Page> pages, Chapter chapter)
    {
        if (pages.size() % 2 != 0) pages.add(new Page(chapter));
    }

    private Page[] createPages(PagedChapter[] chapters)
    {
        List<Page> pages = new ArrayList<Page>();
        for (PagedChapter chapter : chapters)
        {
            tableOfContent.get(chapter.chapter.id).setPageIndex(pages.size());
            List<Page> chapterPages = chapter.pages;
            pages.addAll(chapterPages);
            evenOutPages(pages, chapter.chapter);
        }
        return pages.toArray(new Page[pages.size()]);
    }

    private PagedChapter[] initFromChapters(Chapter[] chapters)
    {
        PagedChapter[] initChapters = new PagedChapter[chapters.length];
        for (int i = 0; i < chapters.length; i++)
        {
            Chapter chapter = chapters[i];
            PagedChapter pagedChapter = new PagedChapter(chapter);
            Container container = new Container(chapter);
            Page currentPage = new Page(chapter);
            List<IPageElement> elements1 = chapter.getElements();
            for (int i1 = 0; i1 < elements1.size(); i1++)
            {
                IPageElement element = elements1.get(i1);
                element.initElement(container);
                int height = element.getHeight(container);
                if (height > container.getAvailableHeight())
                {
                    if (element instanceof ISplittable)
                    {
                        while (height > container.getAvailableHeight() && element instanceof ISplittable)
                        {
                            IPageElement split = ((ISplittable) element).createSplitElement(container);
                            currentPage.addElement(element);
                            pagedChapter.addPage(currentPage.copy());
                            currentPage = new Page(chapter);
                            container.reset();
                            element = split;
                            element.initElement(container);
                            height = element.getHeight(container);
                        }
                    } else
                    {
                        pagedChapter.addPage(currentPage.copy());
                        currentPage = new Page(chapter);
                        container.reset();
                    }
                }
                currentPage.addElement(element);
                container.decreaseHeight(height);
                container.increaseElementIndex();
            }
            pagedChapter.addPage(currentPage);
            initChapters[i] = pagedChapter;
        }
        return initChapters;
    }

    private Chapter[] makeChapters(IChapterHandler[] handlers)
    {
        Chapter[] chapters = new Chapter[handlers.length];
        for (int i = 0; i < handlers.length; i++)
        {
            String id = handlers[i].getId();
            tableOfContent.put(id, new ChapterMarker(handlers[i]));
            Chapter chapter = new Chapter(!CookingBookUnlocksProperties.hasPlayerUnlocked(id, mc.thePlayer), id);
            if (chapter.isLocked()) handlers[i].addLockedElementsToChapter(chapter);
            else handlers[i].addElementsToChapter(chapter);
            chapters[i] = chapter;
        }
        return chapters;
    }

    public boolean canGoRight()
    {
        return rightPageIndex < pages.length - 1;
    }

    public boolean canGoLeft()
    {
        return leftPageIndex > 0;
    }

    @Override
    public Map<String, ChapterMarker> getTableOfContent()
    {
        return tableOfContent;
    }

    @Override
    public void goToPage(int page)
    {
        page = Math.max(Math.min(page, pages.length - 2), 0);
        if (page % 2 == 0)
            leftPageIndex = page;
        else
            leftPageIndex = page - 1;
        rightPageIndex = leftPageIndex + 1;
    }

    @Override
    protected void keyTyped(char charTypes, int keyCode)
    {
        super.keyTyped(charTypes, keyCode);

        switch (keyCode)
        {
            case Keyboard.KEY_1:
                goToPage(0);
                break;
            case Keyboard.KEY_2:
                goToPage(1);
                break;
            case Keyboard.KEY_3:
                goToPage(2);
                break;
            case Keyboard.KEY_4:
                goToPage(3);
                break;
            case Keyboard.KEY_5:
                goToPage(4);
                break;
            case Keyboard.KEY_6:
                goToPage(5);
                break;
            case Keyboard.KEY_7:
                goToPage(6);
                break;
            case Keyboard.KEY_8:
                goToPage(7);
                break;
            case Keyboard.KEY_9:
                goToPage(8);
                break;
            case Keyboard.KEY_LEFT:
            case Keyboard.KEY_A:
                goToPage(leftPageIndex - 1);
                break;
            case Keyboard.KEY_RIGHT:
            case Keyboard.KEY_D:
                goToPage(rightPageIndex + 1);
                break;
        }
    }

    public class Container implements IPageElementContainer
    {
        int height = BOOK_HEIGHT - TOP_PADDING - BOTTOM_PADDING;
        int elementIndex = 0;
        Chapter chapter;

        private Container(Chapter chapter)
        {
            this.chapter = chapter;
        }

        @Override
        public int getAvailableWidth()
        {
            return PAGE_WIDTH - LEFT_PADDING - RIGHT_PADDING;
        }

        private void setHeight(int height)
        {
            this.height = height;
        }

        private void resetHeight()
        {
            setHeight(BOOK_HEIGHT - TOP_PADDING - BOTTOM_PADDING);
        }

        private void resetIndex()
        {
            elementIndex = 0;
        }

        private void reset()
        {
            resetHeight();
            resetIndex();
        }

        private void decreaseHeight(int amount)
        {
            this.height -= amount;
        }

        private void increaseElementIndex()
        {
            elementIndex++;
        }

        @Override
        public int getAvailableHeight()
        {
            return height;
        }

        @Override
        public int getCurrentElementId()
        {
            return elementIndex;
        }

        @Override
        public IChapter getChapter()
        {
            return chapter;
        }

        @Override
        public Minecraft getMinecraft()
        {
            return mc;
        }

        @Override
        public RenderItem getRenderItem()
        {
            return itemRender;
        }

        @Override
        public IBook getGui()
        {
            return GuiScreenBook.this;
        }

        @Override
        public void drawHoverTextAtMouse(List text, FontRenderer renderer)
        {
            hovers.add(new HoverDraw(currentRenderMouseX, currentRenderMouseY, text, renderer));
        }

        @Override
        public void drawHoverText(List text, int x, int y, FontRenderer renderer)
        {
            drawHoveringText(text, x, y, renderer);
        }

        public void onLoop(int height)
        {
            this.decreaseHeight(height);
            this.increaseElementIndex();
        }
    }

    private class HoverDraw
    {
        int x, y;
        List lines;
        FontRenderer renderer;

        private HoverDraw(int x, int y, List lines, FontRenderer renderer)
        {
            this.x = x;
            this.y = y;
            this.lines = lines;
            this.renderer = renderer;
        }

        private HoverDraw(List lines, FontRenderer renderer)
        {
            this(currentRenderMouseX, currentRenderMouseY, lines, renderer);
        }

        public void draw()
        {
            drawHoveringText(lines, x, y, renderer);
        }
    }
}

/*
public class GuiScreenBook extends GuiScreen
{
    private static final String START_CRAFTING = "$C_START$";
    private static final String STOP_CRAFTING = "$C_STOP$";
    private static final String START_TEXT = "$T_START$";
    private static final String STOP_TEXT = "$T_STOP$";
    private static final String CENTER_TEXT = "$T_CENTER$";
    private static final String DONT_TRANSLATE_TEXT = "$T_DT$";
    private static final String START_IMAGE = "$I_START$";
    private static final String STOP_IMAGE = "$I_STOP$";
    private static final String ADD_SPACE = "$SPACE$";
    int pageSet = 0;
    List<Page> pages;
    int[] pageIndex;

    static boolean isMouseHovering(int mouseX, int mouseY, int posX, int posY, int width, int height)
    {
        return mouseX >= posX && mouseY >= posY && mouseX < posX + width && mouseY < posY + height;
    }

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

        Minecraft.getMinecraft().thePlayer.getExtendedProperties("cookingBookUnlocks");

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
//        List<String> lines = new ArrayList<String>();
//
//        lines.add(translate("item.cooking_book.pages.stop_mod_reposts.title"));
//        lines.add("");
//        lines.add(translate("item.cooking_book.pages.stop_mod_reposts.text"));
//
//        List<Element> elements = new ArrayList<Element>();
//        elements.add(new TextElement(lines).center());
//
//        pages.addAll(splitElementsToPages(elements, mc, 120));
    }

    private void addIntroPages()
    {
        List<String> lines = new ArrayList<String>();
        lines.add(translate("item.cooking_book.pages.introduction").replace("%v", ModInfo.version) + ".");
        if (!Kitchen.proxy.versionHighlights.equals(""))
        {
            lines.add("\n\n" + translate("item.cooking_book.pages.update_highlights"));
            lines.add(Kitchen.proxy.versionHighlights.replace("Â§", "§"));
            System.out.println("Added line: " + Kitchen.proxy.versionHighlights);
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

    private interface Page
    {
        public void draw(Minecraft minecraft, int maxWidth, int mouseX, int mouseY);
    }

    private interface Splittable
    {
        */
/**
 * Splits the element into to parts, one in the specified height, and the other the remains.
 *
 * @param toHeight The height if which the first element is being capped to.
 * @return Returns an array of 2 elements.
 *//*

        public Element[] split(int toHeight);
    }

    private interface Element
    {
        public void draw(Minecraft minecraft, int maxWidth, int mouseX, int mouseY);

        public int getHeight(Minecraft minecraft, int maxWidth);
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
                        case 8:
                            x = 2;
                            y = 2;
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
}
*/
