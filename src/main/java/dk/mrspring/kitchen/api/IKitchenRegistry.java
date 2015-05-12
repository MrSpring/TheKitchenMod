package dk.mrspring.kitchen.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 13-05-2015.
 */
public interface IKitchenRegistry
{
    void makeSandwichable(String itemName, int healthAmount, boolean isBread, boolean hideInformation, boolean dropItem);

    String addJam(String jamName, Item jamItem, int jamColor);

    void linkItemToIngredient(String ingredientName, ItemStack linked);

    void addRecipe(String recipeType, ItemStack input, ItemStack output);

    void addRecipe(RecipeType type, ItemStack input, ItemStack output);
}
