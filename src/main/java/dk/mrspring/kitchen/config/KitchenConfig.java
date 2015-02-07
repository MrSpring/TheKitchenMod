package dk.mrspring.kitchen.config;

import java.io.File;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class KitchenConfig extends BaseConfig
{
    public int lettuce_spawn_rate = 15;
    public boolean show_item_debug_info = false;
    public boolean show_console_debug = false;
    public boolean show_mod_repost_info = true;
    public boolean show_different_jars_in_creative_tab = true;
    public boolean show_different_mixing_bowls_in_creative_tab = true;
    public boolean show_plate_message = true;

    public KitchenConfig()
    {
        lettuce_spawn_rate = 10;
        show_item_debug_info = false;
        show_console_debug = false;
        show_mod_repost_info = true;
        show_different_jars_in_creative_tab = true;
        show_different_mixing_bowls_in_creative_tab = true;
    }

    public KitchenConfig(File location, String name)
    {
        super(location, name);
    }
}
