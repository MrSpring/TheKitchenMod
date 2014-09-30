package dk.mrspring.kitchen.api.event;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Konrad on 30-09-2014 for TheKitchenMod.
 */
public interface IOnAddedToBoardEvent extends IBoardEvent
{
    public void onAdded(List<ItemStack> layers, ItemStack added, NBTTagCompound specialTagInfo);
    public boolean canAdd(List<ItemStack> currentLayers, ItemStack toAdd, NBTTagCompound specialTagInfo);
}
