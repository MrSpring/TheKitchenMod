package dk.mrspring.kitchen.config;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.io.File;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class KitchenConfig extends BaseConfig
{
    public int lettuce_spawn_rate = 10;
    public boolean show_item_debug_info = false;
    public boolean show_console_debug = false;
    public boolean show_mod_repost_into = true;

    public KitchenConfig(File location, String name)
    {
        super(location, name);
    }
}
