package dk.mrspring.kitchen.api_impl.client.book.element;

import dk.mrspring.kitchen.api.book.IPageElement;
import dk.mrspring.kitchen.api.book.IPageElementContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class ImageElement implements IPageElement
{
    ResourceLocation location;
    int u;
    int v;
    int width;
    int height;

    public ImageElement(ResourceLocation location, int u, int v, int width, int height)
    {

        this.location = location;
        this.u = u;
        this.v = v;
        this.width = width;
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

    @Override
    public void render(IPageElementContainer container, int mouseX, int mouseY)
    {
        int x = (container.getAvailableWidth() - width) / 2, y = 0;
        container.getMinecraft().getTextureManager().bindTexture(location);
        GL11.glColor4f(1, 1, 1, 1);

        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + height), 0, (double) ((float) (u + 0) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + height), 0, (double) ((float) (u + width) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y), 0, (double) ((float) (u + width) * f), (double) ((float) (v + 0) * f1));
        tessellator.addVertexWithUV((double) (x + 0), (double) (y), 0, (double) ((float) (u + 0) * f), (double) ((float) (v + 0) * f1));
        tessellator.draw();
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
