package dk.mrspring.kitchen.config;

import dk.mrspring.kitchen.config.wrapper.JsonBasicRecipe;

import java.io.File;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class OvenConfig extends BaseConfig
{
    JsonBasicRecipe[] example = new JsonBasicRecipe[] {
            new JsonBasicRecipe("minecraft:dirt", "minecraft:diamond"),
            new JsonBasicRecipe("kitchen:raw_bacon", "kitchen:bacon")
    };
    public JsonBasicRecipe[] custom_oven_recipes = new JsonBasicRecipe[0];

    public OvenConfig()
    {
        example = new JsonBasicRecipe[] {
                new JsonBasicRecipe("minecraft:dirt", "minecraft:diamond"),
                new JsonBasicRecipe("kitchen:raw_bacon", "kitchen:bacon")
        };
        custom_oven_recipes = new JsonBasicRecipe[0];
    }
    
    public OvenConfig(File location, String name)
    {
        super(location, name);
    }
}
