package dk.mrspring.kitchen.api_impl.common;

import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.api.oven.IOvenItemRegistry;
import dk.mrspring.kitchen.api_impl.common.oven.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 22-06-2015.
 */
public class OvenRegistry implements IOvenItemRegistry
{
    private static final OvenRegistry ourInstance = new OvenRegistry();
    private static final IOvenItem defaultItem = new BasicOvenItem();
    private List<IOvenItem> items = new ArrayList<IOvenItem>();

    private OvenRegistry()
    {
        registerOvenItem(new MuffinTrayItem());
        registerOvenItem(new BurgerBunOvenitem());
        registerOvenItem(new RecipeOvenItem());
        registerOvenItem(new FuelOvenItem());
    }

    public static OvenRegistry getInstance()
    {
        return ourInstance;
    }

    @Override
    public void registerOvenItem(IOvenItem item)
    {
        if (item != null) items.add(item);
    }

    @Override
    public IOvenItem getOvenItemFor(IOven oven, ItemStack stack, EntityPlayer player)
    {
        for (IOvenItem item : items)
            if (item.isForItem(oven, stack, player, oven.getFreeSlots())) return item;
        return defaultItem;
    }

    @Override
    public IOvenItem getOvenItemFromName(String itemName)
    {
        for (IOvenItem item : items)
            if (item.getName().equals(itemName)) return item;
        return null;
    }
}
