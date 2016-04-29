package dk.mrspring.kitchen.common.tileentity;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.common.api.oven.IOven;
import dk.mrspring.kitchen.common.api.oven.item.OvenItem;
import dk.mrspring.kitchen.common.api.oven.item.OvenItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Collection;

/**
 * Created on 27-03-2016 for TheKitchenMod.
 */
public class TileEntityOven extends TileEntityInteractable implements IOven
{
    boolean open = false;
    OvenSlot[] slots = new OvenSlot[4];

    public TileEntityOven()
    {
        slots[0] = new OvenSlot(3, 1);
        slots[1] = new OvenSlot(0, 2);
        slots[2] = new OvenSlot(1, 3);
        slots[3] = new OvenSlot(2, 0);
    }

    private class OvenSlot
    {
        OvenItemStack item;
        int next, prev;

        OvenSlot(int prev, int next)
        {
            this.next = next;
            this.prev = prev;
        }

        void setCopy(OvenItemStack stack)
        {
            this.item = stack.copy();
        }

        boolean isEmpty()
        {
            return item == null;
        }

        int getConcurrentEmpty()
        {
            int i = 0, n = 0;
            OvenSlot s = this;
            while (s.isEmpty() && i < 4)
            {
                n++;
                s = slots[s.next];
                i++;
            }
            return n;
        }

        void clear()
        {
            item = null;
        }
    }

    @Override
    public int getSlotCount()
    {
        return slots.length;
    }

    @Override
    public int[] getConcurrentEmptySlots(int slot)
    {
        int empty = slots[slot].getConcurrentEmpty();
        int[] emptySlots = new int[empty];
        for (int i = 0; i < empty; i++) emptySlots[i] = slot + i;
        return emptySlots;
    }

    @Override
    public void fillSlots(OvenItemStack stack, int... slots)
    {
        for (int slot : slots) this.slots[slot].setCopy(stack);
    }

    @Override
    public void clearSlots(OvenItemStack stack, int... slots)
    {
        for (int slot : slots) this.slots[slot].clear();
    }

    @Override
    public void activated(EntityPlayer player, int side, float clickX, float clickY, float clickZ)
    {
        if (player.isSneaking())
        {
            if (player.getCurrentEquippedItem() == null)
            {
                open = !open;
                markForUpdate();
            }
        } else
        {
            int count = getSlotCount();
            for (int i = 0; i < count; i++)
            {
                OvenSlot slot = slots[i];
                if (slot.isEmpty())
                {
                    int[] slots = getConcurrentEmptySlots(i);
                    if (onEmptySlot(slots, player))
                    {
                        markForUpdate();
                        break;
                    } else i += slots.length - 1;
                } else if (slot.item.getItem().onItemRightClick(player, this, slot.item, i))
                    break;
            }
        }
    }

    private boolean onEmptySlot(int[] slots, EntityPlayer player)
    {
        Collection<OvenItem> items = Kitchen.ovenItems.getRegisteredItems();
        for (OvenItem item : items) if (item.canAddToOven(player, this, slots)) return true;
        return false;
    }

    @Override
    public void placed(EntityPlayer player)
    {
    }

    @Override
    public void broken(EntityPlayer player)
    {
    }

    @Override
    public void writeDataToClient(NBTTagCompound compound)
    {
        compound.setInteger("SlotCount", 4);
        compound.setBoolean("IsOpen", open);
        NBTTagList list = new NBTTagList();
        int count = getSlotCount();
        for (int i = 0; i < count; i++)
        {
            OvenItemStack stack = slots[i].item;
            if (stack != null)
            {
                NBTTagCompound rendering = new NBTTagCompound();
                stack.getItem().writeRenderingInfoToClient(this, stack, i, rendering);
                rendering.setInteger("Slot", i);
                list.appendTag(rendering);
            }
        }
        if (list.tagCount() > 0) compound.setTag("RenderInfo", list);
    }

    @Override
    public void writeDataToNBT(NBTTagCompound compound)
    {
        compound.setBoolean("IsOpen", open);
        System.out.println("Writing!");
        NBTTagList list = new NBTTagList();
        int count = getSlotCount();
        for (int i = 0; i < count; i++)
        {
            OvenItemStack stack = slots[i].item;
            if (stack != null)
            {
                NBTTagCompound item = new NBTTagCompound();
                stack.writeToNBT(item);
                item.setInteger("Slot", i);
                list.appendTag(item);
            }
        }
        compound.setTag("Items", list);
    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
        System.out.println(compound.toString());
        open = compound.getBoolean("IsOpen");
        NBTTagList list = compound.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound comp = list.getCompoundTagAt(i);
            slots[comp.getInteger("Slot")].item = OvenItemStack.fromNBT(comp);
        }
    }
}
