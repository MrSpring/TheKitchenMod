package dk.mrspring.kitchen.pan;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrSpring on 25-09-2014 for ModJam4.
 */
public class Jam // TODO: Move to API, rewrite
{
    public static Map<String, Jam> jams = new HashMap<String, Jam>();
    final String name;
    final int color;
    final Item item;
    public Jam(String name, int color, Item item)
    {
        this.name = name;
        this.color = color;
        this.item = item;
    }

    public static void registerJam(Jam jam)
    {
        if (jam != null)
            if (!jams.containsKey(jam.getName()))
                jams.put(jam.getName(), jam);
    }

    public static Jam getJam(String name)
    {
        if (name != null)
            if (jams.containsKey(name))
                return jams.get(name);
        return jams.get("empty");
    }

    public int getColor()
    {
        return color;
    }

    public Item getItem()
    {
        return this.item;
    }

    public String getName()
    {
        return name;
    }
}