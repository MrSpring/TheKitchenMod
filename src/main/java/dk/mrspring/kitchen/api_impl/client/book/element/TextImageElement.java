package dk.mrspring.kitchen.api_impl.client.book.element;

import dk.mrspring.kitchen.api.book.IPageElement;
import dk.mrspring.kitchen.api.book.IPageElementContainer;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Created on 13-02-2016 for TheKitchenMod.
 */
public class TextImageElement implements IPageElement
{
    ResourceLocation location;
    int u, v;
    int width, height;
    List<String> lines;

    @Override
    public void initElement(IPageElementContainer container)
    {

    }

    @Override
    public int getHeight(IPageElementContainer container)
    {
        return 0;
    }

    @Override
    public void render(IPageElementContainer container, int mouseX, int mouseY)
    {

    }

    @Override
    public void mouseClicked(IPageElementContainer container, int mouseX, int mouseY, int mouseButton)
    {

    }

    @Override
    public void onUpdate(IPageElementContainer container)
    {

    }
}
