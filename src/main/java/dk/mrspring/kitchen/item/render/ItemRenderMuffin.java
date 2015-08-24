package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.item.ItemMuffin;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Konrad on 09-08-2015.
 */
public class ItemRenderMuffin
{
    public static Map<String, Integer> muffinColors = new HashMap<String, Integer>();

    public static int getColorAsInteger(String mixType)
    {
        if (mixType != null)
            if (muffinColors.containsKey(mixType))
                return muffinColors.get(mixType);
        return 16777215;
    }

    public static int getColorAsInteger(ItemStack muffinStack)
    {
        String muffinType = ItemMuffin.getMuffinType(muffinStack);
        return getColorAsInteger(muffinType);
    }

    public static void initColors()
    {
        muffinColors.put("vanilla", 0xDFA037);
    }
}
