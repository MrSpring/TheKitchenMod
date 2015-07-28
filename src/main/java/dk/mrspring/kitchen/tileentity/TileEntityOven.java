package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.api_impl.common.OvenRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileEntityOven extends TileEntityTimeable implements IOven
{
    public static final String IS_OPEN = "IsOpen";
    public static final String FILLED_SLOTS = "FilledSlots";
    public static final String HAS_FUEL = "HasFuel";
    public static final String COOK_TIME = "CookTime";
    public static final String ITEM_INDEX = "Index";
    public static final String ITEM_NAME = "ItemName";
    public static final String ITEM_LIST = "OvenItems";
    public static final String ITEM_INFO = "ItemTagInfo";
    public static final String ITEM_INFO_INDEX = "Index";
    public static final String ITEM_INFO_LIST = "OvenItemInfo";
    public static final String OLD_ITEM_LIST = "Items";

    IOvenItem[] items = new IOvenItem[4];
    NBTTagCompound[] tags = new NBTTagCompound[items.length];
    boolean open = false;
    boolean fuel = false;
    int cookTime = 0;

    @Override
    public boolean[] getFreeSlots()
    {
        boolean[] booleans = new boolean[items.length];
        for (int i = 0; i < items.length; i++) booleans[i] = items[i] == null;
        return booleans;
    }

    @Override
    public boolean rightClicked(ItemStack clicked, EntityPlayer player)
    {
        for (int slot = 0; slot < items.length; slot++)
        {
            IOvenItem item = items[slot];
            if (item != null && item.onRightClicked(this, clicked, player, slot)) return true;
        }
        if (clicked == null && player.isSneaking())
        {
            this.toggleOpen();
            return true;
        }

        if (isOpen())
        {
            if (this.isFinished()) return tryRemove(clicked, player);
            else
            {
                IOvenItem item = OvenRegistry.getInstance().getOvenItemFor(this, clicked, player);
                if (item.canAdd(this, clicked, player, getFreeSlots()))
                {
                    if (this.addItem(item, clicked, player)) return true;
                } else return tryRemove(clicked, player);
                return false;
            }
        } else return false;
    }

    private boolean tryRemove(ItemStack clicked, EntityPlayer player)
    {
        for (int i = 0; i < items.length; i++)
        {
            IOvenItem item = items[i];
            if (item != null && item.canBeRemoved(this, clicked, player, i))
            {
                ItemStack[] drops = item.onRemoved(this, clicked, player, i);
                removeItemAt(i);
                for (ItemStack drop : drops) spawnItemInWorld(drop);
                return true;
            }
        }
        return false;
    }

    private boolean addItem(IOvenItem item, ItemStack added, EntityPlayer player)
    {
        boolean[] spaced = spaceItem(item.getSize(this), item.consecutive(this));
        if (!spaced[0]) return false;
        if (spaced.length == 1)
        {
            item.onAdded(this, added, player, new int[0]);
            return true;
        }
        NBTTagCompound compound = new NBTTagCompound();
        List<Integer> fills = new ArrayList<Integer>();
        for (int i = 1; i < spaced.length; i++) if (spaced[i]) fills.add(i - 1);
        int[] slots = ArrayUtils.toPrimitive(fills.toArray(new Integer[fills.size()]));
        compound.setIntArray(FILLED_SLOTS, slots);
        for (int i = 1; i < spaced.length; i++)
        {
            int j = i - 1;
            if (spaced[i])
            {
                items[j] = item;
                setSpecialInfo(j, (NBTTagCompound) compound.copy());
            }
        }
        item.onAdded(this, added, player, slots);
        return true;
    }

    private boolean[] spaceItem(int size, boolean cons)
    {
        if (size == 0) return new boolean[]{true};
        if (size > items.length) return new boolean[items.length + 1];
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
        if (!hasFuel()) return false;
        boolean bool = false;
        for (int i = 0; i < items.length; i++)
        {
            IOvenItem item = items[i];
            if (item != null)
            {
                bool = true;
                if (!item.readyToCook(this, i))
                    return false;
            }
        }
        return bool;
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
        int[] associatedSlots = getOccupyingSlots(slot);
        IOvenItem last = null;
        for (int i : associatedSlots)
        {
            last = items[i];
            items[i] = null;
        }
        return last;
    }

    @Override
    public int[] getOccupyingSlots(int slot)
    {
        NBTTagCompound slotCompound = getSpecialInfo(slot);
        int[] slots = slotCompound.getIntArray(FILLED_SLOTS);
        return slots == null ? new int[0] : slots;
    }

    @Override
    public boolean isSlotFree(int slot)
    {
        return items[slot] == null;
    }

    @Override
    public boolean isOpen()
    {
        return open;
    }

    @Override
    public EntityItem spawnItemInWorld(ItemStack stack)
    {
        if (stack != null)
        {
            Random random = new Random();

            float xRandPos = random.nextFloat() * 0.8F + 0.1F;
            float zRandPos = random.nextFloat() * 0.8F + 0.1F;

            EntityItem entityItem = new EntityItem(worldObj, xCoord + xRandPos, yCoord + 1.15, zCoord + zRandPos, stack);

            entityItem.motionX = random.nextGaussian() * 0.005F;
            entityItem.motionY = random.nextGaussian() * 0.005F + 0.2F;
            entityItem.motionZ = random.nextGaussian() * 0.005F;

            worldObj.spawnEntityInWorld(entityItem);
            return entityItem;
        }
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
        int highest = 1;
        for (IOvenItem item : items)
            if (item != null) highest = Math.max(highest, item.getCookTime(this));
        return highest;
    }

    @Override
    public int getBurnTime()
    {
        int highest = 1;
        for (IOvenItem item : items)
            if (item != null) highest = Math.max(highest, item.getBurnTime(this));
        return highest;
    }

    @Override
    public boolean isFinished()
    {
        return getCookTime() >= getDoneTime();
    }

    @Override
    public boolean isBurnt()
    {
        return getCookTime() >= getBurnTime();
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

        compound.setBoolean(IS_OPEN, isOpen());
        compound.setBoolean(HAS_FUEL, hasFuel());
        compound.setInteger(COOK_TIME, getCookTime());

        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < items.length; i++)
        {
            IOvenItem item = getItemAt(i);
            if (item != null)
            {
                NBTTagCompound itemCompound = new NBTTagCompound();
                itemCompound.setInteger(ITEM_INDEX, i);
                itemCompound.setString(ITEM_NAME, item.getName());
                itemList.appendTag(itemCompound);
            }
        }
        compound.setTag(ITEM_LIST, itemList);
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < tags.length; i++)
        {
            NBTTagCompound c = tags[i];
            if (c != null)
            {
                NBTTagCompound c1 = new NBTTagCompound();
                c1.setTag(ITEM_INFO, c);
                c1.setInteger(ITEM_INFO_INDEX, i);
                tagList.appendTag(c1);
            }
        }
        compound.setTag(ITEM_INFO_LIST, tagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        open = compound.getBoolean(IS_OPEN);
        fuel = compound.getBoolean(HAS_FUEL);
        cookTime = compound.getInteger(COOK_TIME);

        if (compound.hasKey(ITEM_LIST, 9))
        {
            items = new IOvenItem[4];
            NBTTagList items = compound.getTagList(ITEM_LIST, 10);
            for (int i = 0; i < items.tagCount(); i++)
            {
                NBTTagCompound itemCompound = items.getCompoundTagAt(i);
                String itemName = itemCompound.getString(ITEM_NAME);
                if (itemName == null) continue;
                IOvenItem ovenItem = OvenRegistry.getInstance().getOvenItemFromName(itemName);
                if (ovenItem == null) continue;
                int index = itemCompound.getInteger(ITEM_INDEX);
                this.items[index] = ovenItem;
            }
        }

        if (compound.hasKey(ITEM_INFO_LIST))
        {
            tags = new NBTTagCompound[4];
            NBTTagList info = compound.getTagList(ITEM_INFO_LIST, 10);
            for (int i = 0; i < info.tagCount(); i++)
            {
                NBTTagCompound infoCompound = info.getCompoundTagAt(i);
                NBTTagCompound tag = infoCompound.getCompoundTag(ITEM_INFO);
                if (tag == null) continue;
                int index = infoCompound.getInteger(ITEM_INFO_INDEX);
                tags[index] = tag;
            }
        }
    }

    public int getSlotCount()
    {
        return this.items.length;
    }
}
