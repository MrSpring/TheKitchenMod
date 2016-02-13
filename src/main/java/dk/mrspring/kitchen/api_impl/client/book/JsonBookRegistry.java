package dk.mrspring.kitchen.api_impl.client.book;

import com.google.gson.*;
import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.api.book.IPageElement;
import dk.mrspring.kitchen.api_impl.client.book.element.SpacerElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
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
        GameSettings settings = Minecraft.getMinecraft().gameSettings;
        IResource resource = get(manager, expandWithLanguage(settings.language));
        if (resource == null) resource = get(manager, expandWithLanguage("en_US"));
        if (resource == null) return;
        InputStream stream = resource.getInputStream();
        Gson gson = makeGson();
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
        public boolean center = false;

        @Override
        public IPageElement makeElement()
        {
//            if (text == null && (text_lines == null || text_lines.length == 0)) return empty();
//            if (text != null) return new TextElement(text, center).setScaleFactor(text_scale);
//            String s = "";
//            for (String line : text_lines) s += line + "\n";
//            return new TextElement(s, center).setScaleFactor(text_scale);
            return empty();
        }
    }

    private static class TextWithImageElementWrapper extends TextElementWrapper
    {
        public Align image_align;
        public String image_location = null;
        public int[] image_uv = new int[]{0, 0};
        public int[] image_size = new int[]{0, 0};

        @Override
        public IPageElement makeElement()
        {
            if (image_location == null || image_size.length != 2 || image_uv.length != 2) return empty();
            return empty();
        }

        private enum Align
        {
            LEFT, RIGHT;
        }
    }

    private static class ElementSerializer implements JsonDeserializer<ElementWrapper>
    {
        @Override
        public ElementWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            /*if (!json.isJsonObject())
                throw new JsonParseException("Expected ElementWrapper element to be Json Object. It was not!");
            JsonObject object = json.getAsJsonObject();
            JsonElement element_type = object.get("type");
            ElementType type = context.deserialize(element_type, ElementType.class);
            switch (type)
            {
                case TEXT:
                    return context.deserialize(element_type, TextElementWrapper.class);
                case TEXT_WITH_IMAGE:
            }*/return null;
        }
    }

    private static IPageElement empty()
    {
        return new SpacerElement(0);
    }

    private enum ElementType
    {
        UNKNOWN,
        TEXT,
        TEXT_WITH_IMAGE,
        IMAGE,
        END_PAGE,
        SPACER
    }
}
