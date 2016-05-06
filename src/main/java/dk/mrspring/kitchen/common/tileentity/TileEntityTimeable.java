package dk.mrspring.kitchen.common.tileentity;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static dk.mrspring.kitchen.common.util.ItemUtils.*;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
public abstract class TileEntityTimeable extends TileEntityInteractable
{
    String sound = ModInfo.toResource("ding");
    private boolean hasDinged = false;
    private boolean hasTimer = false;

    public float[] getTimerLocalPosition()
    {
        return new float[]{0.5F, 1.2F, 0.5F};
    }

    @Override
    @SuppressWarnings("SimplifiableIfStatement")
    public boolean activated(EntityPlayer player, int side, float clickX, float clickY, float clickZ)
    {
        ItemStack stack = player.getCurrentEquippedItem();
        if (!notEmpty(stack) && item(stack, Kitchen.items.timer))
            return activatedWithTimer(player, stack, side, clickX, clickY, clickZ);
        else return false;
    }

    @Override
    public void broken(EntityPlayer player)
    {

    }

    public boolean activatedWithTimer(EntityPlayer player, ItemStack timer, int side,
                                      float clickX, float clickY, float clickZ)
    {
        if (!getHasTimer())
        {
            setHasTimer(true);
            decrSize(timer, 1);
            markForUpdate();
            return true;
        } else return false;
    }

    public void dropTimer(EntityPlayer player, int side)
    {
        spawnItem(newStack(Kitchen.items.timer));
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (!hasDinged && hasTimer && this.getTime() > this.getDoneTime())
        {
            playSound(sound, 1F, 1F);
            if (worldObj.isRemote)
            {
                float[] position = getTimerLocalPosition();
                Kitchen.proxy.particles.spawnParticle(worldObj,
                        position[0] + (float) xCoord,
                        position[1] + (float) yCoord,
                        position[2] + (float) zCoord, 0);
            }
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
