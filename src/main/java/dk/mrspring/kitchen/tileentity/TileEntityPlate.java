package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.item.ItemSandwich;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class TileEntityPlate extends TileEntityBase
{
    List<ItemStack> items = new ArrayList<ItemStack>();
    boolean isFull = false;

    public boolean clicked(ItemStack stack)
    {
        if (stack != null)
        {
            if (!isFull && (!(stack.getItem() instanceof ItemSandwich) || this.items.size() == 0))
                return this.addStack(stack);
        } else
        {
            spawn(removeTopItem());
            return true;
        }
        return false;
    }

    public boolean addStack(ItemStack stack)
    {
        mark();
        ItemStack copy = stack.copy();
        copy.stackSize = 1;
        return this.items.add(copy);
    }

    public ItemStack removeTopItem()
    {
        if (!isEmpty())
        {
            mark();
            int size = items.size();
            if (size - 1 == 0) isFull = false;
            return items.remove(size - 1);
        } else return null;
    }

    public ItemStack toItemStack()
    {
        ItemStack plate = new ItemStack(KitchenBlocks.plate);
        NBTTagCompound plateCompound = new NBTTagCompound();
        this.writeItemsToNBT(plateCompound);
        plate.setTagInfo("PlateData", plateCompound);
        return plate;
    }

    public List<ItemStack> getItems()
    {
        return items;
    }

    public boolean isEmpty()
    {
        return getItems().size() == 0;
    }

    public void clear()
    {
        getItems().clear();
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

    @Override
    public int getNBTLevel()
    {
        return 1;
    }

    @Override
    public void readDataFromOldNBT(int oldLevel, int newLevel, NBTTagCompound compound)
    {
        warnNBTLevelChange(oldLevel, newLevel);
        readDataFromNBT(compound);
    }
}
