package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.ISoundPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by MrSpring on 11-12-2014 for TheKitchenMod.
 */
public abstract class TileEntityTimeable extends TileEntityBase implements ISoundPlayer
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
            playSound(ModInfo.toTexture("sizzle"), 1F, 1F, false);
            if (worldObj.isRemote)
            {
                float[] position = getTimerLocalPosition();
                Kitchen.proxy.spawnDingParticle(worldObj, position[0] + (float) xCoord, position[1] + (float) yCoord, position[2] + (float) zCoord);
            }
            hasDinged = true;
        } else if (this.getTime() < this.getDoneTime() && hasDinged && hasTimer)
            this.hasDinged = false;
    }

    @Override
    public void playSound(String sound, float f1, float f2, boolean b1)
    {
        worldObj.playSoundEffect(0.5D + xCoord, 0.5D + yCoord, 0.5D + zCoord, sound, f1, f2);
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
