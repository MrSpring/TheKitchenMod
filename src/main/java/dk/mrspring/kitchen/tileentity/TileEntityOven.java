package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import dk.mrspring.kitchen.tileentity.casserole.Casserole;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityOven extends TileEntityTimeable
{
    protected ItemStack[] ovenItems = new ItemStack[4];
    protected Casserole casserole = null;
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
                System.out.println("ItemStack is not null");
                if (OvenRecipes.instance().getOutputFor(itemStack) != null)
                    return this.forceAddItemStack(itemStack);
                System.out.println("Could not find recipe for it...");

                if (itemStack.getItem() == Items.coal && !this.hasCoal)
                {
                    this.hasCoal = true;
                    itemStack.stackSize--;
                    return true;
                }

                System.out.println("And it is not coal either...");

                if (itemStack.getItem() == Item.getItemFromBlock(KitchenBlocks.casserole) && this.casserole == null && isArrayEmpty(this.ovenItems))
                {
                    System.out.println("Got it! It's a casserole!");
                    Casserole fromItemStack = Casserole.loadFromItemStack(itemStack);
                    if (fromItemStack != null)
                    {
                        System.out.println("Setting casserole");
                        this.casserole = fromItemStack;
                        itemStack.stackSize--;
                        return true;
                    }
                }

                return false;
            } else
                return false;
        else
            return false;
    }

    public boolean hasCasserole()
    {
        return this.casserole != null && isArrayEmpty(this.ovenItems);
    }

    public Casserole getCasserole()
    {
        return casserole;
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
        if (hasCasserole())
            return new ItemStack[]{casserole.toItemStack()};
        else return getOvenItems();
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

        if (hasCasserole())
        {
            itemStack = casserole.toItemStack();
            this.casserole = null;
        } else
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

        if (casserole != null)
        {
            NBTTagCompound casseroleCompound = new NBTTagCompound();
            casserole.writeToNBT(casseroleCompound);
            compound.setTag("Casserole", casseroleCompound);
        } else compound.removeTag("Casserole");
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

        if (compound.hasKey("Casserole"))
        {
            casserole = new Casserole();
            casserole.readFromNBT(compound.getCompoundTag("Casserole"));
        } else casserole = null;
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
    }
}
