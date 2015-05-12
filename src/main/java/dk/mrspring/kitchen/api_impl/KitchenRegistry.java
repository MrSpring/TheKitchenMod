package dk.mrspring.kitchen.api_impl;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.api.IKitchenRegistry;
import dk.mrspring.kitchen.api.RecipeType;
import dk.mrspring.kitchen.config.SandwichableConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 03-11-2014 for TheKitchenMod.
 */
public class KitchenRegistry implements IKitchenRegistry
{
    @Override
    public void makeSandwichable(String itemName, int healthAmount, boolean isBread, boolean hideInformation, boolean dropItem)
    {
        ModConfig.getSandwichConfig().makeSandwichable(new SandwichableConfig.SandwichableEntry(itemName, healthAmount, isBread, hideInformation, dropItem));
    }

    @Override
    public String addJam(String jamName, Item jamItem, int jamColor)
    {
        return "";
    }

    @Override
    public void linkItemToIngredient(String ingredientName, ItemStack linked)
    {

    }

    @Override
    public void addRecipe(String recipeType, ItemStack input, ItemStack output)
    {

    }

    @Override
    public void addRecipe(RecipeType type, ItemStack input, ItemStack output)
    {

    }
    /*public static void makeSandwichable(SandwichableConfig.SandwichableEntry entry)
    {
        ModConfig.getSandwichConfig().makeSandwichable(entry);
    }

    public static void makeSandwichable(Item item, int healAmount, boolean isBread, boolean hideInformation)
    {
        SandwichableConfig.SandwichableEntry entry = new SandwichableConfig.SandwichableEntry(GameRegistry.findUniqueIdentifierFor(item).toString(), healAmount, isBread);
        if (hideInformation)
            entry.hideInformation();
        makeSandwichable(entry);
    }

    public static void makeSandwichable(Item item, int healAmount, boolean isBread)
    {
        makeSandwichable(item, healAmount, isBread, false);
    }

    public static void makeSandwichable(Item item, int healAmount)
    {
        makeSandwichable(item, healAmount, false);
    }


    public static void linkItemToIngredient(Item item, int metadata, String ingredientName)
    {
        IngredientRegistry.getInstance().linkToIngredient(new IngredientRegistry.Stack(item, metadata), ingredientName);
    }

    public static void linkItemToIngredient(Item item, String ingredientName)
    {
        linkItemToIngredient(item, -1, ingredientName);
    }*/
}
