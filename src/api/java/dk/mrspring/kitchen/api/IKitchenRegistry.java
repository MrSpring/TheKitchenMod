package dk.mrspring.kitchen.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 13-05-2015.
 */
public interface IKitchenRegistry
{
    /***
     * Makes an Item Sandwichable, so it can be used in a Sandwich.
     * @param itemName
     * @param healthAmount
     * @param isBread
     * @param hideInformation
     * @param dropItem
     */
    void makeSandwichable(ItemStack item, int healthAmount, boolean isBread, boolean hideInformation, boolean dropItem);

    String addJam(String jamName, Item jamItem, int jamColor);

    void linkItemToIngredient(String ingredientName, ItemStack linked);

    void addRecipe(String recipeType, ItemStack input, ItemStack output);

    void addRecipe(RecipeType type, ItemStack input, ItemStack output);
}
