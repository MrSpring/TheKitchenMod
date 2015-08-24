package dk.mrspring.kitchen.util;

import dk.mrspring.kitchen.item.ItemJamJar;
import dk.mrspring.kitchen.item.ItemMixingBowl;
import dk.mrspring.kitchen.item.food.ItemIceCreamableBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by Konrad on 13-07-2015.
 */
public class ItemUtils
{
    public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2, boolean checkTag)
    {
//        System.out.println("Comparing: \"" + name(stack1) + "\", with: \"" + name(stack2) + "\".");
        if (ItemMixingBowl.areBowlsEqual(stack1, stack2)) return true;
        if (ItemJamJar.isJamEqual(stack1, stack2)) return true;
        if (ItemIceCreamableBase.hasEqualIceCream(stack1, stack2)) return true;
        if (equalStacks(stack1, stack2))
            return !checkTag || ItemStack.areItemStackTagsEqual(stack1, stack2);
        return false;
    }

    public static boolean equalStacks(ItemStack stack1, ItemStack stack2)
    {
        return !(stack1 == null || stack2 == null) && stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage();
    }

    public static boolean item(ItemStack stack, Item item)
    {
        return stack != null && stack.getItem() == item;
    }

    public static String name(ItemStack stack)
    {
        return stack != null ? stack.toString() : "null";
    }

    public static boolean itemDict(ItemStack stack, String oreDictionaryName)
    {
        if (stack != null)
        {
            int[] idsForStack = OreDictionary.getOreIDs(stack);
            int idForName = OreDictionary.getOreID(oreDictionaryName);
            return ArrayUtils.contains(idsForStack, idForName);
        } else return oreDictionaryName == null; // If stack and ore dict. name are both null, they are equal.
    }

    public static String getStringTag(ItemStack stack, String tagName)
    {
        if (stack == null || !stack.hasTagCompound()) return null;
        else return stack.getTagCompound().getString(tagName);
    }

    public static void setStringTag(ItemStack stack, String tagName, String tagValue)
    {
        if (stack == null) return;
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null) stack.setTagCompound(compound = new NBTTagCompound());
        compound.setString(tagName, tagValue);
    }
}
