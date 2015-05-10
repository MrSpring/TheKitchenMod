package dk.mrspring.kitchen.config;

import dk.mrspring.kitchen.config.wrapper.JsonBasicRecipe;

import java.io.File;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class KnifeConfig extends BaseConfig
{
    public int knife_recipe = 0;
    public String[] custom_knife_recipe = new String[]{"BBI", "BIB", "SBB"};
    public JsonBasicRecipe[] custom_knife_recipes = new JsonBasicRecipe[0];

    public KnifeConfig()
    {
        knife_recipe = 0;
        custom_knife_recipe = new String[]{"BBI", "BIB", "SBB"};
        custom_knife_recipes = new JsonBasicRecipe[0];
    }

    public KnifeConfig(File location, String name)
    {
        super(location, name);
    }
}
