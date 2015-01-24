package dk.mrspring.kitchen.pan;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrSpring on 25-09-2014 for ModJam4.
 */
public class Jam
{
    public static Map<String, Jam> jams = new HashMap<String, Jam>();

    final String name;
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

    public Jam(String name, int color, String itemName)
    {
        this.name = name;
        this.color = color;
        this.item = itemName;
    }

    public static void registerJam(Jam jam)
    {
        if (jam != null)
            if (!jams.containsKey(jam.getName()))
                jams.put(jam.getName(), jam);
    }

    public String getName()
    {
        return name;
    }

    public static Jam getJam(String name)
    {
        if (name != null)
            if (jams.containsKey(name))
                return jams.get(name);
        return jams.get("empty");
    }
}