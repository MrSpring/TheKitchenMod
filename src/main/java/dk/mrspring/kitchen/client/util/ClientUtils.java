package dk.mrspring.kitchen.client.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class ClientUtils
{
    public static void push()
    {
        GL11.glPushMatrix();
    }

    public static void pop()
    {
        GL11.glPopMatrix();
    }

    public static void bind(ResourceLocation texture)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    }

    public static void translate(float x, float y, float z)
    {
        GL11.glTranslatef(x, y, z);
    }

    public static void rotate(float angle, float x, float y, float z)
    {
        GL11.glRotatef(angle, x, y, z);
    }

    public static void rotate(int angle)
    {
        rotate(4, angle);
    }

    public static void rotate(int angles, int angle)
    {
        float degrees = (float) angle / (float) angles;
        rotate(360F * degrees, 0F, 1F, 0F);
    }

    public static void scale(float x, float y, float z)
    {
        GL11.glScalef(x, y, z);
    }

    public static void scale(float scale)
    {
        scale(scale, scale, scale);
    }

    public static ResourceLocation modelTexture(String name)
    {
        return ModInfo.toLocation(String.format("textures/models/%s.png", name));
    }

    public static void enableTextures()
    {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}
