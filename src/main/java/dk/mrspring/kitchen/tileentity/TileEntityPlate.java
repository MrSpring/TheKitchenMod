package dk.mrspring.kitchen.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.item.ItemSandwich;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileEntityPlate extends TileEntityBase
{
    List<ItemStack> items = new ArrayList<ItemStack>();
    boolean isFull = false;

    @SideOnly(Side.CLIENT)
    public List<Position> positions;
    @SideOnly(Side.CLIENT)
    Random rand;

    public boolean addItem(ItemStack stack)
    {
        if (stack == null || isFull) return false;
        ItemStack adding = stack.copy();
        adding.stackSize = 1;
        this.items.add(adding);
        if (adding.getItem() instanceof ItemSandwich) isFull = true;
        if (worldObj != null && worldObj.isRemote) this.addRandomPosition();
        return true;
    }

    @SideOnly(Side.CLIENT)
    void addRandomPosition()
    {
        if (positions == null) positions = new ArrayList<Position>();
        if (positions.size() < items.size()) positions.add(new Position());
    }

    public ItemStack removeTopItem()
    {
        if (this.items.size() == 0) return null;
        int index = this.items.size() - 1;
        ItemStack removed = items.remove(index);
        if (isFull) isFull = false;
        if (worldObj.isRemote) this.removeRandomPosition(index);
        return removed;
    }

    @SideOnly(Side.CLIENT)
    void removeRandomPosition(int index)
    {
        positions.remove(index);
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
//        if (worldObj.isRemote) positions = new ArrayList<Position>();

        for (int i = 0; i < itemList.tagCount(); ++i)
        {
            if (itemList.getCompoundTagAt(i) != null)
            {
                NBTTagCompound item = itemList.getCompoundTagAt(i);
                ItemStack itemStack = ItemStack.loadItemStackFromNBT(item);
                this.addItem(itemStack);
            }
        }
        if (worldObj.isRemote) if (positions != null && positions.size() > items.size())
            positions.subList(items.size(), positions.size()).clear();
    }

    @SideOnly(Side.CLIENT)
    public class Position
    {
        public float xOffset;
        public float zOffset;

        Position()
        {
            if (rand == null) rand = new Random();
            this.xOffset = rand.nextFloat() - 0.5F;
            this.zOffset = rand.nextFloat() - 0.5F;
        }
    }
}
