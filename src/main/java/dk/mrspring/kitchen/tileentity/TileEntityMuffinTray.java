package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created on 04-10-2015 for TheKitchenMod.
 */
public class TileEntityMuffinTray extends TileEntity
{
    public static final String ITEM_LIST = "ItemList", ITEM_SLOT = "Slot";

    ItemStack[] muffins = new ItemStack[6];

    public void onPlaced(ItemStack placed)
    {

    }

    public boolean rightClick(ItemStack clicked, EntityPlayer clicker, float clickX, float clickZ)
    {
        int slot = getSlotAt(clickX, clickZ);
        System.out.println(slot);
        if (slot >= 0 && slot < muffins.length)
            if (getInSlot(slot) == null)
            {
                if (ItemUtils.item(clicked, KitchenItems.uncooked_muffin))
                {
                    setInSlot(slot, clicked.copy());
                    clicked.stackSize--;
                    return true;
                }
            } else
            {
                BlockContainerBase.spawnItemInWorld(getInSlot(slot).copy(), getWorldObj(), xCoord, yCoord, zCoord);
                setInSlot(slot, null);
                return true;
            }

        return false;
    }

    public ItemStack getInSlot(int slot)
    {
        return muffins[slot];
    }

    public void setInSlot(int slot, ItemStack stack)
    {
        this.muffins[slot] = stack;
    }

    public int getSlotAt(float x, float z)
    {
        float p = 0.0625F;
        switch (getBlockMetadata())
        {
            case 2:
                x = 1F - x;
                z = 1F - z;
            case 0:
                x -= 2 * p;
                x *= (1F + 4 * p);
                int vert = (int) (x / 0.3333F);
                if (z > 9.5F * p) vert += 3;
                System.out.println(x + ", " + z);
                return vert;
            case 3:
                x = 1F - x;
                z = 1F - z;
            case 1:
                vert = (int) (z / 0.3333F);
                if (x < (16F - 9.5F) * p) vert += 3;
                System.out.println(x + ", " + z);
                return vert;
            default:
                return -1;

        }
    }

    public void clearSlots()
    {
        for (int i = 0; i < muffins.length; i++)
            muffins[i] = null;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.clearSlots();
        NBTTagList itemList = compound.getTagList(ITEM_LIST, 10);
        for (int i = 0; i < itemList.tagCount(); i++)
        {
            NBTTagCompound itemCompound = itemList.getCompoundTagAt(i);
            int slot = itemCompound.getInteger(ITEM_SLOT);
            ItemStack stack = ItemStack.loadItemStackFromNBT(itemCompound);
            setInSlot(slot, stack);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < muffins.length; i++)
        {
            ItemStack inSlot = getInSlot(i);
            if (inSlot != null)
            {
                NBTTagCompound itemCompound = new NBTTagCompound();
                inSlot.writeToNBT(itemCompound);
                itemCompound.setInteger(ITEM_SLOT, i);
                itemList.appendTag(itemCompound);
            }
        }
        compound.setTag(ITEM_LIST, itemList);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 2, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.func_148857_g());
    }

    public int getMuffinCount()
    {
        return muffins.length;
    }
}
