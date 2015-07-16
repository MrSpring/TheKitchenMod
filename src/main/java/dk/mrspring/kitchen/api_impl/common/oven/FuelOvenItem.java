package dk.mrspring.kitchen.api_impl.common.oven;

import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 12-07-2015.
 */
public class FuelOvenItem implements IOvenItem
{
    @Override
    public String getName()
    {
        return "fuel";
    }

    @Override
    public String getDisplayName()
    {
        return "Fuel";
    }

    @Override
    public boolean isForItem(IOven oven, ItemStack item, EntityPlayer player, boolean[] freeSlots)
    {
        return item.getItem() == Items.coal;
    }

    @Override
    public boolean canAdd(IOven oven, ItemStack adding, EntityPlayer player, boolean[] freeSlots)
    {
        return true;
    }

    @Override
    public void onAdded(IOven oven, ItemStack added, EntityPlayer player, int[] slots)
    {
        oven.addFuel();
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
