package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.recipe.ToasterRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class TileEntityToaster extends TileEntityTimeable
{
    ItemStack stack1 = null, stack2 = null;
    int cookTime = 0;
    boolean isCooking = false;

    public boolean addItem(ItemStack clicked)
    {
        ItemStack stack = clicked.copy();
        stack.stackSize = 1;
        if (ToasterRecipes.getToastingResult(stack) != null && !isCooking)
        {
            if (this.stack1 == null)
            {
                this.stack1 = stack;
                return true;
            } else if (this.stack2 == null)
            {
                this.stack2 = stack;
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (isCooking)
        {
            if (this.cookTime > 400)
            {
                if (this.stack1 != null)
                {
                    this.stack1 = ToasterRecipes.getToastingResult(stack1);
                    this.stack1.stackSize = 1;
                }
                if (this.stack2 != null)
                {
                    this.stack2 = ToasterRecipes.getToastingResult(stack2);
                    this.stack2.stackSize = 1;
                }
                this.cookTime = 0;
                this.isCooking = false;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                return;
            }

            this.cookTime++;
        }
    }

    public boolean canStartCooking()
    {
        if (this.stack1 != null || this.stack2 != null)
        {
            if (stack1 != null)
                if (ToasterRecipes.getToastingResult(stack1) == null)
                    return false;
            if (stack2 != null)
                if (ToasterRecipes.getToastingResult(stack2) == null)
                    return false;

            return true;
        }
        return false;
    }

    public ItemStack removeItem()
    {
        ItemStack toReturn = null;

        if (!this.isCooking)
        {
            if (this.stack1 != null)
            {
                toReturn = this.stack1;
                this.stack1 = null;
            } else if (this.stack2 != null)
            {
                toReturn = this.stack2;
                this.stack2 = null;
            }
        }
        return toReturn;
    }

    public boolean startCooking()
    {
        if (this.canStartCooking() && !this.isCooking)
        {
            this.isCooking = true;
            this.cookTime = 0;
            return true;
        } else return false;
    }

    public boolean isCooking()
    {
        return isCooking;
    }

    public ItemStack getStack1()
    {
        return stack1;
    }

    public ItemStack getStack2()
    {
        return stack2;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("CookTime", cookTime);
        compound.setBoolean("IsCooking", isCooking);
        if (stack1 != null)
            compound.setTag("Stack1", stack1.writeToNBT(new NBTTagCompound()));
        if (stack2 != null)
            compound.setTag("Stack2", stack2.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.cookTime = compound.getInteger("CookTime");
        this.isCooking = compound.getBoolean("IsCooking");
        if (compound.hasKey("Stack1"))
            this.stack1 = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Stack1"));
        else this.stack1 = null;
        if (compound.hasKey("Stack2"))
            this.stack2 = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Stack2"));
        else this.stack2 = null;
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
    public int getTime()
    {
        return this.cookTime;
    }

    @Override
    public int getDoneTime()
    {
        return 380;
    }
}
