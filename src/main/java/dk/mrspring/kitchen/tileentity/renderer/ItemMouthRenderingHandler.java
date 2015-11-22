package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.util.ItemUtils;
import dk.mrspring.kitchen.util.RenderUtils;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 22-11-2015.
 */
public class ItemMouthRenderingHandler implements TileEntityGrinderRenderer.IMouthRenderingHandler
{
    @Override
    public boolean shouldUse(ItemStack mouth)
    {
        return ItemUtils.item(mouth, KitchenItems.plate_mouth);
    }

    @Override
    public void render(ItemStack mouth)
    {
        GL11.glTranslatef(0, 1.52F, -0.21F);
        float s = 1.95F;
        GL11.glScalef(s, s, s);
        GL11.glRotatef(90F, 1, 0, 0);
        RenderUtils.renderItem(mouth, 0, 0, 0);
    }
}
