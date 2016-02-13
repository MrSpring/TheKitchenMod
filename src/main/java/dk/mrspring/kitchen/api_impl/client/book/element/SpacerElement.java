package dk.mrspring.kitchen.api_impl.client.book.element;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.book.IPageElement;
import dk.mrspring.kitchen.api.book.IPageElementContainer;
import dk.mrspring.kitchen.api.book.ISplittable;
import org.lwjgl.opengl.GL11;

/**
 * Created on 11-09-2015 for TheKitchenMod.
 */
public class SpacerElement extends ImageElement implements ISplittable
{
    int height;
    boolean doImage = true;

    public SpacerElement(int height)
    {
        super(ModInfo.toResource("textures/gui/cooking_book.png"), 0, 222, 99, 11);
        this.height = height;
    }

    @Override
    public void initElement(IPageElementContainer container)
    {
    }

    @Override
    public int getHeight(IPageElementContainer container)
    {
        return height;
    }

    public SpacerElement disableImage()
    {
        this.doImage = false;
        return this;
    }

    @Override
    public void render(IPageElementContainer container, int mouseX, int mouseY)
    {
        if (height > 9 && doImage)
        {
            float heightOffset = ((float) (height - 11)) / 2F;
            GL11.glTranslatef(0, heightOffset, 0);
            super.render(container, mouseX, mouseY);
        }
    }

    @Override
    public IPageElement createSplitElement(IPageElementContainer container)
    {
        return new SpacerElement(0).disableImage();
    }
}
