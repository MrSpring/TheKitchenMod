package dk.mrspring.kitchen.client.tileentity;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created on 27-03-2016 for TheKitchenMod.
 */
public class TileEntityClientOven extends TileEntityClient
{
    boolean isOpen = false;

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
        this.isOpen = compound.getBoolean("IsOpen");
        System.out.println(compound.getString("id"));
    }
}
