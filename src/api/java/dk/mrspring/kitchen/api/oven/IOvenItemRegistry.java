package dk.mrspring.kitchen.api.oven;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 22-06-2015.
 */
public interface IOvenItemRegistry
{
    void registerOvenItem(IOvenItem item);

    IOvenItem getOvenItemFor(IOven oven, ItemStack stack, EntityPlayer player);

    IOvenItem getOvenItemFromName(String itemName);
}
