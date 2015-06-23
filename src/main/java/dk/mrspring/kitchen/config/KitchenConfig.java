package dk.mrspring.kitchen.config;

import org.lwjgl.input.Keyboard;

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
    public int show_stats_key = Keyboard.KEY_LSHIFT;
    public boolean show_item_name = true;
    public int[] show_item_name_key_combo = new int[]{Keyboard.KEY_LSHIFT, Keyboard.KEY_LCONTROL, Keyboard.KEY_LMENU};

    public KitchenConfig()
    {
        lettuce_spawn_rate = 15;
        show_item_debug_info = false;
        show_console_debug = false;
        show_mod_repost_info = true;
        show_different_jars_in_creative_tab = true;
        show_different_mixing_bowls_in_creative_tab = true;
        show_stats_key = Keyboard.KEY_LSHIFT;
        show_item_name = true;
        show_item_name_key_combo = new int[]{Keyboard.KEY_LSHIFT, Keyboard.KEY_LCONTROL, Keyboard.KEY_LMENU};
    }

    public KitchenConfig(File location, String name)
    {
        super(location, name);
    }
}
