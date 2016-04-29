package dk.mrspring.kitchen.common.api.oven.item;

import dk.mrspring.kitchen.Kitchen;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created on 03-04-2016 for TheKitchenMod.
 */
public final class OvenItemStack
{
    private static final String OVEN_ITEM_ID = "OIId";
    private static final String COMPOUND_INFO = "CompoundInfo";

    OvenItem item;
    String name;
    public NBTTagCompound compound;

    public OvenItemStack(OvenItem item)
    {
        this(item, Kitchen.ovenItems.reverseGet(item));
    }

    public OvenItemStack(String name)
    {
        this(Kitchen.ovenItems.get(name), name);
    }

    public OvenItemStack(OvenItem item, String name)
    {
        this.setItem(item, name);
    }

    public boolean hasCompound()
    {
        return compound == null;
    }

    public NBTTagCompound getCompound()
    {
        if (compound == null) setCompound(new NBTTagCompound());
        return compound;
    }

    public void setCompound(NBTTagCompound compound)
    {
        this.compound = compound;
    }

    public void setItem(OvenItem item, String name)
    {
        this.item = item;
        this.name = name;
    }

    public OvenItemStack copy()
    {
        OvenItemStack stack = new OvenItemStack(item, name);
        stack.setCompound(compound);
        return stack;
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString(OVEN_ITEM_ID, name);
        if (this.compound != null)
        {
            System.out.println("Setting compound");
            compound.setTag(COMPOUND_INFO, this.compound);
        }
    }

    public static OvenItemStack fromNBT(NBTTagCompound compound)
    {
        String id = compound.getString(OVEN_ITEM_ID);
        OvenItem item = Kitchen.ovenItems.get(id);
        if (item != null)
        {
            OvenItemStack stack = new OvenItemStack(item, id);
            if (compound.hasKey(COMPOUND_INFO, 10))
                stack.setCompound(compound.getCompoundTag(COMPOUND_INFO));
            return stack;
        } else return null;
    }

    public OvenItem getItem()
    {
        return item;
    }
}
