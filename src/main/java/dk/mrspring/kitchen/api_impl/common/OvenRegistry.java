package dk.mrspring.kitchen.api_impl.common;

import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.api.oven.IOvenItemRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 22-06-2015.
 */
public class OvenRegistry implements IOvenItemRegistry
{
    private static OvenRegistry ourInstance = new OvenRegistry();

    public static OvenRegistry getInstance()
    {
        return ourInstance;
    }

    private OvenRegistry()
    {
    }

    @Override
    public void registerOvenItem(IOvenItem item)
    {

    }

    @Override
    public IOvenItem getOvenItemFor(IOven oven, ItemStack stack, EntityPlayer player)
    {
        return null;
    }

    @Override
    public IOvenItem getOvenItemFromName(String itemName)
    {
        return null;
    }
}
