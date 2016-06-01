package dk.mrspring.kitchen.tileentity;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import dk.mrspring.kitchen.tileentity.renderer.OpeningAnimation;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityOven extends TileEntityBase
{
    ItemStack[] ovenItems = new ItemStack[4];
    int burnTime = 0;
    int itemState = 0;
    boolean isCooking = false;

    public static final int RAW = 0;
    public static final int COOKED = 1;
    public static final int BURNT = 2;

    boolean isOpen = false;
    boolean hasCoal = false;

    @SideOnly(Side.CLIENT)
    OpeningAnimation openingAnimation;

    public TileEntityOven()
    {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            openingAnimation = new OpeningAnimation(0, 65, 10, isOpen());
    }

    public boolean rightClicked(ItemStack itemStack, boolean alt)
    {
        if (itemStack != null)
        {
            if (isOpen())
            {
                if (OvenRecipes.getCookingResult(itemStack) != null)
                    return this.addItemStack(itemStack);

                if (itemStack.getItem() == Items.coal && !this.hasCoal)
                {
                    this.hasCoal = true;
                    itemStack.stackSize--;
                    mark();
                    return true;
                }
            }

            return false;
        } else
        {
            if (alt) toggleOpen();
            else if (isOpen()) spawn(removeFirstItem());
            return true;
        }
    }

    @Override
    public void spawn(double x, double y, double z, ItemStack... stacks)
    {
        super.spawn(x, y + 1D, z, stacks);
    }

    private boolean addItemStack(ItemStack itemStack)
    {
        // TODO OreDictionary support

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
                    mark();
                    return true;
                }
            } else
            {
                // Sets i slot to itemStack if it's null or its stackSize is 0.
                this.ovenItems[i] = itemStack.copy();
                this.ovenItems[i].stackSize = 1;
                --itemStack.stackSize;
                mark();
                return true;
            }
        }

        return false;
    }

    @Override
    public void updateEntity()
    {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            openingAnimation.update(isOpen());

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
            this.cookItems();
            mark();
        }

        if (this.burnTime == 600)
        {
            this.itemState = BURNT;
            this.burnItems();
            mark();
        }
    }

    @SideOnly(Side.CLIENT)
    public OpeningAnimation getOpeningAnimation()
    {
        return openingAnimation;
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

    public boolean canCookItems()
    {
        boolean foundCompatible = false;

        for (ItemStack item : this.ovenItems)
            if (item != null)
                if (OvenRecipes.getCookingResult(item) != null)
                    foundCompatible = true;

        return foundCompatible;
    }

    public void toggleOpen()
    {
        if (isOpen()) setClosed();
        else setOpen();
    }

    public void setOpen()
    {
        this.isOpen = true;
        this.hasCoal = false;
        this.isCooking = false;
        mark();
    }

    public void setClosed()
    {
        this.isOpen = false;
        this.burnTime = 0;
        mark();
    }

    public void cookItems()
    {
        for (int i = 0; i < this.ovenItems.length; ++i)
        {
            if (this.ovenItems[i] != null)
            {
                if (OvenRecipes.getCookingResult(this.ovenItems[i]) != null)
                {
                    int stackSize = this.ovenItems[i].stackSize;
                    this.ovenItems[i] = OvenRecipes.getCookingResult(this.ovenItems[i]);
                    this.ovenItems[i].stackSize *= stackSize;
                }
            }
        }
    }

    public void burnItems()
    {
        for (int i = 0; i < this.ovenItems.length; ++i)
            if (this.ovenItems[i] != null)
            {
                int stackSize = this.ovenItems[i].stackSize;
                this.ovenItems[i] = new ItemStack(KitchenItems.burnt_meat, stackSize);
            }
    }

    public ItemStack removeFirstItem()
    {
        for (int i = 0; i < ovenItems.length; i++)
        {
            ItemStack stack = ovenItems[i];
            if (stack != null)
            {
                ItemStack copy = stack.copy();
                ovenItems[i] = null;
                mark();
                return copy;
            }
        }
        return null;
    }

    @Override
    public void writeDataToNBT(NBTTagCompound compound)
    {
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
    public void readDataFromNBT(NBTTagCompound compound)
    {
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
    public int getNBTLevel()
    {
        return 1;
    }

    @Override
    public void readDataFromOldNBT(int oldLevel, int newLevel, NBTTagCompound compound)
    {
        warnNBTLevelChange(oldLevel, newLevel);
        readDataFromNBT(compound);
    }

    public boolean isCooking()
    {
        return isCooking;
    }
}
