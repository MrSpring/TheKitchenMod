package dk.mrspring.kitchen.api_impl.client.book;

import com.google.gson.*;
import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.api.book.IChapter;
import dk.mrspring.kitchen.api.book.IChapterHandler;
import dk.mrspring.kitchen.api.book.IPageElement;
import dk.mrspring.kitchen.api_impl.client.book.element.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created on 11-02-2016 for TheKitchenMod.
 */
public class JsonBookRegistry extends BasicBookRegistry implements IResourceManagerReloadListener
{
    ResourceLocation location;

    public JsonBookRegistry(ResourceLocation location)
    {
        this.location = location;
    }

    @Override
    public void onResourceManagerReload(IResourceManager manager)
    {
        this.registeredHandlers.clear();
        GameSettings settings = Minecraft.getMinecraft().gameSettings;
        IResource resource = get(manager, expandWithLanguage(settings.language));
        if (resource == null) resource = get(manager, expandWithLanguage("en_US"));
        if (resource == null) return;
        InputStream stream = resource.getInputStream();
        Gson gson = makeGson();
        BookWrapper book = gson.fromJson(new InputStreamReader(stream), BookWrapper.class);
        for (Map.Entry<String, ChapterWrapper> entry : book.chapters.entrySet())
        {
            String id = entry.getKey();
            registerChapterHandler(id, entry.getValue().unwrap(id));
        }
    }

    public Gson makeGson()
    {
        return new GsonBuilder().registerTypeAdapter(ElementWrapper.class, new ElementSerializer()).create();
    }

    private IResource get(IResourceManager manager, ResourceLocation location)
    {
        try
        {
            return manager.getResource(location);
        } catch (IOException e)
        {
            ModLogger.error("Failed to load JSON book: " + location, e);
            return null;
        }
    }

    public ResourceLocation expandWithLanguage(String lang)
    {
        return new ResourceLocation(location.getResourceDomain(), "lang/manual/" + location.getResourcePath() + "-" + lang + ".json");
    }

    private static class BookWrapper
    {
        String title = "null";
        Map<String, ChapterWrapper> chapters;
    }

    private static class ChapterWrapper
    {
        String toc_title = "null";
        boolean show_in_toc = true;
        ElementWrapper[] elements;

        public IChapterHandler unwrap(String id)
        {
            WrapChapterHandler handler = new WrapChapterHandler(id);
            for (ElementWrapper wrapper : elements) handler.addWrapper(wrapper);
            return handler;
        }
    }

    private static class WrapChapterHandler implements IChapterHandler
    {
        List<ElementWrapper> elements = new LinkedList<ElementWrapper>();
        String id;

        WrapChapterHandler(String id)
        {
            this.id = id;
        }

        void addWrapper(ElementWrapper wrapper)
        {
            this.elements.add(wrapper);
        }

        @Override
        public void addElementsToChapter(IChapter chapter)
        {
            for (ElementWrapper wrapper : elements)
            {
                if (wrapper != null)
                {
                    IPageElement element = wrapper.makeElement();
                    if (element != null) chapter.addElement(element);
                }
            }
        }

        @Override
        public void addLockedElementsToChapter(IChapter chapter)
        {
            addElementsToChapter(chapter);
        }

        @Override
        public String getId()
        {
            return id;
        }

        @Override
        public String getUnlocalizedTitle()
        {
            return id;
        }
    }

    private static abstract class ElementWrapper
    {
        ElementType type = ElementType.UNKNOWN;

        public abstract IPageElement makeElement();
    }

    private static class TextElementWrapper extends ElementWrapper
    {
        public String text = null;
        public float text_scale = 1F;
        public String[] text_lines = new String[0];
        public Alignment text_alignment = Alignment.LEFT;
        public int text_color = 0x4C1C06;
        public boolean text_shadow = false;

        @Override
        public IPageElement makeElement()
        {
            if (text == null && (text_lines == null || text_lines.length == 0)) return empty();
            String text = this.text;
            if (text == null) text = makeTextFromLines();
            return new TextElement(text, text_alignment, text_scale, text_color, text_shadow);
        }

        String makeTextFromLines()
        {
            if (text_lines.length > 0)
            {
                String s = "";
                for (String line : text_lines) s += line + "\n";
                return s.substring(0, s.length() - 1);
            } else return "";
        }
    }

    private static class TextWithImageElementWrapper extends TextElementWrapper
    {
        public TextImageElement.ImageAlign image_align;
        public String image_location = null;
        public int[] image_uv = new int[]{0, 0};
        public int[] image_size = new int[]{0, 0};

        @Override
        public IPageElement makeElement()
        {
            if (image_location == null || image_size.length != 2 || image_uv.length != 2) return super.makeElement();
            String text = this.text;
            if (text == null) text = makeTextFromLines();
            return new TextImageElement(text, text_alignment, text_scale, text_color, text_shadow, image_size[0], image_size[1], image_uv[0], image_uv[1], new ResourceLocation(image_location), image_align);
        }
    }

    private static class ImageElementWrapper extends ElementWrapper
    {
        public String image_location = null;
        public int[] image_uv = new int[]{0, 0};
        public int[] image_size = new int[]{0, 0};

        @Override
        public IPageElement makeElement()
        {
            if (image_location == null || image_size.length != 2 || image_uv.length != 2) return null;
            return new ImageElement(new ResourceLocation(image_location), image_uv[0], image_uv[1], image_size[0], image_size[1]);
        }
    }

    private static class EndPageElementWrapper extends ElementWrapper
    {
        public int offset = 0;

        @Override
        public IPageElement makeElement()
        {
            return new EndOfPageElement(Math.max(0, offset));
        }
    }

    private static class SpacerElementWrapper extends ElementWrapper
    {
        int height = 0;

        @Override
        public IPageElement makeElement()
        {
            if (height > 0) return new SpacerElement(height);
            else return null;
        }
    }

    private static class ElementSerializer implements JsonDeserializer<ElementWrapper>
    {
        @Override
        public ElementWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            if (!json.isJsonObject())
                throw new JsonParseException("Expected ElementWrapper element to be Json Object. It was not!");
            JsonObject object = json.getAsJsonObject();
            JsonElement element_type = object.get("type");
            ElementType type = context.deserialize(element_type, ElementType.class);
            if (type != ElementType.UNKNOWN) return context.deserialize(object, type.WRAPPER_CLASS);
            else return null;
        }
    }

    private static IPageElement empty()
    {
        return new SpacerElement(0);
    }

    private enum ElementType
    {
        UNKNOWN(null),
        TEXT(TextElementWrapper.class),
        TEXT_WITH_IMAGE(TextWithImageElementWrapper.class),
        IMAGE(ImageElementWrapper.class),
        END_PAGE(EndPageElementWrapper.class),
        SPACER(SpacerElementWrapper.class);

        final Class<? extends ElementWrapper> WRAPPER_CLASS;

        ElementType(Class<? extends ElementWrapper> clazz)
        {
            this.WRAPPER_CLASS = clazz;
        }
    }
}
