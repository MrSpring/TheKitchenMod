package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.api_impl.common.registry.OvenRegistry;
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
    public static final String FILLED_SLOTS = "FilledSlots"; // TODO: Store in OvenSlot
    public static final String IS_OPEN = "IsOpen";
    public static final String HAS_FUEL = "HasFuel";
    public static final String IS_COOKING = "IsCooking";
    public static final String ITEM_INDEX = "ItemIndex";
    public static final String ITEM_NAME = "ItemName";
    public static final String ITEM_COOK_TIME = "ItemCookTime";
    public static final String ITEM_INFO = "ItemInfo";
    public static final String ITEM_LIST = "Items";

    OvenSlot[] items = new OvenSlot[4];
    boolean fuel = false, open = false, cooking = false;

    @Override
    public boolean rightClicked(ItemStack clicked, EntityPlayer player)
    {
        for (int slot = 0; slot < getSlotCount(); slot++)
        {
            IOvenItem item = getItemAt(slot);
            if (item != null && item.onRightClicked(this, clicked, player, slot)) return true;
        }
        if (clicked == null && player.isSneaking())
        {
            this.toggleOpen();
            return true;
        }

        if (isOpen())
        {
//            if (this.isFinished() && tryRemove(clicked, player)) return true;
//            else
//            {
            IOvenItem item = OvenRegistry.getInstance().getOvenItemFor(this, clicked, player);
            if (item.canAdd(this, clicked, player, getFreeSlots()))
            {
                if (this.addItem(item, clicked, player)) return true;
            } else return tryRemove(clicked, player);
            return false;
//            }
        } else return false;
    }

    private boolean tryRemove(ItemStack clicked, EntityPlayer player)
    {
        for (int i = 0; i < items.length; i++)
        {
            IOvenItem item = getItemAt(i);
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
        populateTag(compound, slots);
        for (int i = 1; i < spaced.length; i++)
        {
            int j = i - 1;
            if (spaced[i])
            {
                setSlot(j, item);
                setSpecialInfo(j, (NBTTagCompound) compound.copy());
            }
        }
        item.onAdded(this, added, player, slots);
        return true;
    }

    private boolean[] spaceItem(int size, boolean cons)
    {
        if (size == 0) return new boolean[]{true};
        if (size > getSlotCount()) return new boolean[getSlotCount() + 1];
        int empty = 0;
        boolean[] slots = new boolean[getSlotCount() + 1];
        for (int i = 0; i < getSlotCount(); i++)
        {
            IOvenItem item = getItemAt(i);
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
    public void toggleOpen()
    {
        if (isOpen()) closeAndStartCooking();
        else openAndStopCooking();
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (cooking) this.updateCookTimes();
    }

    private void openAndStopCooking()
    {
        if (isCooking()) fuel = false;
        open = true;
        cooking = false;
    }

    private void closeAndStartCooking()
    {
        this.open = false;
        boolean foundNonNull = false;
        for (int i = 0; i < items.length; i++)
        {
            IOvenItem item = getItemAt(i);
            if (item != null)
            {
                foundNonNull = true;
                if (!item.readyToCook(this, i))
                    return;
            }
        }
        if (!foundNonNull) return;
        resetCookTimes();
        cooking = true;
    }

    private void updateCookTimes()
    {
        for (int slot = 0; slot < getSlotCount(); slot++) getSlot(slot).updateCookTime();
    }

    private void resetCookTimes()
    {
        for (int i = 0; i < getSlotCount(); i++) getSlot(i).resetTime();
    }

    public int getSlotCount()
    {
        return items.length;
    }

    private OvenSlot getSlot(int slot)
    {
        if (slot >= 0 && slot < getSlotCount())
            return items[slot] == null ? items[slot] = new OvenSlot() : items[slot];
        else return new OvenSlot();
    }

    private OvenSlot setSlot(int slot, IOvenItem item)
    {
        return getSlot(slot).setItem(item);
    }

    @Override
    public IOvenItem getItemAt(int slot)
    {
        return getSlot(slot).getItem();
    }

    @Override
    public IOvenItem removeItemAt(int slot)
    {
        int[] associatedSlots = getOccupyingSlots(slot);
        IOvenItem last = null;
        for (int removing : associatedSlots)
        {
            last = getItemAt(removing);
            getSlot(removing).clear();
        }
        return last;
    }

    @Override
    public boolean isSlotFree(int slot)
    {
        return slot >= 0 && slot < items.length && getSlot(slot).isEmpty();
    }

    @Override
    public boolean isOpen()
    {
        return open;
    }

    @Override
    public boolean hasSpace(int size, boolean cons)
    {
        return spaceItem(size, cons)[0];
    }

    @Override
    public NBTTagCompound getSpecialInfo(int slot)
    {
        return getSlot(slot).info();
    }

    @Override
    public NBTTagCompound setSpecialInfo(int slot, NBTTagCompound compound)
    {
        if (compound != null && !compound.hasKey(FILLED_SLOTS, 11)) populateTag(compound, slot);
        return getSlot(slot).setInfo(compound);
    }

    private void populateTag(NBTTagCompound compound, int... slots)
    {
        if (compound != null) compound.setIntArray(FILLED_SLOTS, slots);
    }

    @Override
    public NBTTagCompound resetSpecialInfo(int slot)
    {
        return getSlot(slot).resetInfo().info();
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

    @Override
    public boolean[] getFreeSlots()
    {
        boolean[] booleans = new boolean[items.length];
        for (int i = 0; i < getSlotCount(); i++) booleans[i] = getSlot(i).isEmpty();
        return booleans;
    }

    @Override
    public int[] getOccupyingSlots(int slot)
    {
        NBTTagCompound slotCompound = getSpecialInfo(slot);
        int[] slots = slotCompound.getIntArray(FILLED_SLOTS);
        return slots == null ? new int[]{slot} : slots;
    }

    @Override
    public boolean isBurnt(int slot)
    {
        return getSlot(slot).isBurnt();
    }

    @Override
    public boolean isCooking()
    {
        return cooking;
    }

    @Override
    public int getCookTime(int slot)
    {
        return getSlot(slot).getCookTime();
    }

    @Override
    public int getDoneTime(int slot)
    {
        return getSlot(slot).getDoneTime();
    }

    @Override
    public int getBurnTime(int slot)
    {
        return getSlot(slot).getBurnTime();
    }

    @Override
    public boolean isFinished(int slot)
    {
        return getCookTime(slot) >= getDoneTime(slot);
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
    public int getTime()
    {
        int lowest = 0;
        for (int slot = 0; slot < getSlotCount(); slot++)
            lowest = Math.max(lowest, getSlot(slot).getCookTime());
//        System.out.println("Returning time: " + lowest);
        return lowest;
    }

    @Override
    public int getDoneTime()
    {
        int lowest = 0;
        for (int slot = 0; slot < getSlotCount(); slot++)
            lowest = Math.max(lowest, getSlot(slot).getDoneTime());
//        System.out.println("Returning done: " + lowest);
        return lowest;
    }

    @Override
    public float[] getTimerLocalPosition()
    {
        final float P = 0.0625F;
        float[][] poses = new float[][]{
                new float[]{P * 3, 1F - P - P, 0},
                new float[]{1, 1F - P - P, P * 3},
                new float[]{1 - P * 3, 1F - P * 2, 1F},
                new float[]{0, 1F - P - P, 1F - P * 3}
        };
        int metadata = getBlockMetadata();
        return metadata >= 0 && metadata < poses.length ? poses[metadata] : super.getTimerLocalPosition();
    }

    public float getLidAngle()
    {
        return isOpen() ? 1F : 0F;
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
        compound.setBoolean(IS_COOKING, isCooking());

        writeItemData(compound);
    }

    public void writeItemData(NBTTagCompound compound)
    {
        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < getSlotCount(); i++)
        {
            OvenSlot slot = getSlot(i);
            if (!slot.isEmpty())
            {
                IOvenItem item = slot.getItem();
                NBTTagCompound itemCompound = new NBTTagCompound();
                itemCompound.setInteger(ITEM_INDEX, i);
                itemCompound.setString(ITEM_NAME, item.getName());
                itemCompound.setInteger(ITEM_COOK_TIME, slot.getCookTime());
                itemCompound.setTag(ITEM_INFO, slot.info());
                itemList.appendTag(itemCompound);
            }
        }
        compound.setTag(ITEM_LIST, itemList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        // TODO: Read old NBT data for backwards compatibility

        open = compound.getBoolean(IS_OPEN);
        fuel = compound.getBoolean(HAS_FUEL);
        cooking = compound.getBoolean(IS_COOKING);

        clearAllItems();
        readItemData(compound);
    }

    private void clearAllItems()
    {
        for (int i = 0; i < getSlotCount(); i++) getSlot(i).clear();
    }

    private void readItemData(NBTTagCompound compound)
    {
        NBTTagList itemList = compound.getTagList(ITEM_LIST, 10);
        for (int i = 0; i < itemList.tagCount(); i++)
        {
            NBTTagCompound itemCompound = itemList.getCompoundTagAt(i);
            if (itemCompound.hasKey(ITEM_NAME, 8) && itemCompound.hasKey(ITEM_INDEX, 3))
            {
                String itemName = itemCompound.getString(ITEM_NAME);
                IOvenItem item = OvenRegistry.getInstance().getOvenItemFromName(itemName);
                if (item != null)
                {
                    int index = itemCompound.getInteger(ITEM_INDEX);
                    int cook = itemCompound.getInteger(ITEM_COOK_TIME);
                    NBTTagCompound info = itemCompound.getCompoundTag(ITEM_INFO);

                    OvenSlot slot = getSlot(index);
                    slot.setItem(item);
                    slot.setTime(cook);
                    slot.setInfo(info);
                }
            }
        }
    }

    private class OvenSlot
    {
        IOvenItem item = null;
        int cookTime = 0;
        NBTTagCompound info = new NBTTagCompound();

        public boolean isEmpty()
        {
            return item == null;
        }

        public int getCookTime()
        {
            return isEmpty() ? 0 : cookTime;
        }

        public int getDoneTime()
        {
            return !isEmpty() ? item.getCookTime(TileEntityOven.this) : 0;
        }

        public int getBurnTime()
        {
            return !isEmpty() ? item.getBurnTime(TileEntityOven.this) : 0;
        }

        public boolean isDone()
        {
            return isEmpty() || TileEntityOven.this.getTime() > getDoneTime();
        }

        public boolean isBurnt()
        {
            return !isEmpty() && TileEntityOven.this.getTime() > getBurnTime();
        }

        public OvenSlot setItem(IOvenItem item)
        {
            this.item = item;
            if (this.item == null) resetInfo();
            return this;
        }

        public IOvenItem getItem()
        {
            return item;
        }

        public OvenSlot resetInfo()
        {
            this.info = new NBTTagCompound();
            return this;
        }

        public NBTTagCompound info()
        {
            return info == null ? setInfo(new NBTTagCompound()) : info;
        }

        public NBTTagCompound setInfo(NBTTagCompound newCompound)
        {
            return this.info = newCompound;
        }

        public OvenSlot resetTime()
        {
            this.cookTime = 0;
            return this;
        }

        public OvenSlot resetItem()
        {
            this.item = null;
            resetInfo();
            return this;
        }

        public void clear()
        {
            resetItem();
            resetTime();
        }

        public void updateCookTime()
        {
            if (!isEmpty()) cookTime++;
        }

        public void setTime(int time)
        {
            this.cookTime = time;
        }
    }

/*
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

    IOvenItem[] items = new IOvenItem[4];
    NBTTagCompound[] tags = new NBTTagCompound[items.length];
    int[] cookTime = new int[4];
    boolean open = false;
    boolean fuel = false;
    float lidAngle = 0;

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
            if (this.isFinished() && tryRemove(clicked, player)) return true;
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
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
            if (isOpen())
                lidAngle = Math.min(1F, lidAngle + 0.1F);
            else lidAngle = Math.max(0F, lidAngle - 0.1F);

        if (!isOpen() && readyToCook())
        {
            for (int i = 0; i < cookTime.length; i++)
                cookTime[i]++;
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
        cookTime = new int[4];
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
            tags[i] = null;
            cookTime[i] = 0;
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
    public int getCookTime(int slot)
    {
//        int highest = 1;
//        for (int i : cookTime) highest = Math.max(highest, i);
//        return highest;
        return cookTime[slot];
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
    public int getBurnTime(int slot)
    {
        int highest = 1;
        for (IOvenItem item : items)
            if (item != null) highest = Math.max(highest, item.getBurnTime(this));
        return highest;
    }

    @Override
    public boolean isFinished(int slot)
    {
        return getCookTime() >= getDoneTime();
    }

    @Override
    public boolean isBurnt(int slot)
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
        compound.setIntArray(COOK_TIME, cookTime);

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
        cookTime = compound.getIntArray(COOK_TIME);

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

    public float getLidAngle()
    {
        return lidAngle;
    }
*/
}
