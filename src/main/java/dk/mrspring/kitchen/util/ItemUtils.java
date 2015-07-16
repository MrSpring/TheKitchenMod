package dk.mrspring.kitchen.util;

import dk.mrspring.kitchen.item.ItemJamJar;
import dk.mrspring.kitchen.item.ItemMixingBowl;
import dk.mrspring.kitchen.item.food.ItemIceCreamableBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Map;

/**
 * Created by Konrad on 13-07-2015.
 */
public class ItemUtils
{
    public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2, boolean checkTag)
    {
        if (ItemMixingBowl.areBowlsEqual(stack1, stack2)) return true;
        if (ItemJamJar.isJamEqual(stack1, stack2)) return true;
        if (ItemIceCreamableBase.hasEqualIceCream(stack1, stack2)) return true;
        if (ItemStack.areItemStacksEqual(stack1, stack2))
            return !checkTag || ItemStack.areItemStackTagsEqual(stack1, stack2);
        else return false;
    }

    public static boolean item(ItemStack stack, Item item)
    {
        return stack != null && stack.getItem() == item;
    }

    public static void populateStackCompound(ItemStack stack, Map<String, Object> tag)
    {
        NBTTagCompound compound = toCompound(tag);
        stack.setTagCompound(compound);
    }

    private static NBTTagCompound toCompound(Map<String, Object> tag)
    {
        NBTTagCompound compound = new NBTTagCompound();

    }

    private static NBTBase nbtThat(Object object)
    {
        if (object instanceof Number) return new NBTTagInt((Integer) object);
        if (object instanceof String[])
        {
            String[] strings = (String[]) object;
            NBTTagList list = new NBTTagList();
            for (String string : strings) list.appendTag(new NBTTagString(string));
            return list;
        }
        if (object instanceof Number[]) return new NBTTagIntArray(ArrayUtils.toPrimitive((Integer[]) object));
        if (object instanceof Map)
        {
            Map<String, Object> map = (Map<String, Object>) object;
            NBTTagCompound compound = new NBTTagCompound();
            for (Map.Entry<String, Object> entry : map.entrySet())
            {
                NBTBase nbt = nbtThat(entry.getValue());
                compound.setTag(entry.getKey());
            }
        }
    }
}
