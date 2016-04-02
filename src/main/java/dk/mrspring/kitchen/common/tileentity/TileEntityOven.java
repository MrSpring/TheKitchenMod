package dk.mrspring.kitchen.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created on 27-03-2016 for TheKitchenMod.
 */
public class TileEntityOven extends TileEntityInteractable
{
    boolean open = false;

    @Override
    public void activated(EntityPlayer player, int side, float clickX, float clickY, float clickZ)
    {
        if (player.isSneaking())
        {
            open = !open;
            markForUpdate();
        }
    }

    @Override
    public void placed(EntityPlayer player)
    {
    }

    @Override
    public void broken(EntityPlayer player)
    {
    }

    @Override
    public void writeDataToClient(NBTTagCompound compound)
    {
        compound.setBoolean("IsOpen", open);
    }

    @Override
    public void writeDataToNBT(NBTTagCompound compound)
    {
        compound.setBoolean("IsOpen", open);
    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
        open = compound.getBoolean("IsOpen");
    }
}
