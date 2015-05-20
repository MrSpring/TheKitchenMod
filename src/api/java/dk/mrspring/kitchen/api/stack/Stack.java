package dk.mrspring.kitchen.api.stack;

import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.config.wrapper.JsonItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 13-05-2015.
 */

/**
 * An ItemStack wrapper class.
 */
public class Stack
{
    public int metadata;
    public Item item;
    public int amount;

    /**
     * @param item     The item associated with this Stack.
     * @param metadata The metadata associated with this Stack. Use -1 if any can be used.
     * @param amount   The amount stored in this Stack.
     */
    public Stack(Item item, int metadata, int amount)
    {
        this.metadata = metadata;
        this.item = item;
        this.amount = amount;
    }

    public Stack(Item item, int metadata)
    {
        this(item, metadata, 1);
    }

    /**
     * Creates a Stack based on an {@link net.minecraft.item.ItemStack ItemStack}.
     *
     * @param stack The {@link net.minecraft.item.ItemStack ItemStack} to copy data from.
     */
    public Stack(ItemStack stack)
    {
        this(stack, true);
    }

    public Stack(JsonItemStack stack)
    {
        this(stack.toItemStack());
    }

    /**
     * Creates a Stack based on an {@link net.minecraft.item.ItemStack ItemStack}.
     *
     * @param stack        The {@link net.minecraft.item.ItemStack ItemStack} to copy data from.
     * @param copyMetadata Whether the metadata from stack should be used. If false any will be accepted.
     */
    public Stack(ItemStack stack, boolean copyMetadata)
    {
        this(stack.getItem(), copyMetadata ? stack.getItemDamage() : -1);
    }

    @Override
    public String toString()
    {
        return item.getUnlocalizedName() + ":" + String.valueOf(metadata);
    }

    /**
     * @return Returns an ItemStack with the Stack's item and metadata, with a stackSize of 1.
     */
    public ItemStack toItemStack()
    {
        return new ItemStack(item, amount, metadata);
    }

    public JsonItemStack toJsonStack()
    {
        return new JsonItemStack(this.toItemStack());
    }

    /**
     * @param that  The stack to compare this to.
     * @param types What the two stacks should be compared to.
     * @return Returns true if the two Stacks are the same based on the set types.
     */
    public boolean areStacksEqual(Stack that, Type... types)
    {
        for (Type type : types)
        {
            switch (type)
            {
                case ITEM:
                    if (this.item != that.item)
                        return false;
                    break;
                case METADATA:
                    if (!((that.metadata == -1 || this.metadata == -1) || that.metadata == this.metadata))
                        return false;
                    break;
                case AMOUNT:
                    if (this.amount != that.amount)
                        return false;
                    break;
            }
        }
        return true;
    }

    public enum Type
    {
        ITEM,
        METADATA,
        AMOUNT
    }

    /*@Override
    public boolean equals(Object that)
    {
        if (that instanceof Stack)
        {
//            ModLogger.print(ModLogger.DEBUG, "Comparing: \"" + this.toString() + "\" to: \"" + that.toString() + "\"");
            Stack objStack = (Stack) that;
            return ((objStack.metadata == -1 || this.metadata == -1) || objStack.metadata == this.metadata) &&
                    objStack.item == this.item;
        } else return super.equals(that);
    }*/
}
