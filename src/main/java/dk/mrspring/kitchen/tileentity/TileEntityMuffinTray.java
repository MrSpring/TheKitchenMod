package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.item.block.ItemBlockMuffinTray;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created on 04-10-2015 for TheKitchenMod.
 */
public class TileEntityMuffinTray extends TileEntityBase
{
    ItemBlockMuffinTray.Tray tray;

    public void onPlaced(ItemStack placed)
    {
        this.tray = new ItemBlockMuffinTray.Tray(placed);
    }

    public ItemStack makeDropStack()
    {
        ItemStack stack = new ItemStack(getBlockType(), 1);
        tray.writeToStack(stack);
        return stack;
    }

    public boolean rightClick(ItemStack clicked, EntityPlayer clicker, float clickX, float clickZ)
    {
        int slot = getSlotAt(clickX, clickZ);
        System.out.println(slot);
        if (slot >= 0 && slot < tray.getMuffinCount())
            if (getInSlot(slot) == null)
            {
                if (ItemUtils.item(clicked, KitchenItems.raw_muffin))
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

    public int amountFilled()
    {
        int amount = 0;
        for (ItemStack stack : tray.getMuffins()) if (stack != null) amount++;
        return amount;
    }

    public ItemStack getInSlot(int slot)
    {
        return tray.getInSlot(slot);
    }

    public void setInSlot(int slot, ItemStack stack)
    {
        this.tray.setInSlot(slot, stack);
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
                return vert;
            case 3:
                x = 1F - x;
                z = 1F - z;
            case 1:
                vert = (int) (z / 0.3333F);
                if (x < (16F - 9.5F) * p) vert += 3;
                return vert;
            default:
                return -1;

        }
    }

    public void clearSlots()
    {
        this.tray.clearSlots();
    }

    public int getMuffinCount()
    {
        return tray.getMuffinCount();
    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
        this.tray = new ItemBlockMuffinTray.Tray(compound);
    }

    @Override
    public void writeDataToNBT(NBTTagCompound compound)
    {
        this.tray.writeTo(compound);
    }
}
