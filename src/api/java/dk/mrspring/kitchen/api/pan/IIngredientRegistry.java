package dk.mrspring.kitchen.api.pan;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 14-05-2015.
 */
public interface IIngredientRegistry
{
    void registerIngredient(IIngredient ingredient);

    IIngredient getIngredientFor(IFryingPan fryingPan, ItemStack stack, EntityPlayer player);

    IIngredient getIngredientFromName(String ingredientName);
}
