package dk.mrspring.kitchen.api.grinder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 02-06-2015.
 */
public interface IGrinderOpeningHandler
{
    boolean isForItem(IGrinder grinder, ItemStack used, EntityPlayer player);

    void onAdded(IGrinder grinder, ItemStack added, EntityPlayer player);

    int getDone(IGrinder grinder, ItemStack used, EntityPlayer player);

    int getColor(IGrinder grinder, ItemStack processing, EntityPlayer player);
}
