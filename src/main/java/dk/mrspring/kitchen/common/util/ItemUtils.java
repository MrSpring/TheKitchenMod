package dk.mrspring.kitchen.common.util;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.common.item.IComparable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
public class ItemUtils
{
    public static final int END = 0, BYTE = 1, SHORT = 2, INT = 3, LONG = 4, FLOAT = 5, DOUBLE = 6, BYTE_ARRAY = 7,
            STRING = 8, LIST = 9, COMPOUND = 10, INT_ARRAY = 11;

    public static boolean equalStacks(ItemStack stack1, ItemStack stack2)
    {
        if (stack1.getItem() instanceof IComparable)
            return ((IComparable) stack1.getItem()).compareStacks(stack1, stack2);
        else if (stack2.getItem() instanceof IComparable)
            return ((IComparable) stack2.getItem()).compareStacks(stack2, stack1);
        else return item(stack1, stack2) && equalDamage(stack1, stack2) && equalTags(stack1, stack2);
    }

    public static boolean notEmpty(ItemStack stack)
    {
        return stack != null && stack.getItem() != null && stack.stackSize > 0;
    }

    public static boolean areContainerStacksEqual(ItemStack stack1, ItemStack stack2, Item item, String tag)
    {
        if (!items(item, stack1, stack2)) return false;
        String type1 = getString(stack1, tag), type2 = getString(stack2, tag);
        return !(type1 == null || type2 == null) && type1.equals(type2);
    }

    public static boolean equalDamage(ItemStack stack1, ItemStack stack2)
    {
        return stack1 != null && stack2 != null && stack1.getItemDamage() == stack2.getItemDamage();
    }

    public static boolean equalTags(ItemStack stack1, ItemStack stack2)
    {
        if (stack1.hasTagCompound() && stack2.hasTagCompound())
        {
            return stack1.getTagCompound().equals(stack2.getTagCompound());
        } else return !stack1.hasTagCompound() && !stack2.hasTagCompound();
    }

    public static boolean item(ItemStack stack, Block block)
    {
        return item(stack, ItemBlock.getItemFromBlock(block));
    }

    public static boolean item(ItemStack stack, Item item)
    {
        return stack != null && stack.getItem() == item;
    }

    public static boolean item(ItemStack stack1, ItemStack stack2)
    {
        return stack1 != null && stack2 != null && stack1.getItem() == stack2.getItem();
    }

    public static boolean items(Item item, ItemStack... stacks)
    {
        for (ItemStack stack : stacks) if (!item(stack, item)) return false;
        return true;
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

    public static boolean canDrop(ItemStack stack) // TODO: Get rid of item comparison
    {
        if (item(stack, Kitchen.items.mixing_bowl)) return false;
        if (item(stack, Kitchen.items.jam_jar)) return false;
        return true;
    }

    public static String name(ItemStack stack, String fallback)
    {
        return stack != null ? stack.toString() : fallback;
    }

    public static String name(ItemStack stack)
    {
        return name(stack, "null");
    }

    public static void decrDamage(ItemStack stack, int amount)
    {
        incrDamage(stack, -amount);
    }

    public static void incrDamage(ItemStack stack, int amount)
    {
        setDamage(stack, damage(stack) + amount);
    }

    /**
     * Decreases the items meta and returns the new value, but makes sure the damage may not get lower than min.
     *
     * @param stack  The {@link ItemStack} to damage.
     * @param amount The amount to decrease.
     * @param min    The minimum damage value.
     * @return Returns the new damage value.
     */
    public static int damage(ItemStack stack, int amount, int min)
    {
        return damage(stack, Math.min(amount, damage(stack) - min));
    }

    /**
     * Decreases the items meta and returns the new value.
     *
     * @param stack  The {@link ItemStack} to damage.
     * @param amount The amount to decrease.
     * @return Returns the new damage value.
     */
    public static int damage(ItemStack stack, int amount)
    {
        decrDamage(stack, amount);
        return damage(stack);
    }

    /**
     * @param stack The {@link ItemStack} to get the damage from.
     * @return Returns the damage value of the passed {@link ItemStack}.
     */
    public static int damage(ItemStack stack)
    {
        return stack.getItemDamage();
    }

    public static void setDamage(ItemStack stack, int newDamage)
    {
        stack.setItemDamage(newDamage);
    }

    public static void decrSize(ItemStack stack, int amount)
    {
        incrSize(stack, -amount);
    }

    public static void incrSize(ItemStack stack, int amount)
    {
        setSize(stack, size(stack) + amount);
    }

    public static int size(ItemStack stack, int amount, int min)
    {
        return damage(stack, Math.min(amount, size(stack) - min));
    }

    public static int size(ItemStack stack, int amount)
    {
        decrSize(stack, amount);
        return size(stack);
    }

    public static int size(ItemStack stack)
    {
        return stack.stackSize;
    }

    public static void setSize(ItemStack stack, int newSize)
    {
        stack.stackSize = Math.max(0, newSize);
    }

    public static ItemStack fromNBT(NBTTagCompound compound)
    {
        if (compound == null) return null;
        else return ItemStack.loadItemStackFromNBT(compound);
    }

    public static NBTTagCompound toNBT(ItemStack stack)
    {
        return stack.writeToNBT(new NBTTagCompound());
    }

    public static ItemStack copy(ItemStack stack, int newStackSize, int newDamage)
    {
        ItemStack copy = stack.copy();
        copy.stackSize = newStackSize;
        setDamage(stack, newDamage);
        return copy;
    }

    public static ItemStack copy(ItemStack stack, int newStackSize)
    {
        ItemStack copy = stack.copy();
        copy.stackSize = newStackSize;
        return copy;
    }

    public static ItemStack copy(ItemStack stack)
    {
        return stack.copy();
    }

    public static String getString(ItemStack stack, String name)
    {
        return getCompound(stack).getString(name);
    }

    public static void setString(ItemStack stack, String name, String value)
    {
        getCompound(stack).setString(name, value);
    }

    public static boolean hasString(ItemStack stack, String name)
    {
        return getCompound(stack).hasKey(name, STRING);
    }

    public static NBTTagCompound getCompound(ItemStack stack)
    {
        if (!stack.hasTagCompound()) stack.setTagCompound(newCompound());
        return stack.getTagCompound();
    }

    public static NBTTagCompound getCompound(ItemStack stack, String name)
    {
        NBTTagCompound compound = getCompound(stack);
        if (!compound.hasKey(name, COMPOUND)) compound.setTag(name, newCompound());
        return compound.getCompoundTag(name);
    }

    public static void removeTag(ItemStack stack, String name)
    {
        getCompound(stack).removeTag(name);
    }

    private static NBTTagCompound newCompound()
    {
        return new NBTTagCompound();
    }

    public static ItemStack newStack(Item item, int size, int damage)
    {
        return new ItemStack(item, size, damage);
    }

    public static ItemStack newStack(Item item, int size)
    {
        return newStack(item, size, 0);
    }

    public static ItemStack newStack(Item item)
    {
        return newStack(item, 1);
    }

    public static ItemStack newStack(Block block, int size, int damage)
    {
        return new ItemStack(block, size, damage);
    }

    public static ItemStack newStack(Block block, int size)
    {
        return newStack(block, size, 0);
    }

    public static ItemStack newStack(Block block)
    {
        return newStack(block, 1);
    }
}
