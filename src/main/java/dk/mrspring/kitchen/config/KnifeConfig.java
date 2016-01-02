package dk.mrspring.kitchen.config;

import dk.mrspring.kitchen.config.wrapper.JsonBasicRecipe;
import dk.mrspring.kitchen.config.wrapper.JsonCraftingRecipe;
import dk.mrspring.kitchen.config.wrapper.JsonItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;

import java.io.File;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class KnifeConfig extends BaseConfig
{
    private static final JsonCraftingRecipe DEFAULT_KNIFE_RECIPE = new JsonCraftingRecipe(
            new String[]{"I ", " S"},
            new char[]{'I', 'S'},
            new JsonItemStack[]{new JsonItemStack("iron_ingot"), new JsonItemStack("stick")});

    private static final JsonCraftingRecipe IRON_TORCH = new JsonCraftingRecipe(
            new String[]{"I", "S"},
            new char[]{'I', 'S'},
            new JsonItemStack[]{new JsonItemStack("iron_ingot"), new JsonItemStack("stick")});

    private static final JsonCraftingRecipe LINE = new JsonCraftingRecipe(
            new String[]{"IS"},
            new char[]{'I', 'S'},
            new JsonItemStack[]{new JsonItemStack("iron_ingot"), new JsonItemStack("stick")});

    private static final JsonCraftingRecipe CUSTOM_TEMPLATE = new JsonCraftingRecipe(
            new String[]{"  I", " I ", "S  "},
            new char[]{'I', 'S'},
            new JsonItemStack[]{new JsonItemStack("iron_ingot"), new JsonItemStack("stick")});

    public int knife_recipe_preset = 0;
    public JsonCraftingRecipe current_knife_recipe = CUSTOM_TEMPLATE.copy();
    public JsonBasicRecipe[] custom_knife_recipes = new JsonBasicRecipe[0];

    public KnifeConfig()
    {
        knife_recipe_preset = 0;
        current_knife_recipe = CUSTOM_TEMPLATE.copy();
        custom_knife_recipes = new JsonBasicRecipe[0];
    }

    public JsonCraftingRecipe getKnifeRecipe()
    {
        switch (knife_recipe_preset)
        {
            case 1:
                return IRON_TORCH.copy();
            case 2:
                return LINE.copy();
            case 3:
                return current_knife_recipe.copy();
            case 0:
            default:
                return DEFAULT_KNIFE_RECIPE.copy();
        }
    }

    public static ShapedRecipes getFallbackRecipe(ItemStack output)
    {
        return DEFAULT_KNIFE_RECIPE.toRecipe(output);
    }

    public KnifeConfig(File location, String name)
    {
        super(location, name);
    }
}
