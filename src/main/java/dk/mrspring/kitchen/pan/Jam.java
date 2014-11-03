package dk.mrspring.kitchen.pan;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Created by MrSpring on 25-09-2014 for ModJam4.
 */
public enum Jam
{
    EMPTY(000000, "null"),
    STRAWBERRY(16196364, "kitchen:strawberry_jam"),
    APPLE(14415786, "kitchen:apple_jam"),
    PEANUT(9659689, "kitchen:peanut_jam");

    final int color;
    final String item;

    public int getColor()
    {
        return color;
    }

    public Item getItem()
    {
        if (!this.item.equals("null"))
        {
            String[] nameAndId = this.item.split(":");
            String modId = nameAndId[0], itemName = nameAndId[1];
            return GameRegistry.findItem(modId, itemName);
        } else return null;
    }

    Jam(int color, String itemName)
    {
        this.color = color;
        this.item = itemName;
    }
}