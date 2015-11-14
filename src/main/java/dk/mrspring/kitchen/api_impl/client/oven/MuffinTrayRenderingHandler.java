package dk.mrspring.kitchen.api_impl.client.oven;

import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.api_impl.common.oven.MuffinTrayOvenItem;
import org.lwjgl.opengl.GL11;

/**
 * Created on 14-11-2015 for TheKitchenMod.
 */
public class MuffinTrayRenderingHandler extends RecipeItemRenderingHandler
{
    @Override
    public boolean shouldBeUsed(IOven oven, IOvenItem item, int slot, boolean firstSlot)
    {
        return item instanceof MuffinTrayOvenItem;
    }

    @Override
    public void renderPreTranslate(IOven oven, IOvenItem item, int slot, boolean firstSlot)
    {
        float s = 2.1F;
        GL11.glScalef(s, s, s);
        GL11.glTranslatef(0.25F, 0.45F, 1.15F);
        GL11.glRotatef(180, 0, 1, 0);
        GL11.glRotatef(90, 1, 0, 0);
    }

    @Override
    public void render(IOven oven, IOvenItem item, int slot, boolean firstSlot)
    {
        if (firstSlot)
            super.render(oven, item, slot, true);
    }
}
