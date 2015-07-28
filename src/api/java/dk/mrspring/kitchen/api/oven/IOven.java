package dk.mrspring.kitchen.api.oven;

import dk.mrspring.kitchen.api.ICooking;
import dk.mrspring.kitchen.api.ISpawner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Konrad on 21-06-2015.
 */
public interface IOven extends ICooking, ISpawner
{
    boolean rightClicked(ItemStack clicked, EntityPlayer player);

    IOvenItem getItemAt(int slot);

    IOvenItem removeItemAt(int slot);

    boolean isSlotFree(int slot);

    boolean isOpen();

    boolean hasSpace(int size, boolean cons);

    NBTTagCompound getSpecialInfo(int slot);

    NBTTagCompound setSpecialInfo(int slot, NBTTagCompound compound);

    NBTTagCompound resetSpecialInfo(int slot);

    boolean hasFuel();

    void addFuel();

    boolean[] getFreeSlots();

    int[] getOccupyingSlots(int baseSlot);

    int getBurnTime();

    boolean isBurnt();
}
