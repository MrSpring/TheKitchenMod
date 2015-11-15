package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.item.ItemSandwich;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileEntityPlate extends TileEntityBase
{
    protected List<ItemStack> items = new ArrayList<ItemStack>();
    protected boolean isFull = false;
    protected Random random = new Random();

    public boolean addItem(ItemStack stack)
    {
        if (stack == null || isFull) return false;
        ItemStack adding = stack.copy();
        adding.stackSize = 1;
        this.items.add(adding);
        if (adding.getItem() instanceof ItemSandwich) isFull = true;
        return true;
    }

    public ItemStack removeTopItem()
    {
        if (this.items.size() == 0) return null;
        int index = this.items.size() - 1;
        ItemStack removed = items.remove(index);
        if (isFull) isFull = false;
        return removed;
    }

    public List<ItemStack> getItems()
    {
        return items;
    }

    @Override
    public void writeDataToNBT(NBTTagCompound compound)
    {
        this.writeItemsToNBT(compound);
        compound.setBoolean("IsFull", this.isFull);
    }

    public void writeItemsToNBT(NBTTagCompound compound)
    {
        NBTTagList itemList = new NBTTagList();
        for (ItemStack item : this.items)
        {
            if (item != null)
            {
                NBTTagCompound itemCompound = new NBTTagCompound();
                itemCompound = item.writeToNBT(itemCompound);
                itemList.appendTag(itemCompound);
            }
        }
        compound.setTag("Items", itemList);
    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
        this.readItemsFromNBT(compound);
        this.isFull = compound.getBoolean("IsFull");
    }

    public void readItemsFromNBT(NBTTagCompound compound)
    {
        NBTTagList itemList = compound.getTagList("Items", 10);
        this.items = new ArrayList<ItemStack>();

        for (int i = 0; i < itemList.tagCount(); ++i)
        {
            if (itemList.getCompoundTagAt(i) != null)
            {
                NBTTagCompound item = itemList.getCompoundTagAt(i);
                ItemStack itemStack = ItemStack.loadItemStackFromNBT(item);
                this.items.add(itemStack);
            }
        }
    }
}
