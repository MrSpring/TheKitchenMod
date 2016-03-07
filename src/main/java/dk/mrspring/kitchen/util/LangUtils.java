package dk.mrspring.kitchen.util;

import net.minecraft.client.resources.I18n;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
public class LangUtils
{
    public static String deepFormat(String key, String... format)
    {
        for (int i = 0; i < format.length; i++) format[i] = I18n.format(format[i]);
        return I18n.format(key, format);
    }
}
