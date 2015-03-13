package dk.mrspring.kitchen.comp.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.api.ItemFilter;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 11-03-2015.
 */
public class NEIKitchenConfig implements IConfigureNEI
{
    private static final List<Item> BANNED = new ArrayList<Item>();

    static
    {
        BANNED.add(KitchenItems.jam_apple);
        BANNED.add(KitchenItems.jam_cocoa);
        BANNED.add(KitchenItems.jam_peanut);
        BANNED.add(KitchenItems.jam_strawberry);
    }

    @Override
    public void loadConfig()
    {
        ItemStack newSandwich = KitchenItems.basic_sandwich.copy();

        NBTTagList layers = new NBTTagList();
        NBTTagCompound itemCompound = new NBTTagCompound();
        new ItemStack(KitchenItems.bread_slice).writeToNBT(itemCompound);
        layers.appendTag(itemCompound);
        itemCompound = new NBTTagCompound();
        new ItemStack(KitchenItems.bacon).writeToNBT(itemCompound);
        layers.appendTag(itemCompound);
        itemCompound = new NBTTagCompound();
        new ItemStack(KitchenItems.lettuce_leaf).writeToNBT(itemCompound);
        layers.appendTag(itemCompound);
        itemCompound = new NBTTagCompound();
        new ItemStack(KitchenItems.tomato_slice).writeToNBT(itemCompound);
        layers.appendTag(itemCompound);
        itemCompound = new NBTTagCompound();
        new ItemStack(KitchenItems.bread_slice).writeToNBT(itemCompound);
        layers.appendTag(itemCompound);

        newSandwich.setTagInfo("SandwichLayers", layers);
        API.addItemListEntry(newSandwich);
        API.addItemFilter(new ItemFilter.ItemFilterProvider()
        {
            @Override
            public ItemFilter getFilter()
            {
                return new ItemFilter()
                {
                    @Override
                    public boolean matches(ItemStack item)
                    {
                        return !BANNED.contains(item.getItem());
                    }
                };
            }
        });
//        API.
    }

    @Override
    public String getName()
    {
        return ModInfo.name;
    }

    @Override
    public String getVersion()
    {
        return ModInfo.version;
    }
}
