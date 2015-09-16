package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.Kitchen;
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

    public float[] getTimerLocalPosition()
    {
        return new float[]{0.5F, 1.2F, 0.5F};
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (!hasDinged && hasTimer && this.getTime() > this.getDoneTime())
        {
            worldObj.playSound(xCoord, yCoord, zCoord, ModInfo.modid + ":" + "ding", 1, 1, false);
            if (worldObj.isRemote)
            {
                float[] position = getTimerLocalPosition();
                Kitchen.proxy.spawnDingParticle(worldObj, position[0] + (float) xCoord, position[1] + (float) yCoord, position[2] + (float) zCoord);
            }
            System.out.println("sound");
            hasDinged = true;
        } else if (this.getTime() < this.getDoneTime() && hasDinged && hasTimer)
            this.hasDinged = false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.hasTimer = compound.getBoolean("HasTimer");
        this.hasDinged = compound.getBoolean("HasDinged");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setBoolean("HasTimer", this.hasTimer);
        compound.setBoolean("HasDinged", this.hasDinged);
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
