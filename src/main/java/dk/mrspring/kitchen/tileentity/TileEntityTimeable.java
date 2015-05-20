package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.ModInfo;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by MrSpring on 11-12-2014 for TheKitchenMod.
 */
public abstract class TileEntityTimeable extends TileEntity
{
    private boolean hasDinged = false;
    private boolean hasTimer = false;

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (this.getTime() > this.getDoneTime() && !hasDinged && hasTimer)
        {
            worldObj.playSound(xCoord, yCoord, zCoord, ModInfo.modid + ":" + "ding", 1, 1, false);
            hasDinged = true;
        } else if (this.getTime() < this.getDoneTime() && hasDinged && hasTimer)
            this.hasDinged = false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.hasTimer = compound.getBoolean("HasTimer");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setBoolean("HasTimer", this.hasTimer);
    }

    public boolean getHasTimer()
    {
        return hasTimer;
    }

    public void setHasTimer(boolean hasTimer)
    {
        this.hasTimer = hasTimer;
    }

    public abstract int getTime();

    public abstract int getDoneTime();
}
