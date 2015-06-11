package dk.mrspring.kitchen.api;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Konrad on 11-06-2015.
 */
public interface ISpecialTagInfo
{
    NBTTagCompound getSpecialInfo();

    NBTTagCompound setSpecialInfo(NBTTagCompound compound);

    NBTTagCompound resetSpecialInfo();
}
