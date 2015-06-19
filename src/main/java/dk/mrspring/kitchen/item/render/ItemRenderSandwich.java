package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.tileentity.renderer.TileEntityBoardRenderer;
import dk.mrspring.kitchen.util.SandwichUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ItemRenderSandwich implements IItemRenderer
{
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
            case EQUIPPED:
                return true;
            case EQUIPPED_FIRST_PERSON:
                return true;
            case ENTITY:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        switch (helper)
        {
            case ENTITY_BOBBING:
                return true;
            case ENTITY_ROTATION:
                return true;
            default:
                return false;
        }
    }


    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        switch (type)
        {
            case EQUIPPED:
            {
                GL11.glPushMatrix();

                GL11.glRotatef(-60, 1, 0, 0);
                GL11.glTranslatef(.4F, 2.3F, .3F);
                float f = 1.5F;
                GL11.glScalef(f, f, f);

                List<ItemStack> layers = SandwichUtils.getSandwichAsLayerList(item);
                TileEntityBoardRenderer.renderLayers(layers, null);

                GL11.glPopMatrix();

                break;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                GL11.glPushMatrix();

                GL11.glTranslatef(.2F, 1.8F, -0.2F);
                GL11.glRotatef(90, 0, 1, 0);
                GL11.glRotatef(-10, 1, 0, 0);

                float scale = 1F;
                GL11.glScalef(scale, scale, scale);

                List<ItemStack> layers = SandwichUtils.getSandwichAsLayerList(item);
                TileEntityBoardRenderer.renderLayers(layers, null);

                GL11.glPopMatrix();

                break;
            }
            case ENTITY:
            {
                GL11.glPushMatrix();

                GL11.glTranslatef(0F, 2.15F, 0F);
                float f = 1.5F;
                GL11.glScalef(f, f, f);

                List<ItemStack> layers = SandwichUtils.getSandwichAsLayerList(item);
                TileEntityBoardRenderer.renderLayers(layers, null);

                GL11.glPopMatrix();

                break;
            }
        }
    }
}
