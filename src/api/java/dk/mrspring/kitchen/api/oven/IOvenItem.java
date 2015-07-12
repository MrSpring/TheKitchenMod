package dk.mrspring.kitchen.api.oven;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 21-06-2015.
 */
public interface IOvenItem
{
    String getName();

    String getDisplayName();

    boolean isForItem(IOven oven, ItemStack item, EntityPlayer player, boolean[] freeSlots);

    boolean canAdd(IOven oven, ItemStack adding, EntityPlayer player, boolean[] freeSlots);

    void onAdded(IOven oven, ItemStack added, EntityPlayer player, int[] slots);

    boolean readyToCook(IOven oven, int slot);

    int getSize(IOven oven);

    boolean consecutive(IOven oven);

    int getCookTime(IOven oven);

    boolean onRightClicked(IOven oven, ItemStack clicked, EntityPlayer player, int slot);

    boolean canBeRemoved(IOven oven, ItemStack clicked, EntityPlayer player, int slot);

    ItemStack onRemoved(IOven oven, ItemStack clicked, EntityPlayer player, int slot);
}
