package dk.mrspring.kitchen.item.render;

import net.minecraft.item.ItemStack;

import java.util.HashMap;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class ItemMixingBowlRenderer
{
    static HashMap<String, Integer> mixColors = new HashMap<String, Integer>();
    static HashMap<String, float[]> rgbCache = new HashMap<String, float[]>();

    public static int getColorAsInteger(String mixType)
    {
        if (mixType != null)
            if (mixColors.containsKey(mixType))
                return mixColors.get(mixType);
        return 16777215;
    }

    public static float[] getColorAsRGB(String mixType)
    {
        if (mixType != null)
        {
            if (!rgbCache.containsKey(mixType))
            {
                int intColor = getColorAsInteger(mixType);
                if (intColor != 16777215)
                {
                    float red = ((intColor >> 16) & 0xFF);
                    float green = ((intColor >> 8) & 0xFF);
                    float blue = (intColor & 0xFF);
                    float[] colorAsRGB = new float[]{red, green, blue};
                    rgbCache.put(mixType, colorAsRGB);
                    return colorAsRGB;
                }
            } else return rgbCache.get(mixType);
        }
        return new float[]{1, 1, 1};
    }

    private static String getMixType(ItemStack mixingBowlStack)
    {
        if (mixingBowlStack.getTagCompound() != null)
            return mixingBowlStack.getTagCompound().getString("MixType");
        else return null;
    }

    public static int getColorAsInteger(ItemStack mixingBowlStack)
    {
        String mixType = getMixType(mixingBowlStack);
        return getColorAsInteger(mixType);
    }

    public static float[] getColorAsRGB(ItemStack mixingBowlStack)
    {
        String mixType = getMixType(mixingBowlStack);
        return getColorAsRGB(mixType);
    }

    public static void initColors()
    {
        mixColors.put("waffle_dough", 16042133);
    }
}
