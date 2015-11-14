package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.recipe.ToasterRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

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
        if (ToasterRecipes.instance().getOutputFor(stack) != null && !isCooking)
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
            if (this.cookTime > 400 && !worldObj.isRemote)
            {
                if (this.stack1 != null)
                    this.stack1 = ToasterRecipes.instance().getOutputFor(stack1);

                if (this.stack2 != null)
                    this.stack2 = ToasterRecipes.instance().getOutputFor(stack2);

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
                if (ToasterRecipes.instance().getOutputFor(stack1) == null)
                    return false;
            if (stack2 != null)
                if (ToasterRecipes.instance().getOutputFor(stack2) == null)
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
    public void writeDataToNBT(NBTTagCompound compound)
    {
        compound.setInteger("CookTime", cookTime);
        compound.setBoolean("IsCooking", isCooking);
        if (stack1 != null)
            compound.setTag("Stack1", stack1.writeToNBT(new NBTTagCompound()));
        if (stack2 != null)
            compound.setTag("Stack2", stack2.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
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
    public int getTime()
    {
        return this.cookTime;
    }

    @Override
    public int getDoneTime()
    {
        return 400;
    }

    @Override
    public float[] getTimerLocalPosition()
    {
        final float P = 0.0625F;
        float[][] poses = new float[][]{
                new float[]{7 * P, 3 * P, 3 * P},
                new float[]{1 - 5 * P, 3 * P, 4 * P},
                new float[]{1 - 3 * P, 3 * P, 7 * P},
                new float[]{1 - 4 * P, 3 * P, 1 - 5 * P},
                new float[]{1F - 7 * P, 3 * P, 1 - 3 * P},
                new float[]{5 * P, 3 * P, 1 - 4 * P},
                new float[]{3 * P, 3 * P, 1F - 7 * P},
                new float[]{4 * P, P, 5 * P}
        };
        int metadata = worldObj.getBlockMetadata(xCoord, yCoord - 1, zCoord);
        return metadata >= 0 && metadata < poses.length ? poses[metadata] : super.getTimerLocalPosition();
    }
}
