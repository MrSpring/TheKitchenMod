package dk.mrspring.kitchen.api.pan;

import dk.mrspring.kitchen.api.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 14-05-2015.
 */
public interface IFryingPan extends ISpecialTagInfo, ICooking, ISpawner, ISoundPlayer
{
    boolean rightClicked(ItemStack clicked, EntityPlayer player);

    IIngredient replaceIngredient(IIngredient newIngredient);

    boolean setIngredient(IIngredient ingredient);

    IIngredient getIngredient();

    boolean checkIsFunctional();
}
