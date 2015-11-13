package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

/**
 * Created on 04-10-2015 for TheKitchenMod.
 */
public class TileEntityMuffinTray extends TileEntity
{
    ItemStack[] muffins = new ItemStack[6];

    public void onPlaced(ItemStack placed)
    {

    }

    public boolean rightClick(ItemStack clicked, EntityPlayer clicker, float clickX, float clickZ)
    {
        int slot = getSlotAt(clickX, clickZ);
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

    private ItemStack getInSlot(int slot)
    {
        return muffins[slot];
    }

    private void setInSlot(int slot, ItemStack stack)
    {
        this.muffins[slot] = stack;
    }

    public int getSlotAt(float x, float z)
    {
        switch (getBlockMetadata())
        {
            case 2:
                x = 1F - x;
                z = 1F - z;
            case 0:
                int vert = (int) (x / 0.3333F);
                if (z > 0.5F) vert += 3;
                return vert;
            case 3:
                x = 1F - x;
                z = 1F - z;
            case 1:
                vert = (int) (z / 0.3333F);
                if (x < 0.5F) vert += 3;
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
        NBTTagList itemList = compound.getTagList("ItemList", 10);
        for (int i = 0; i < itemList.tagCount(); i++)
        {
            NBTTagCompound itemCompound = itemList.getCompoundTagAt(i);
            int slot = itemCompound.getInteger("Slot");
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
                itemCompound.setInteger("Slot", i);
                itemList.appendTag(itemCompound);
            }
        }
        compound.setTag("ItemList", itemList);
    }
}
