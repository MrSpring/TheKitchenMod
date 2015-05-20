package dk.mrspring.kitchen.api.pan;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Konrad on 14-05-2015.
 */
public interface IFryingPan
{
    boolean rightClicked(ItemStack clicked, EntityPlayer player);

    IIngredient replaceIngredient(IIngredient newIngredient);

    boolean setIngredient(IIngredient ingredient);

    IIngredient getIngredient();

    NBTTagCompound setSpecialInfo(NBTTagCompound newCompound);

    NBTTagCompound getSpecialInfo();

    NBTTagCompound resetSpecialInfo();

    boolean isCooking();

    int getCookTime();

    int getDoneTime();

    boolean isFinished();

    EntityItem spawnItemInWorld(ItemStack stack);
}
