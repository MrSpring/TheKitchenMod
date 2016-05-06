package dk.mrspring.kitchen.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import static dk.mrspring.kitchen.common.util.ItemUtils.empty;

/**
 * Created on 03-05-2016 for TheKitchenMod.
 */
public class TileEntityModernOven extends TileEntityOvenBase
{
    final String IS_OPEN = "IsOpen";

    boolean open = false;

    @Override
    public boolean activated(EntityPlayer player, int side, float clickX, float clickY, float clickZ)
    {
        if (player.isSneaking())
        {
            if (empty(player.getCurrentEquippedItem()))
            {
                open = !open;
                markForUpdate();
                return true;
            }
        } else if (open)
            return super.activated(player, side, clickX, clickY, clickZ);
        return false;
    }

    @Override
    public int getSlotCount()
    {
        return 4;
    }

    @Override
    public void writeDataToClient(NBTTagCompound compound)
    {
        super.writeDataToClient(compound);
        compound.setBoolean(IS_OPEN, open);
    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
        super.readDataFromNBT(compound);
        this.open = compound.getBoolean(IS_OPEN);
    }

    @Override
    public void writeDataToNBT(NBTTagCompound compound)
    {
        super.writeDataToNBT(compound);
        compound.setBoolean(IS_OPEN, open);
    }
}
