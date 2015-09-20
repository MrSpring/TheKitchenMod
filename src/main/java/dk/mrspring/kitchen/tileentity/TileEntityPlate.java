package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.item.ItemSandwich;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.Random;

public class TileEntityPlate extends TileEntity
{
    protected ArrayList<ItemStack> items = new ArrayList<ItemStack>();
    protected boolean isFull = false;
    protected Random random = new Random();

    public boolean addItem(ItemStack itemStack)
    {
        if (itemStack != null)
            if (itemStack.getItem() != null)
                if (itemStack.getItem() instanceof ItemSandwich)
                    if (!this.isFull && this.items.size() == 0)
                    {
                        ItemStack item = itemStack.copy();
                        item.stackSize = 1;

                        this.items.add(item);
                        this.isFull = true;
                        return true;
                    } else
                        return false;
                else
                {
                    if (!this.isFull)
                    {
                        ItemStack item = itemStack.copy();
                        item.stackSize = 1;

                        this.items.add(item);
                        return true;
                    } else
                        return false;
                }
            else
                return false;
        else
            return false;
    }

    public ItemStack removeTopItem()
    {
        int index = this.items.size();

        if (index != 0)
        {
            ItemStack item = this.items.get(index - 1);

            if (item != null)
                if (item.getItem() != null)
                {
                    this.items.remove(index - 1);

                    if (this.isFull)
                        this.isFull = false;

                    return item;
                } else
                    return null;
            else
                return null;
        } else
            return null;
    }

    public ArrayList<ItemStack> getItems()
    {
        return items;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
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
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
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
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.func_148857_g());
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 2, compound);
    }
}
