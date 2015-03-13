package dk.mrspring.kitchen.item.render;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class ItemMixingBowlRenderer
{
    public static HashMap<String, Integer> mixColors = new LinkedHashMap<String, Integer>();

    public static int getColorAsInteger(String mixType)
    {
        if (mixType != null)
            if (mixColors.containsKey(mixType))
                return mixColors.get(mixType);
        return 16777215;
    }

    public static float[] getColorAsRGB(String mixType)
    {
        int baseColor = getColorAsInteger(mixType);
        return intAsFloatArray(baseColor);
    }

    public static float[] intAsFloatArray(int color)
    {
        float red = ((color >> 16) & 0xFF);
        float green = ((color >> 8) & 0xFF);
        float blue = (color & 0xFF);
        return new float[]{red, green, blue};
    }

    public static String getMixType(ItemStack mixingBowlStack)
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

    public static void initColors()
    {
        mixColors.put("waffle_dough", 0xFFBB56);
        mixColors.put("pancake_dough", 0xFFD375);
        mixColors.put("burger_bun_dough", 0xFFD375);
        mixColors.put("vanilla_ice_cream", 0xFFFFFF);
        mixColors.put("strawberry_ice_cream", 0xFF9F9E);
        mixColors.put("chocolate_ice_cream", 0xCC8051);
        mixColors.put("apple_ice_cream", 0xF2EFBC);
    }
}
