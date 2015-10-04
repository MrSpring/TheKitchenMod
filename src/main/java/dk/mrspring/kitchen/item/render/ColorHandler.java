package dk.mrspring.kitchen.item.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 04-10-2015 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public abstract class ColorHandler
{
    private final Map<String, Integer> COLORS = new HashMap<String, Integer>();
    private int defaultColor = 16777215;

    abstract String getIdentifierFromStack(ItemStack stack);

    public abstract void loadDefaults();

    public int getColorAsInteger(String type)
    {
        if (type != null)
            if (COLORS.containsKey(type))
                return COLORS.get(type);
        return defaultColor;
    }

    public ColorHandler setDefaultColor(int newColor)
    {
        this.defaultColor = newColor;
        return this;
    }

    public int getColorFromStack(ItemStack stack)
    {
        String type = this.getIdentifierFromStack(stack);
        return getColorAsInteger(type);
    }

    public boolean registerColor(String typeName, int color)
    {
        if (!COLORS.containsKey(typeName))
        {
            COLORS.put(typeName, color);
            return true;
        } else return false;
    }

    public float[] getColorAsRGB(String type)
    {
        int baseColor = getColorAsInteger(type);
        return getIntAsRGB(baseColor);
    }

    public Map<String, Integer> getColors()
    {
        return COLORS;
    }

    public static float[] getIntAsRGB(int color)
    {
        float red = ((color >> 16) & 0xFF);
        float green = ((color >> 8) & 0xFF);
        float blue = (color & 0xFF);
        return new float[]{red, green, blue};
    }

    public static int getRGBAsInt(float[] color, int fallback)
    {
        if (color == null || color.length < 3) return fallback;
        int red = (int) (color[0] * 255);
        int green = (int) (color[1] * 255);
        int blue = (int) (color[2] * 255);
        int rgb = red;
        rgb = (rgb << 8) + green;
        rgb = (rgb << 8) + blue;
        return rgb;
    }
}
