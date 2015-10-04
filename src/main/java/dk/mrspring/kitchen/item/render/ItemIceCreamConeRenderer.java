package dk.mrspring.kitchen.item.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 12-02-2015.
 */
@SideOnly(Side.CLIENT)
public class ItemIceCreamConeRenderer extends ItemIceCreamableRenderer
{
    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();
        switch (type)
        {
            case EQUIPPED:
                GL11.glTranslatef(-0.25F, 0.7F, -0.6F);
                GL11.glRotatef(45, 1, 0, 0);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(0, 0.1F, 0);
                break;
        }
        super.renderItem(type, item, data);
        GL11.glPopMatrix();
    }
}
