package dk.mrspring.kitchen.config;

import dk.mrspring.kitchen.recipe.OvenRecipes;
import dk.mrspring.kitchen.recipe.ToasterRecipes;

import java.io.File;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class ToasterConfig extends BaseConfig
{
    public String[] custom_toaster_recipes_input = ToasterRecipes.defaultInput;
    public String[] custom_toaster_recipes_output = ToasterRecipes.defaultOutput;

    public ToasterConfig(File location, String name)
    {
        super(location, name);
    }
}
