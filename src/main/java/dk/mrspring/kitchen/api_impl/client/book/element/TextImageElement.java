package dk.mrspring.kitchen.api_impl.client.book.element;

import dk.mrspring.kitchen.api.book.IPageElementContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created on 13-02-2016 for TheKitchenMod.
 */
public class TextImageElement extends TextElement
{
    ResourceLocation image;
    int width, height, u, v;
    int vPadding = 2, hPadding = 2;
    ImageAlign imageAlign = ImageAlign.RIGHT;

    public TextImageElement(String text, Alignment align, float scaleFactor, int textColor, boolean textShadow, int width, int height, int u, int v, ResourceLocation image, ImageAlign imageAlign)
    {
        super(text, align, scaleFactor, textColor, textShadow);
        this.setImageProperties(width, height, u, v, image, imageAlign);
    }

    public TextImageElement(String text, Alignment align, float scaleFactor, int textColor, int width, int height, int u, int v, ResourceLocation image, ImageAlign imageAlign)
    {
        super(text, align, scaleFactor, textColor);
        this.setImageProperties(width, height, u, v, image, imageAlign);
    }

    public TextImageElement(String text, Alignment align, float scaleFactor, int width, int height, int u, int v, ResourceLocation image, ImageAlign imageAlign)
    {
        super(text, align, scaleFactor);
        this.setImageProperties(width, height, u, v, image, imageAlign);
    }

    public TextImageElement(String text, Alignment align, int width, int height, int u, int v, ResourceLocation image, ImageAlign imageAlign)
    {
        super(text, align);
        this.setImageProperties(width, height, u, v, image, imageAlign);
    }

    public TextImageElement(String text, int width, int height, int u, int v, ResourceLocation image, ImageAlign imageAlign)
    {
        super(text);
        this.setImageProperties(width, height, u, v, image, imageAlign);
    }

    @Override
    public int getXOffsetAtHeight(int y, IPageElementContainer container)
    {
        return imageAlign == ImageAlign.LEFT ? y < height + vPadding ? width + hPadding : 0 : 0;
    }

    @Override
    public int getWidthAtHeight(int y, IPageElementContainer container)
    {
        return y < height + vPadding ? container.getAvailableWidth() - width + hPadding : container.getAvailableWidth();
    }

    public TextImageElement setImageProperties(int width, int height, int u, int v, ResourceLocation image, ImageAlign imageAlign)
    {
        this.width = width;
        this.height = height;
        this.u = u;
        this.v = v;
        this.image = image;
        this.imageAlign = imageAlign;
        return this;
    }

    @Override
    public void render(IPageElementContainer container, int mouseX, int mouseY)
    {
        super.render(container, mouseX, mouseY);
        int x = imageAlign == ImageAlign.RIGHT ? container.getAvailableWidth() - width : 0;
        drawTexturedModalRect(x, 0, u, v, width, height, container);
    }

    public void drawTexturedModalRect(int x, int y, int u, int v, int w, int h, IPageElementContainer container)
    {
        container.getMinecraft().getTextureManager().bindTexture(image);
        GL11.glColor4f(1, 1, 1, 1);

        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + h), 0D, (double) ((float) (u + 0) * f), (double) ((float) (v + h) * f1));
        tessellator.addVertexWithUV((double) (x + w), (double) (y + h), 0D, (double) ((float) (u + w) * f), (double) ((float) (v + h) * f1));
        tessellator.addVertexWithUV((double) (x + w), (double) (y + 0), 0D, (double) ((float) (u + w) * f), (double) ((float) (v + 0) * f1));
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), 0D, (double) ((float) (u + 0) * f), (double) ((float) (v + 0) * f1));
        tessellator.draw();
    }

    @Override
    public boolean canSplit(IPageElementContainer container)
    {
        System.out.println("Can split: " + this.height + " <= " + container.getAvailableHeight());
        return container.getAvailableHeight() >= this.height && super.canSplit(container);
    }

    public enum ImageAlign
    {
        LEFT, RIGHT
    }
}
