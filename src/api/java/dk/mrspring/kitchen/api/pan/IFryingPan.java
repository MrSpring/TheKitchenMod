package dk.mrspring.kitchen.api.pan;

import dk.mrspring.kitchen.api.ICooking;
import dk.mrspring.kitchen.api.ISpecialTagInfo;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 14-05-2015.
 */
public interface IFryingPan extends ISpecialTagInfo, ICooking
{
    boolean rightClicked(ItemStack clicked, EntityPlayer player);

    IIngredient replaceIngredient(IIngredient newIngredient);

    boolean setIngredient(IIngredient ingredient);

    IIngredient getIngredient();

    EntityItem spawnItemInWorld(ItemStack stack);

    boolean checkIsFunctional();
}
