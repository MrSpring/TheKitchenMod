package dk.mrspring.kitchen.config;

import dk.mrspring.kitchen.config.wrapper.JsonBasicRecipe;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import dk.mrspring.kitchen.recipe.ToasterRecipes;

import java.io.File;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class ToasterConfig extends BaseConfig
{
    JsonBasicRecipe[] example = new JsonBasicRecipe[] {
            new JsonBasicRecipe("minecraft:dirt", "minecraft:diamond"),
            new JsonBasicRecipe("kitchen:raw_bacon", "kitchen:bacon")
    };
    public JsonBasicRecipe[] custom_toaster_recipes = new JsonBasicRecipe[0];

    public ToasterConfig(File location, String name)
    {
        super(location, name);
    }
}
