package dk.mrspring.kitchen.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 22-11-2015.
 */
public class RenderUtils
{
    public static void renderItem(ItemStack item, double xOffset, double yOffset, double zOffset, boolean force3D)
    {
        if (item != null)
        {
            GameSettings settings = Minecraft.getMinecraft().gameSettings;
            boolean flag = settings.fancyGraphics;
            if (force3D) settings.fancyGraphics = true;
            GL11.glPushMatrix();
            GL11.glTranslated(xOffset, yOffset, zOffset);
            EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, item);
            itemEntity.hoverStart = 0.0F;
            RenderItem.renderInFrame = true;
            GL11.glRotatef(180, 0, 1, 1);
            GL11.glRotatef(180, 0, 1, 0);
            RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;
            GL11.glPopMatrix();
            settings.fancyGraphics = flag;
        }
    }
}
