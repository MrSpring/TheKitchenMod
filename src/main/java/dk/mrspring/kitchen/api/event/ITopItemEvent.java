package dk.mrspring.kitchen.api.event;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Konrad on 30-09-2014 for TheKitchenMod.
 */
public interface ITopItemEvent extends IBoardEvent
{
    public boolean canAddItemOnTop(List<ItemStack> layers, ItemStack tryingToAdd, NBTTagCompound specialTagInfo);
}
