package dk.mrspring.kitchen.api_impl.common.oven;

import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 11-07-2015.
 */
public class BasicOvenItem implements IOvenItem
{
    @Override
    public String getName()
    {
        return "empty";
    }

    @Override
    public String getDisplayName()
    {
        return "Empty";
    }

    @Override
    public boolean isForItem(IOven oven, ItemStack item, EntityPlayer player, boolean[] freeSlots)
    {
        return false;
    }

    @Override
    public boolean canAdd(IOven oven, ItemStack adding, EntityPlayer player, boolean[] freeSlots)
    {
        return false;
    }

    @Override
    public void onAdded(IOven oven, ItemStack added, EntityPlayer player, int[] slots)
    {

    }

    @Override
    public boolean readyToCook(IOven oven, int slot)
    {
        return false;
    }

    @Override
    public int getSize(IOven oven)
    {
        return 0;
    }

    @Override
    public boolean consecutive(IOven oven)
    {
        return false;
    }

    @Override
    public int getCookTime(IOven oven)
    {
        return 0;
    }

    @Override
    public boolean onRightClicked(IOven oven, ItemStack clicked, EntityPlayer player, int slot)
    {
        return false;
    }

    @Override
    public boolean canBeRemoved(IOven oven, ItemStack clicked, EntityPlayer player, int slot)
    {
        return false;
    }

    @Override
    public ItemStack onRemoved(IOven oven, ItemStack clicked, EntityPlayer player, int slot)
    {
        return null;
    }
}
