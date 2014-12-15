package dk.mrspring.kitchen.item.render;

import net.minecraft.item.ItemStack;

import java.util.HashMap;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class ItemMixingBowlRenderer
{
    public static HashMap<String, Integer> mixColors = new HashMap<String, Integer>();

    public static int getColorAsInteger(String mixType)
    {
        if (mixType != null)
            if (mixColors.containsKey(mixType))
                return mixColors.get(mixType);
        return 16777215;
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
    }
}
