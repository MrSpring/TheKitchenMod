package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.api_impl.common.OvenRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class TileEntityOven extends TileEntityTimeable implements IOven
{
    public static String FILLED_SLOTS = "FilledSlots";

    IOvenItem[] items = new IOvenItem[4];
    NBTTagCompound[] tags = new NBTTagCompound[items.length];
    boolean open = false;
    boolean fuel = false;
    int cookTime = 0;

    public boolean[] getFreeSlots()
    {
        boolean[] booleans = new boolean[items.length];
        for (int i = 0; i < items.length; i++) booleans[i] = items[i] == null;
        return booleans;
    }

    @Override
    public boolean rightClicked(ItemStack clicked, EntityPlayer player)
    {
        for (IOvenItem item : items)
            if (item != null)
                if (item.onRightClicked(this, clicked, player))
                    return true;
        if (clicked == null && player.isSneaking())
        {
            this.toggleOpen();
            return true;
        }

        if (isOpen())
        {
            if (this.isFinished())
            {
                for (int i = 0; i < items.length; i++)
                {
                    IOvenItem item = items[i];
                    if (item != null && item.canBeRemoved(this, clicked, player))
                    {
                        removeItemAt(i);
                        ItemStack drop = item.onRemoved(this, clicked, player);
                        spawnItemInWorld(drop);
                        return true;
                    }
                }
                return false;
            } else
            {
                IOvenItem item = OvenRegistry.getInstance().getOvenItemFor(this, clicked, player);
                if (item.canAdd(this, clicked, player, getFreeSlots()))
                    if (this.addItem(item))
                    {
                        item.onAdded(this, clicked, player);
                        return true;
                    }
                return false;
            }
        } else return false;
    }

    private boolean addItem(IOvenItem item)
    {
        boolean[] spaced = spaceItem(item.getSize(this), item.consecutive(this));
        if (!spaced[0])
            return false;
        NBTTagCompound compound = new NBTTagCompound();
        List<Integer> fills = new ArrayList<Integer>();
        for (int i = 1; i < spaced.length; i++)
            if (spaced[i])
                fills.add(i - 1);

        compound.setIntArray(FILLED_SLOTS, ArrayUtils.toPrimitive(fills.toArray(new Integer[fills.size()])));
        for (int i = 1, j = 0; i < spaced.length; i = 1 + (j++))
            if (spaced[i])
            {
                items[j] = item;
                setSpecialInfo(j, (NBTTagCompound) compound.copy());
            }
        return true;
    }

    private boolean[] spaceItem(int size, boolean cons)
    {
        if (size > items.length)
            return new boolean[items.length + 1];
        int empty = 0;
        boolean[] slots = new boolean[items.length + 1];
        for (int i = 0; i < items.length; i++)
        {
            IOvenItem item = items[i];
            if (empty >= size)
                break;
            else if (item == null)
            {
                empty++;
                slots[i + 1] = true;
            } else if (cons && empty > 0)
            {
                empty = 0;
                slots = new boolean[items.length + 1];
            }
        }
        if (empty >= size)
        {
            slots[0] = true;
            return slots;
        } else return new boolean[items.length + 1];
    }

    @Override
    public void updateEntity()
    {
        if (!isOpen() && readyToCook())
        {
            cookTime++;
        }
    }

    private boolean readyToCook()
    {
        for (int i = 0; i < items.length; i++)
        {
            IOvenItem item = items[i];
            if (!item.readyToCook(this, i))
                return false;
        }
        return true;
    }

    @Override
    public boolean hasSpace(int size, boolean cons)
    {
        return spaceItem(size, cons)[0];
    }

    @Override
    public boolean hasFuel()
    {
        return fuel;
    }

    @Override
    public void addFuel()
    {
        this.fuel = true;
    }

    public void toggleOpen()
    {
        if (isOpen())
            closeAndStartCooking();
        else openAndStopCooking();
    }

    private void openAndStopCooking()
    {
        if (isCooking())
            fuel = false;
        open = true;
    }

    private void closeAndStartCooking()
    {
        this.open = false;
        for (int i = 0; i < items.length; i++)
        {
            IOvenItem item = items[i];
            if (item != null && !item.readyToCook(this, i))
                return;
        }
        cookTime = 0;
    }

    @Override
    public IOvenItem getItemAt(int slot)
    {
        return items[slot];
    }

    @Override
    public IOvenItem removeItemAt(int slot)
    {
        int[] associatedSlots = getAssociatedSlots(slot);
        IOvenItem last = null;
        for (int i : associatedSlots)
        {
            last = items[i];
            items[i] = null;
        }
        return last;
    }

    private int[] getAssociatedSlots(int slot)
    {
        NBTTagCompound slotCompound = getSpecialInfo(slot);
        int[] slots = slotCompound.getIntArray(FILLED_SLOTS);
        return slots == null ? new int[0] : slots;
    }

    @Override
    public boolean addOvenItem(IOvenItem item)
    {
        return false;
    }

    @Override
    public boolean addOvenItemAt(IOvenItem item, int slot)
    {
        return false;
    }

    @Override
    public boolean isSlotFree(int slot)
    {
        return false;
    }

    @Override
    public boolean isOpen()
    {
        return open;
    }

    @Override
    public EntityItem spawnItemInWorld(ItemStack stack)
    {
        return null;
    }

    @Override
    public boolean isCooking()
    {
        return getCookTime() > 0 && !isOpen();
    }

    @Override
    public int getCookTime()
    {
        return cookTime;
    }

    @Override
    public int getTime()
    {
        return getCookTime();
    }

    @Override
    public int getDoneTime()
    {
        int highest = 0;
        for (IOvenItem item : items) highest = Math.max(highest, item.getCookTime(this));
        return highest;
    }

    @Override
    public boolean isFinished()
    {
        return getCookTime() >= getDoneTime() && isCooking();
    }

    @Override
    public NBTTagCompound getSpecialInfo(int slot)
    {
        if (tags[slot] == null)
            tags[slot] = new NBTTagCompound();
        return tags[slot];
    }

    @Override
    public NBTTagCompound setSpecialInfo(int slot, NBTTagCompound compound)
    {
        NBTTagCompound old = (NBTTagCompound) getSpecialInfo(slot).copy();
        tags[slot] = compound;
        return old;
    }

    @Override
    public NBTTagCompound resetSpecialInfo(int slot)
    {
        return setSpecialInfo(slot, new NBTTagCompound());
    }
    /*protected ItemStack[] ovenItems = new ItemStack[4];
    protected int burnTime = 0;
    protected int itemState = 0;
    protected boolean isCooking = false;

    public static final int RAW = 0;
    public static final int COOKED = 1;
    public static final int BURNT = 2;

    protected boolean isOpen = false;
    protected boolean hasCoal = false;

    protected float lidAngle = 0;

    public boolean addItemStack(ItemStack itemStack)
    {
        if (!worldObj.isRemote)
            worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

        if (itemStack != null)
            if (itemStack.getItem() != null)
            {
                if (OvenRecipes.instance().getOutputFor(itemStack) != null)
                    return this.forceAddItemStack(itemStack);

                if (itemStack.getItem() == Items.coal && !this.hasCoal)
                {
                    this.hasCoal = true;
                    itemStack.stackSize--;
                    return true;
                }

                return false;
            } else
                return false;
        else
            return false;
    }

    private boolean isArrayEmpty(ItemStack[] array)
    {
        for (ItemStack stack : array)
            if (stack != null)
                return false;
        return true;
    }

    private boolean forceAddItemStack(ItemStack itemStack)
    {
        // TODO OreDictionary support?

        ItemStack temp = itemStack.copy();
        temp.stackSize = 1;

        // Goes through all Items in the ovenItems array, and checks if any of them are empty.
        for (int i = 0; i < this.ovenItems.length; ++i)
        {
            if (this.ovenItems[i] != null)
            {
                // If itemStacks item equals the Item in slot i, than increase its stackSize an decrease itemStacks.stackSize. Do nothing if they don't.
                if (itemStack.isItemEqual(this.ovenItems[i]) && this.ovenItems[i].stackSize < 4)
                {
                    ++this.ovenItems[i].stackSize;
                    --itemStack.stackSize;
                    return true;
                }
            } else
            {
                // Sets i slot to itemStack if it's null or its stackSize is 0.
                this.ovenItems[i] = itemStack.copy();
                this.ovenItems[i].stackSize = 1;
                --itemStack.stackSize;
                return true;
            }
        }

        return false;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (this.isOpen())
        {
            if (this.lidAngle + 0.1F < 1.0)
                this.lidAngle += 0.1F;
        } else
        {
            if (this.lidAngle - 0.1F > 0.0)
                this.lidAngle -= 0.1F;
        }

        if (!this.isOpen() && this.hasCoal)
            if (!this.isCooking)
                if (this.canCookItems())
                {
                    ++this.burnTime;
                    this.isCooking = true;
                } else
                    this.burnTime = 0;
            else
                ++this.burnTime;
        else
            this.burnTime = 0;

        if (this.burnTime == 0)
            this.itemState = RAW;

        if (this.burnTime >= 400)
        {
            this.itemState = COOKED;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.cookItems();
        }

        if (this.burnTime >= 600)
        {
            this.itemState = BURNT;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.burnItems();
        }
    }

    public boolean isOpen()
    {
        return isOpen;
    }

    public boolean hasCoal()
    {
        return hasCoal;
    }

    public int getItemState()
    {
        return itemState;
    }

    public ItemStack[] getOvenItems()
    {
        return ovenItems;
    }

    public ItemStack[] getDroppedItems()
    {
        return getOvenItems();
    }

    public float getLidAngle()
    {
        return this.lidAngle;
    }

    public boolean canCookItems()
    {
        boolean foundCompatible = false;

        for (ItemStack item : this.ovenItems)
            if (item != null)
                if (OvenRecipes.instance().getOutputFor(item) != null)
                    foundCompatible = true;

        return foundCompatible;
    }

    public void setOpen()
    {
        this.isOpen = true;
        this.hasCoal = false;
        this.isCooking = false;
    }

    public void setClosed()
    {
        this.isOpen = false;
        this.burnTime = 0;
    }

    public void cookItems()
    {
        for (int i = 0; i < this.ovenItems.length; ++i)
        {
            if (this.ovenItems[i] != null)
            {
                if (this.ovenItems[i].getItem() != null)
                {
                    if (OvenRecipes.instance().getOutputFor(this.ovenItems[i]) != null)
                    {
                        int stackSize = this.ovenItems[i].stackSize;
                        this.ovenItems[i] = OvenRecipes.instance().getOutputFor(this.ovenItems[i]);
                        this.ovenItems[i].stackSize = stackSize;
                    }
                }
            }
        }
    }

    public ItemStack removeTopItem()
    {
        int i;
        ItemStack itemStack = null;

        for (i = 3; i >= 0; --i)
        {
            if (this.ovenItems[i] != null)
                if (this.ovenItems[i].getItem() != null)
                {
                    itemStack = this.ovenItems[i].copy();
                    this.ovenItems[i] = null;
                    break;
                }
        }


        if (itemStack != null)
            return itemStack;
        else
            return null;
    }

    public void burnItems()
    {
        for (int i = 0; i < this.ovenItems.length; ++i)
        {
            if (this.ovenItems[i] != null)
            {
                if (this.ovenItems[i].getItem() != null)
                {
                    int stackSize = this.ovenItems[i].stackSize;
                    this.ovenItems[i] = new ItemStack(KitchenItems.burnt_meat, stackSize);
                }
            }
        }
    }

    public int getBurnTime()
    {
        return burnTime;
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

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setShort("CookTime", (short) this.burnTime);
        compound.setBoolean("IsOpen", this.isOpen());
        compound.setBoolean("HasCoal", this.hasCoal);
        compound.setShort("ItemState", (short) this.itemState);
        compound.setBoolean("IsCooking", this.isCooking);

        NBTTagList nbtTagList = new NBTTagList();

        for (int i = 0; i < this.ovenItems.length; ++i)
        {
            if (this.ovenItems[i] != null)
            {
                NBTTagCompound itemCompound = new NBTTagCompound();
                itemCompound.setByte("Slot", (byte) i);
                this.ovenItems[i].writeToNBT(itemCompound);
                nbtTagList.appendTag(itemCompound);
            }
        }

        compound.setTag("Items", nbtTagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.burnTime = compound.getShort("CookTime");
        this.isOpen = compound.getBoolean("IsOpen");
        this.hasCoal = compound.getBoolean("HasCoal");
        this.itemState = compound.getShort("ItemState");
        this.isCooking = compound.getBoolean("IsCooking");

        this.ovenItems = new ItemStack[4];

        NBTTagList nbtTagList = compound.getTagList("Items", 10);

        for (int i = 0; i < nbtTagList.tagCount(); ++i)
        {
            NBTTagCompound itemCompound = nbtTagList.getCompoundTagAt(i);
            byte slot = itemCompound.getByte("Slot");

            if (slot >= 0 && slot < this.ovenItems.length)
                this.ovenItems[slot] = ItemStack.loadItemStackFromNBT(itemCompound);
        }
    }

    @Override
    public int getTime()
    {
        return this.getBurnTime();
    }

    @Override
    public int getDoneTime()
    {
        return 400;
    }*/
}
