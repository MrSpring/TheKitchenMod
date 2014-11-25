package dk.mrspring.kitchen.config;

import dk.mrspring.kitchen.recipe.OvenRecipes;

import java.io.File;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class OvenConfig extends BaseConfig
{
    public String[] custom_oven_recipes_input = OvenRecipes.defaultInput;
    public String[] custom_oven_recipes_output = OvenRecipes.defaultOutput;

    public OvenConfig(File location, String name)
    {
        super(location, name);
    }
}
