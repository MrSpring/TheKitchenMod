package dk.mrspring.kitchen.item.render;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRenderSandwich implements IItemRenderer
{
    protected ItemStack[] items;
    protected boolean readData = true;

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
                GL11.glRotatef(20, 0, 0, 1);
                GL11.glTranslatef(.4F, 1.2F, .125F);
                float f = 1.1F;
                GL11.glScalef(f, f, f);

//				SandwichRender.renderSandwich(item, null);
                // TODO: FIX

                GL11.glPopMatrix();

                break;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                GL11.glPushMatrix();

                //GL11.glTranslatef(-1F, 0F, 0F);
                GL11.glTranslatef(.35F, 1.3F, -0.2F);
                GL11.glRotatef(90, 0, 1, 0);
                GL11.glRotatef(-10, 1, 0, 0);

                float scale = 0.7F;
                GL11.glScalef(scale, scale, scale);

//				SandwichRender.renderSandwich(item, null);
                // TODO: FIX
                GL11.glPopMatrix();

                break;
            }
            case ENTITY:
            {
                GL11.glPushMatrix();

                GL11.glTranslatef(0F, 2F, 0F);
                float f = 1.5F;
                GL11.glScalef(f, f, f);


//                SandwichRender.renderSandwich(item, null);
                // TODO: FIX

                GL11.glPopMatrix();

                break;
            }
        }
    }
}
