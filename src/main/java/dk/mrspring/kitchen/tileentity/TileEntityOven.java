package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityOven extends TileEntity
{
    protected ItemStack[] ovenItems = new ItemStack[4];
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
                if (FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null)
                    if (FurnaceRecipes.smelting().getSmeltingResult(itemStack).getItem() instanceof ItemFood)
                        return this.forceAddItemStack(itemStack);

                if (OvenRecipes.getCookingResult(itemStack) != null)
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
        {
            this.itemState = RAW;
        }

        if (this.burnTime == 400)
        {
            this.itemState = COOKED;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.cookItems();
        }

        if (this.burnTime == 600)
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

    public float getLidAngle()
    {
        return this.lidAngle;
    }

    public boolean canCookItems()
    {
        boolean foundCompatible = false;

        for (ItemStack item : this.ovenItems)
            if (item != null)
                if (OvenRecipes.getCookingResult(item) != null)
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
                    /*if (FurnaceRecipes.smelting().getSmeltingResult(this.ovenItems[i]) != null)
                    {
                        int stackSize = this.ovenItems[i].stackSize;
                        this.ovenItems[i] = FurnaceRecipes.smelting().getSmeltingResult(this.ovenItems[i]);
                        this.ovenItems[i].stackSize = stackSize;
                    }*/

                    if (OvenRecipes.getCookingResult(this.ovenItems[i]) != null)
                    {
                        int stackSize = this.ovenItems[i].stackSize;
                        this.ovenItems[i] = OvenRecipes.getCookingResult(this.ovenItems[i]);
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
}
