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
public abstract class TileEntityOvenBase extends TileEntityInteractable implements IOven
{
    OvenSlot[] slots;// = new OvenSlot[4];

    public TileEntityOvenBase()
    {
        int slots = getSlotCount();
        if (slots <= 0) throw new IllegalArgumentException("An oven must have at least one slot!");
        this.slots = new OvenSlot[slots];

        for (int i = 0; i < slots; i++)
            this.slots[i] = new OvenSlot(i, slots);
    }

    private class OvenSlot
    {
        OvenItemStack item;
        int next, prev;

        OvenSlot(int index, int slotCount)
        {
            this.next = index + 1;
            if (next >= slotCount) this.next = 0;
            this.prev = index - 1;
            if (prev < 0) prev = slotCount - 1;
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
            while (s.isEmpty() && i < getSlotCount())
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
    public boolean activated(EntityPlayer player, int side, float clickX, float clickY, float clickZ)
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
                    return true;
                } else i += slots.length - 1;
            } else if (slot.item.getItem().onItemRightClick(player, this, slot.item, i))
                return true;
        }
        return false;
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
        compound.setInteger("SlotCount", getSlotCount());
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
        NBTTagList list = compound.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound comp = list.getCompoundTagAt(i);
            slots[comp.getInteger("Slot")].item = OvenItemStack.fromNBT(comp);
        }
    }
}
